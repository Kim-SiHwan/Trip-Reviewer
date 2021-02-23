package kim.sihwan.trip_reviewer.controller;

import kim.sihwan.trip_reviewer.dto.comment.CommentRequestDto;
import kim.sihwan.trip_reviewer.dto.comment.CommentResponseDto;
import kim.sihwan.trip_reviewer.dto.comment.CommentUpdateRequestDto;
import kim.sihwan.trip_reviewer.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comment")
public class CommentController {
    private final CommentService commentService;

    private List<CommentResponseDto> getComments(Long reviewId){
        return commentService.findAllComments(reviewId);
    }

    @GetMapping("/{reviewId}")
    public ResponseEntity<CommentResponseDto> findAllCommentsByReviewId(@PathVariable("reviewId")Long reviewId){
        return new ResponseEntity(commentService.findAllComments(reviewId),HttpStatus.OK);
    }

    @GetMapping("/my/{username}")
    public ResponseEntity<CommentResponseDto> getAllCommentsByUsername(@PathVariable("username")String username){
        return new ResponseEntity(commentService.findAllCommentsByUsername(username),HttpStatus.OK);
    }

    @PostMapping
    public void addComment(@RequestBody CommentRequestDto requestDto){
        commentService.addComment(requestDto);
    }


    @PutMapping
    public ResponseEntity updateComment(@RequestBody CommentUpdateRequestDto updateRequestDto){
        commentService.updateComment(updateRequestDto);
        return new ResponseEntity(getComments(updateRequestDto.getReviewId()),HttpStatus.OK);
    }

    @DeleteMapping("/{commentId}")
    public void deleteComment(@PathVariable("commentId")Long commentId){
        commentService.deleteComment(commentId);
    }


}
