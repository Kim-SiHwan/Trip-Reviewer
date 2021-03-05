package kim.sihwan.trip_reviewer.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import kim.sihwan.trip_reviewer.dto.review.ReviewListResponseDto;
import kim.sihwan.trip_reviewer.dto.review.ReviewRequestDto;
import kim.sihwan.trip_reviewer.dto.review.ReviewResponseDto;
import kim.sihwan.trip_reviewer.dto.review.ReviewUpdateRequestDto;
import kim.sihwan.trip_reviewer.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(tags = {"4. Review"})
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/review")
public class ReviewController {
    private final ReviewService reviewService;


    @ApiOperation(value = "리뷰 전체 조회",notes = "리뷰에 작성된 태그 id로 해당 태그가 있는 모든 리뷰를 조회한다. 태그를 정하지 않는다면 0값을 넣는다.")
    @GetMapping("/all/{tagId}")
    public ResponseEntity<List<ReviewListResponseDto>> findAllReviews(@PathVariable Long tagId){
        return new ResponseEntity<>(reviewService.findAllReviews(tagId),HttpStatus.OK);
    }

    @ApiOperation(value = "리뷰 단건 조회",notes = "리뷰 id로 리뷰를 조회한다.")
    @GetMapping("/{reviewId}")
    public ResponseEntity<ReviewResponseDto> findOneByReviewId(@PathVariable Long reviewId){
        return new ResponseEntity<>(reviewService.findOneByReviewId(reviewId), HttpStatus.OK);
    }

    @ApiImplicitParam(name = "AUTHORIZATION", value = "Bearer +로그인 후 access_token", required = true, dataType = "String", paramType = "header", defaultValue = "Bearer ")
    @ApiOperation(value = "내가 쓴 리뷰 전체 조회",notes = "현재 로그인된 아이디로 자신이 쓴 모든 리뷰를 조회한다.")
    @GetMapping("/my/{username}")
    public ResponseEntity<List<ReviewListResponseDto>> getReviewsByUsername(@PathVariable String username){
        return new ResponseEntity<>(reviewService.findAllReviewsByUsername(username),HttpStatus.OK);
    }

    @ApiOperation(value = "리뷰 사진 조회",notes = "리뷰의 사진을 조회한다.")
    @GetMapping(value = "/download", produces = {MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE})
    public ResponseEntity<Resource> downloadAlbumImage(@RequestParam("filename")String filename){
        final String PATH = "C:\\Users\\김시환\\Desktop\\Git\\Trip-Reviewer\\src\\main\\resources\\images\\reviewImages\\"+filename;
        Resource resource = new FileSystemResource(PATH);
        return new ResponseEntity<>(resource,HttpStatus.OK);
    }

    @ApiImplicitParam(name = "AUTHORIZATION", value = "Bearer +로그인 후 access_token", required = true, dataType = "String", paramType = "header", defaultValue = "Bearer ")
    @ApiOperation(value = "리뷰 생성",notes = "리뷰 정보를 입력받아 리뷰를 생성한다. 리뷰 정보는 Set 구조의 태그, Set 구조의 리뷰앨범으로 구성되어있다.")
    @PostMapping
    public void addReview(@ModelAttribute @Valid ReviewRequestDto requestDto){
        reviewService.addReview(requestDto);
    }

    @ApiImplicitParam(name = "AUTHORIZATION", value = "Bearer +로그인 후 access_token", required = true, dataType = "String", paramType = "header", defaultValue = "Bearer ")
    @ApiOperation(value = "리뷰 삭제",notes = "리뷰 id로 리뷰를 삭제한다.")
    @DeleteMapping("/{reviewId}")
    public void deleteReview(@PathVariable Long reviewId){
        reviewService.deleteReview(reviewId);
    }

    @ApiImplicitParam(name = "AUTHORIZATION", value = "Bearer +로그인 후 access_token", required = true, dataType = "String", paramType = "header", defaultValue = "Bearer ")
    @ApiOperation(value = "리뷰 수정", notes = "리뷰 id로 리뷰를 수정한다. 사진은 변경할 수 없음.")
    @PatchMapping
    public void updateReview(@RequestBody ReviewUpdateRequestDto updateRequestDto){
        reviewService.updateReview(updateRequestDto);
    }




}
