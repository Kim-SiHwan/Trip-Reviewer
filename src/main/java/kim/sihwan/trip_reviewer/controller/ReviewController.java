package kim.sihwan.trip_reviewer.controller;

import kim.sihwan.trip_reviewer.dto.review.ReviewListResponseDto;
import kim.sihwan.trip_reviewer.dto.review.ReviewRequestDto;
import kim.sihwan.trip_reviewer.dto.review.ReviewResponseDto;
import kim.sihwan.trip_reviewer.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/review")
public class ReviewController {
    private final ReviewService reviewService;

    public List<ReviewListResponseDto> getReviews(Long tagId){
        List<ReviewListResponseDto> list = reviewService.findAllReviews(tagId);
        return list;
    }

    @GetMapping("/all/{tagId}")
    public ResponseEntity<ReviewListResponseDto> findAllReviews(@PathVariable Long tagId){
//        List<ReviewListResponseDto> result = getReviews(tagId);
        return new ResponseEntity(reviewService.findAllReviews(tagId),HttpStatus.OK);
    }

    @GetMapping("/{reviewId}")
    public ResponseEntity<ReviewResponseDto> findOneByReviewId(@PathVariable Long reviewId){
//        ReviewResponseDto reviewResponseDto = reviewService.findOneByReviewId(reviewId);
        return new ResponseEntity(reviewService.findOneByReviewId(reviewId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity addReview(@ModelAttribute ReviewRequestDto requestDto){
        reviewService.addReview(requestDto);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping(value = "/download", produces = {MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE})
    public ResponseEntity<Resource> downloadAlbumImage(@RequestParam("filename")String filename){
        Resource resource = new FileSystemResource("C:\\Users\\김시환\\Desktop\\Git\\Trip-Reviewer\\src\\main\\resources\\static\\reviewImages\\" + filename);
        return new ResponseEntity(resource,HttpStatus.OK);
    }


    @DeleteMapping("/{reviewId}")
    public void deleteReview(@PathVariable Long reviewId){
        reviewService.deleteReview(reviewId);
    }

    @GetMapping("/my/{username}")
    public ResponseEntity getReviewsByUsername(@PathVariable String username){
//        List<ReviewListResponseDto> result = reviewService.findAllReviewsByUsername(username);
        return new ResponseEntity(reviewService.findAllReviewsByUsername(username),HttpStatus.OK);
    }



}
