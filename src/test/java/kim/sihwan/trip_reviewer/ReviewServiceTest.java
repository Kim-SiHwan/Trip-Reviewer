/*
package kim.sihwan.trip_reviewer;

import kim.sihwan.trip_reviewer.domain.Member;
import kim.sihwan.trip_reviewer.dto.review.ReviewRequestDto;
import kim.sihwan.trip_reviewer.dto.review.ReviewResponseDto;
import kim.sihwan.trip_reviewer.repository.MemberRepository;
import kim.sihwan.trip_reviewer.service.ReviewService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;

@SpringBootTest
public class ReviewServiceTest {
    @Autowired
    ReviewService reviewService;
    @Autowired
    MemberRepository memberRepository;


    @Test
    void 인증없이_리뷰생성() {
        //given
        //기존에 생성되어있는 멤버이름 = kim,user
        Member member = memberRepository.findById(1L).get();
        ReviewRequestDto reviewRequestDto = new ReviewRequestDto();
        Set<String> tags = Set.of("태그1","태그2");
        reviewRequestDto.setArea("부산");
        reviewRequestDto.setContent("부산에 놀러왔습니다!");
        reviewRequestDto.setTags(tags);
        reviewRequestDto.setTitle("부산여행");
        reviewRequestDto.setUsername(member.getUsername());

        //when
        Long reviewId = reviewService.addReview(reviewRequestDto);
        ReviewResponseDto review = reviewService.findOneByReviewId(reviewId);
        //then
        assertEquals("부산", review.getArea());
        assertEquals("부산여행", review.getTitle());
        assertEquals("부산에 놀러왔습니다!", review.getContent());
        assertEquals("태그1", review.getTags().get(0).getTag());
        assertEquals("태그2", review.getTags().get(1).getTag());

    }

    @Test
    void 인증없이_리뷰삭제() {
        //given
        //기존에 생성되어있는 멤버이름 = kim,user
        Member member = memberRepository.findById(1L).get();
        ReviewRequestDto reviewRequestDto = new ReviewRequestDto();
        Set<String> tags = Set.of("태그1", "태그");

        reviewRequestDto.setArea("부산");
        reviewRequestDto.setContent("부산에 놀러왔습니다!");
        reviewRequestDto.setTags(tags);
        reviewRequestDto.setTitle("부산여행");
        reviewRequestDto.setUsername(member.getUsername());

        //when
        Long reviewId = reviewService.addReview(reviewRequestDto);
        reviewService.deleteReview(reviewId);

        //then
        //에러발생할것임.
        reviewService.findOneByReviewId(reviewId);

    }


}
*/
