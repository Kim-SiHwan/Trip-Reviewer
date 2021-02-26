package kim.sihwan.trip_reviewer.service;

import kim.sihwan.trip_reviewer.domain.Member;
import kim.sihwan.trip_reviewer.domain.Review;
import kim.sihwan.trip_reviewer.exception.cumtomException.DeletedReviewException;
import kim.sihwan.trip_reviewer.dto.review.ReviewListResponseDto;
import kim.sihwan.trip_reviewer.dto.review.ReviewRequestDto;
import kim.sihwan.trip_reviewer.dto.review.ReviewResponseDto;
import kim.sihwan.trip_reviewer.repository.MemberRepository;
import kim.sihwan.trip_reviewer.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewService {

    //결합도 줄이고싶음.
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

    @Cacheable(key = "#tagId", value = "reviewList", unless = "#result == null")
    public List<ReviewListResponseDto> findAllReviews(Long tagId) {

        if (tagId == 0) {

            return reviewRepository.findAll()
                    .stream()
                    .map(ReviewListResponseDto::toDto)
                    .sorted(Comparator.comparing(ReviewListResponseDto::getId, Comparator.reverseOrder()))
                    .collect(Collectors.toList());
        }

        //쿼리 줄일방법 생각해보자.
        return tagService.getReviewIdsByTagId(tagId)
                .stream()
                .map(reviewTag -> ReviewListResponseDto.toDto(reviewTag.getReview()))
                .sorted(Comparator.comparing(ReviewListResponseDto::getId, Comparator.reverseOrder()))
                .collect(Collectors.toList());
    }

    @Cacheable(key = "#reviewId", value = "review", unless = "#result == null")
    public ReviewResponseDto findOneByReviewId(Long reviewId) {
        return new ReviewResponseDto(reviewRepository.findReviewById(reviewId).orElseThrow(DeletedReviewException::new));
    }

    @Transactional
    @CacheEvict(key = "0", value = "reviewList")
    public Long addReview(ReviewRequestDto requestDto) {
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
        reviewRepository.deleteById(reviewId);
    }


}
