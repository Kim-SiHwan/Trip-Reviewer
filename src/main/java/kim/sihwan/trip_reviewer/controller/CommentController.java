package kim.sihwan.trip_reviewer.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import kim.sihwan.trip_reviewer.dto.comment.CommentRequestDto;
import kim.sihwan.trip_reviewer.dto.comment.CommentResponseDto;
import kim.sihwan.trip_reviewer.dto.comment.CommentUpdateRequestDto;
import kim.sihwan.trip_reviewer.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(tags = "5. Comment")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comment")
public class CommentController {
    private final CommentService commentService;


    @ApiOperation(value = "댓글 전체 조회",notes = "리뷰 id로 해당 리뷰에 달린 모든 댓글을 조회한다.")
    @GetMapping("/{reviewId}")
    public ResponseEntity<List<CommentResponseDto>> findAllCommentsByReviewId(@PathVariable("reviewId")Long reviewId){
        return new ResponseEntity<>(commentService.findAllComments(reviewId),HttpStatus.OK);
    }

    @ApiImplicitParam(name = "AUTHORIZATION", value = "Bearer +로그인 후 access_token", required = true, dataType = "String", paramType = "header", defaultValue = "Bearer ")
    @ApiOperation(value = "내가 쓴 댓글 전체 조회",notes = "현재 로그인된 아이디로 자신이 쓴 모든 댓글을 조회한다.")
    @GetMapping("/my/{username}")
    public ResponseEntity<List<CommentResponseDto>> getAllCommentsByUsername(@PathVariable String username){
        return new ResponseEntity<>(commentService.findAllCommentsByUsername(username),HttpStatus.OK);
    }

    @ApiImplicitParam(name = "AUTHORIZATION", value = "Bearer +로그인 후 access_token", required = true, dataType = "String", paramType = "header", defaultValue = "Bearer ")
    @ApiOperation(value = "댓글 생성",notes = "댓글 정보를 입력받아 댓글을 작성한다.")
    @PostMapping
    public void addComment(@RequestBody @Valid CommentRequestDto requestDto){
        commentService.addComment(requestDto);
    }


    @ApiImplicitParam(name = "AUTHORIZATION", value = "Bearer +로그인 후 access_token", required = true, dataType = "String", paramType = "header", defaultValue = "Bearer ")
    @ApiOperation(value = "댓글 수정",notes = "변경할 정보를 입력받아 댓글을 수정한다.")
    @PutMapping
    public void updateComment(@RequestBody @Valid CommentUpdateRequestDto updateRequestDto){
        commentService.updateComment(updateRequestDto);
    }

    @ApiImplicitParam(name = "AUTHORIZATION", value = "Bearer +로그인 후 access_token", required = true, dataType = "String", paramType = "header", defaultValue = "Bearer ")
    @ApiOperation(value = "댓글 삭제",notes = "댓글 id로 리뷰를 삭제한다.")
    @DeleteMapping("/{commentId}")
    public void deleteComment(@PathVariable("commentId")Long commentId){
        commentService.deleteComment(commentId);
    }


}
