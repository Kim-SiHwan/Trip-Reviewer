package kim.sihwan.trip_reviewer.service;

import kim.sihwan.trip_reviewer.domain.Member;
import kim.sihwan.trip_reviewer.domain.Review;
import kim.sihwan.trip_reviewer.domain.ReviewTag;
import kim.sihwan.trip_reviewer.dto.review.ReviewListResponseDto;
import kim.sihwan.trip_reviewer.dto.review.ReviewRequestDto;
import kim.sihwan.trip_reviewer.dto.review.ReviewResponseDto;
import kim.sihwan.trip_reviewer.repository.MemberRepository;
import kim.sihwan.trip_reviewer.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final MemberRepository memberRepository;
    private final ReviewAlbumService reviewAlbumService;
    private final CommentService commentService;
    private final TagService tagService;


    @Cacheable(key = "#username" ,value = "myReviewList",unless = "#result == null")
    public List<ReviewListResponseDto> findAllReviewsByUsername(String username){
        List<Review> reviews = reviewRepository.findAllByMember_Username(username);
        List<ReviewListResponseDto> list = new ArrayList<>();
        list = reviews
                .stream()
                .map(review-> new ReviewListResponseDto(review))
                .collect(Collectors.toList());
        Collections.reverse(list);
        return list;
    }

    @Cacheable(key = "#tagId" ,value = "reviewList",unless = "#result == null")
    public List<ReviewListResponseDto> findAllReviews(Long tagId){
        List<ReviewListResponseDto> list = new ArrayList<>();
        if(tagId==0){
            List<Review> reviews = reviewRepository.findAll();
            list = reviews
                    .stream()
                    .map(review -> new ReviewListResponseDto(review))
                    .collect(Collectors.toList());

        }else{
            //쿼리 줄일방법 생각해보자.
            List<ReviewTag> reviewTags = tagService.getReviewIdsByTagId(tagId);
            list = reviewTags
                    .stream()
                    .map(reviewTag -> {
                        Review review = reviewTag.getReview();
                        return new ReviewListResponseDto(review);
                    })
                    .collect(Collectors.toList());

        }
        Collections.reverse(list);
        return list;
    }
    @Cacheable(key = "#reviewId" , value = "review" , unless = "#result == null")
    public ReviewResponseDto findOneByReviewId(Long reviewId){
        Review review = reviewRepository.findReviewById(reviewId);
        ReviewResponseDto reviewResponseDto = new ReviewResponseDto(review);
        return reviewResponseDto;
    }

    @Transactional
    @CacheEvict(key = "0" , value = "reviewList")
    public Long addReview(ReviewRequestDto requestDto){
        Member member = memberRepository.findMemberByUsername(requestDto.getUsername()).get();
        Review review = reviewAlbumService.addReviewAlbums(requestDto);
        tagService.addReviewTag(review, requestDto);
        review.addMember(member);
        reviewRepository.save(review);
        return review.getId();
    }

    @Transactional
    @Caching(evict = {
            @CacheEvict(key = "#reviewId", value = "review"),
            @CacheEvict(key = "0", value = "reviewList")
    })
    public void deleteReview(Long reviewId) {
        //효율적인지는 모르겠으나
        //기존 delete로 지우면
        // 태그의 수, 댓글의 수, 앨범의 수 만큼 3N의 삭제 쿼리가 나가지만
        //태그에서 1번, 댓글에서 1번, 앨범에서 1번 딱 3번의 삭제 쿼리가 나감.
        Review review = reviewRepository.findReviewById(reviewId);
        if (SecurityContextHolder.getContext().getAuthentication().getName().equals(review.getMember().getUsername())) {

            List<Long> commentIds = review.getComments()
                    .stream()
                    .map(comment -> comment.getId())
                    .collect(Collectors.toList());

            List<Long> reviewAlbumIds = review.getReviewAlbums()
                    .stream()
                    .map(reviewAlbum -> reviewAlbum.getId())
                    .collect(Collectors.toList());

            List<Long> reviewTagIds = review.getReviewTags()
                    .stream()
                    .map(reviewTag -> reviewTag.getId())
                    .collect(Collectors.toList());

            System.out.println(reviewAlbumIds);


            tagService.deleteAllReviewTagWithReview(reviewTagIds);
            commentService.deleteAllWithReview(commentIds);
            reviewAlbumService.deleteReviewAlbum(reviewAlbumIds);
            reviewRepository.delete(review);

        }
    }




}
