package kim.sihwan.trip_reviewer.service;

import kim.sihwan.trip_reviewer.domain.Member;
import kim.sihwan.trip_reviewer.domain.Review;
import kim.sihwan.trip_reviewer.dto.review.ReviewUpdateRequestDto;
import kim.sihwan.trip_reviewer.exception.cumtomException.DeletedReviewException;
import kim.sihwan.trip_reviewer.dto.review.ReviewListResponseDto;
import kim.sihwan.trip_reviewer.dto.review.ReviewRequestDto;
import kim.sihwan.trip_reviewer.dto.review.ReviewResponseDto;
import kim.sihwan.trip_reviewer.exception.cumtomException.DifferentUsernameException;
import kim.sihwan.trip_reviewer.exception.cumtomException.ReviewNotFoundException;
import kim.sihwan.trip_reviewer.exception.cumtomException.UserNotFoundException;
import kim.sihwan.trip_reviewer.repository.MemberRepository;
import kim.sihwan.trip_reviewer.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final MemberRepository memberRepository;
    private final ReviewAlbumService reviewAlbumService;
    private final TagService tagService;


    public List<ReviewListResponseDto> findAllReviewsByUsername(String username) {
        List<Review> reviews = reviewRepository.findAllByMember_Username(username);
        List<ReviewListResponseDto> list = reviews
                .stream()
                .map(ReviewListResponseDto::toDto)
                .collect(Collectors.toList());
        Collections.reverse(list);
        return list;
    }

    public List<ReviewListResponseDto> findAllReviews(Long tagId) {

        if (tagId == 0) {
            return reviewRepository.findAll()
                    .stream()
                    .map(ReviewListResponseDto::toDto)
                    .sorted(Comparator.comparing(ReviewListResponseDto::getId, Comparator.reverseOrder()))
                    .collect(Collectors.toList());
        }

        return tagService.getReviewIdsByTagId(tagId)
                .stream()
                .map(reviewTag -> ReviewListResponseDto.toDto(reviewTag.getReview()))
                .sorted(Comparator.comparing(ReviewListResponseDto::getId, Comparator.reverseOrder()))
                .collect(Collectors.toList());
    }

    public ReviewResponseDto findOneByReviewId(Long reviewId) {
        return ReviewResponseDto.toDto(reviewRepository.findReviewById(reviewId).orElseThrow(DeletedReviewException::new));
    }

    @Transactional
    public void addReview(ReviewRequestDto requestDto) throws IOException {
        Member member = memberRepository.findMemberByUsername(requestDto.getUsername())
                .orElseThrow(UserNotFoundException::new);
        Review review = reviewAlbumService.addReviewAlbums(requestDto);
        tagService.addReviewTag(review, requestDto);
        review.addMember(member);
        reviewRepository.save(review);
    }

    @Transactional
    public void deleteReview(Long reviewId) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(ReviewNotFoundException::new);
        if(!username.equals("admin4166") && !username.equals(review.getMember().getUsername())){
            throw new DifferentUsernameException();
        }
        reviewRepository.delete(review);
    }

    @Transactional
    public void updateReview(ReviewUpdateRequestDto updateRequestDto) {
        Review review = reviewRepository.findById(updateRequestDto.getReviewId())
                .orElseThrow(ReviewNotFoundException::new);
        if (!updateRequestDto.getTitle().isEmpty()) {
            review.changeTitle(updateRequestDto.getTitle());
        }
        if (!updateRequestDto.getContent().isEmpty()) {
            review.changeContent(updateRequestDto.getContent());
        }
    }
}
