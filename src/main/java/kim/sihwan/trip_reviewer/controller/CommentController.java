package kim.sihwan.trip_reviewer.controller;

import kim.sihwan.trip_reviewer.dto.comment.CommentRequestDto;
import kim.sihwan.trip_reviewer.dto.comment.CommentResponseDto;
import kim.sihwan.trip_reviewer.dto.comment.CommentUpdateRequestDto;
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

    public List<CommentResponseDto> getComments(Long reviewId){
        return commentService.findAllComments(reviewId);
    }


    @GetMapping("/{reviewId}")
    public ResponseEntity findAllCommentsByReviewId(@PathVariable("reviewId")Long reviewId){
        List<CommentResponseDto> result = getComments(reviewId);
        return new ResponseEntity(result,HttpStatus.OK);
    }


    @PostMapping
    public ResponseEntity addComment(@RequestBody CommentRequestDto requestDto){
        commentService.addComment(requestDto);
        return new ResponseEntity(getComments(requestDto.getReviewId()),HttpStatus.OK);
    }

    @DeleteMapping("/{reviewId}/{commentId}")
    public ResponseEntity deleteComment(@PathVariable("reviewId")Long reviewId,
                                        @PathVariable("commentId")Long commentId){
        commentService.deleteComment(commentId);
        return new ResponseEntity(getComments(reviewId),HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity updateComment(@RequestBody CommentUpdateRequestDto updateRequestDto){
        commentService.updateComment(updateRequestDto);
        return new ResponseEntity(getComments(updateRequestDto.getReviewId()),HttpStatus.OK);
    }

    @GetMapping("/my/{username}")
    public ResponseEntity getAllCommentsByUsername(@PathVariable("username")String username){
        List<CommentResponseDto> result = commentService.findAllCommentsByUsername(username);
        return new ResponseEntity(result,HttpStatus.OK);
    }

}
