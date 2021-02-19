package kim.sihwan.trip_reviewer.controller;

import kim.sihwan.trip_reviewer.dto.comment.CommentRequestDto;
import kim.sihwan.trip_reviewer.dto.comment.CommentResponseDto;
import kim.sihwan.trip_reviewer.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comment")
public class CommentController {
    private final CommentService commentService;

    @GetMapping("/{commentId}")
    public ResponseEntity findOneByCommentId(@PathVariable("commentId")Long commentId){
        CommentResponseDto result = commentService.findOneByCommentId(commentId);
        return new ResponseEntity(result, HttpStatus.OK);
    }

    //생성은 지금 어쩔수없이 3개 나가는게 정상임.
    @PostMapping
    public ResponseEntity addComment(@RequestBody CommentRequestDto requestDto){
        commentService.addComment(requestDto);
        //이건 변경된 코멘트만 넘기니까 vue에서 computed할 때 response.data를 reviewInfo.commentList = data이런식으로 바꿔
//        List<CommentResponseDto> result = commentService.findAllComments(requestDto.getReviewId());
        return new ResponseEntity(HttpStatus.OK);
    }

    //얘도 3번, 왜 3번 ? 코멘트 찾기, 코멘트 지우기, 지워진 상태의 코멘트 찾기.
    @DeleteMapping("/{reviewId}/{commentId}")
    public ResponseEntity deleteComment(@PathVariable("reviewId")Long reviewId,
                                        @PathVariable("commentId")Long commentId){
        commentService.deleteComment(commentId);
        List<CommentResponseDto> result = commentService.findAllComments(reviewId);
        return new ResponseEntity(result,HttpStatus.OK);
    }

}
