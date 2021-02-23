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


    @GetMapping("/all/{tagId}")
    public ResponseEntity<List<ReviewListResponseDto>> findAllReviews(@PathVariable Long tagId){
        return new ResponseEntity<>(reviewService.findAllReviews(tagId),HttpStatus.OK);
    }

    @GetMapping("/{reviewId}")
    public ResponseEntity<ReviewResponseDto> findOneByReviewId(@PathVariable Long reviewId){
        return new ResponseEntity<>(reviewService.findOneByReviewId(reviewId), HttpStatus.OK);
    }

    @GetMapping("/my/{username}")
    public ResponseEntity<List<ReviewListResponseDto>> getReviewsByUsername(@PathVariable String username){
        return new ResponseEntity<>(reviewService.findAllReviewsByUsername(username),HttpStatus.OK);
    }

    @GetMapping(value = "/download", produces = {MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE})
    public ResponseEntity<Resource> downloadAlbumImage(@RequestParam("filename")String filename){
        final String PATH = "C:\\Users\\김시환\\Desktop\\Git\\Trip-Reviewer\\src\\main\\resources\\static\\reviewImages\\"+filename;
        Resource resource = new FileSystemResource(PATH);
        return new ResponseEntity<>(resource,HttpStatus.OK);
    }

    @PostMapping
    public void addReview(@ModelAttribute ReviewRequestDto requestDto){
        reviewService.addReview(requestDto);
    }




    @DeleteMapping("/{reviewId}")
    public void deleteReview(@PathVariable Long reviewId){
        reviewService.deleteReview(reviewId);
    }




}
