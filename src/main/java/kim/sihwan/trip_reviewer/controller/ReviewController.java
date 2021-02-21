package kim.sihwan.trip_reviewer.controller;

import kim.sihwan.trip_reviewer.dto.review.ReviewListResponseDto;
import kim.sihwan.trip_reviewer.dto.review.ReviewRequestDto;
import kim.sihwan.trip_reviewer.dto.review.ReviewResponseDto;
import kim.sihwan.trip_reviewer.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/review")
public class ReviewController {
    private final ReviewService reviewService;

    public List<ReviewListResponseDto> getReviews(){
        List<ReviewListResponseDto> list = reviewService.findAllReviews();
        return list;
    }

    @GetMapping
    public ResponseEntity findAllReviews(){
        List<ReviewListResponseDto> result = getReviews();
        return new ResponseEntity(result,HttpStatus.OK);
    }

    @GetMapping("/{reviewId}")
    public ResponseEntity findOneByReviewId(@PathVariable("reviewId")Long reviewId){
        ReviewResponseDto reviewResponseDto = reviewService.findOneByReviewId(reviewId);
        return new ResponseEntity(reviewResponseDto, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity addReview(@ModelAttribute ReviewRequestDto requestDto){
        reviewService.addReview(requestDto);
        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity deleteReview(@PathVariable("reviewId") Long reviewId){
        reviewService.deleteReview(reviewId);
        return new ResponseEntity(HttpStatus.OK);
    }



}