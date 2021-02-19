package kim.sihwan.trip_reviewer.service;

import kim.sihwan.trip_reviewer.domain.*;
import kim.sihwan.trip_reviewer.dto.review.ReviewListResponseDto;
import kim.sihwan.trip_reviewer.dto.review.ReviewRequestDto;
import kim.sihwan.trip_reviewer.dto.review.ReviewResponseDto;
import kim.sihwan.trip_reviewer.repository.CommentRepository;
import kim.sihwan.trip_reviewer.repository.MemberRepository;
import kim.sihwan.trip_reviewer.repository.ReviewAlbumRepository;
import kim.sihwan.trip_reviewer.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final MemberRepository memberRepository;
    private final CommentRepository commentRepository;
    private final ReviewAlbumRepository reviewAlbumRepository;
    private final ReviewAlbumService reviewAlbumService;

    public List<ReviewListResponseDto> findAllReviews(){
        List<Review> reviews = reviewRepository.findAll();
        List<ReviewListResponseDto> list = reviews
                .stream()
                .map(review -> new ReviewListResponseDto(review))
                .collect(Collectors.toList());
        Collections.reverse(list);
        return list;
    }

    public ReviewResponseDto findOneByReviewId(Long reviewId){
        Review review = reviewRepository.findReviewById(reviewId);
        ReviewResponseDto reviewResponseDto = new ReviewResponseDto(review);
        return reviewResponseDto;
    }

    @Transactional
    public void addReview(ReviewRequestDto requestDto){
        Member member = memberRepository.findMemberByUsername(requestDto.getUsername()).get();
        Review review = requestDto.toEntity(requestDto);
        String fileUrl = "C:\\Users\\김시환\\Desktop\\Git\\Trip-Reviewer\\src\\main\\resources\\static\\reviewImages\\";
        String saveUrl = "http://localhost:8080/api/album/download?filename=";

        try{
            for(MultipartFile file : requestDto.getFiles()){
                String newFilename = createNewFilename(file.getOriginalFilename());
                File dest = new File(fileUrl + newFilename);
                file.transferTo(dest);
                ReviewAlbum reviewAlbum = ReviewAlbum
                        .builder()
                        .url(saveUrl + newFilename)
                        .filename(newFilename)
                        .originFilename(file.getOriginalFilename())
                        .build();
                reviewAlbum.addReview(review);
            }
            review.addMember(member);
            reviewRepository.save(review);
        }catch (Exception e){
            System.out.println("리뷰 생성 중 오류 발생");
        }
    }

    @Transactional
    public void deleteReview(Long reviewId){
        Review review = reviewRepository.findReviewById(reviewId);
        List<Long> commentIds = review.getComments()
                .stream()
                .map(comment -> comment.getId())
                .collect(Collectors.toList());
        List<Long> reviewAlbumIds = review.getReviewAlbums()
                .stream()
                .map(reviewAlbum -> reviewAlbum.getId())
                .collect(Collectors.toList());
        System.out.println(reviewAlbumIds);
        commentRepository.deleteAllByIdInQuery(commentIds);

        reviewAlbumService.deleteReviewAlbum(reviewAlbumIds);
        reviewRepository.delete(review);

    }

    public String createNewFilename(String filename){
        UUID uuid = UUID.randomUUID();
        String newFilename= uuid.toString() +"_" + filename;
        return newFilename;
    }


}
