package kim.sihwan.trip_reviewer.service;

import kim.sihwan.trip_reviewer.domain.Comment;
import kim.sihwan.trip_reviewer.domain.Review;
import kim.sihwan.trip_reviewer.dto.comment.CommentRequestDto;
import kim.sihwan.trip_reviewer.dto.comment.CommentResponseDto;
import kim.sihwan.trip_reviewer.dto.comment.CommentUpdateRequestDto;
import kim.sihwan.trip_reviewer.repository.CommentRepository;
import kim.sihwan.trip_reviewer.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {
    private final CommentRepository commentRepository;
    private final ReviewRepository reviewRepository;

    public CommentResponseDto findOneByCommentId(Long commentId){
        Comment comment = commentRepository.findById(commentId).get();
        CommentResponseDto result = new CommentResponseDto(comment);
        return result;
    }

    public List<CommentResponseDto> findAllCommentsByUsername(String username){
        List<Comment> comments = commentRepository.findAllByUsername(username);
        System.out.println("코멘트 서비스 : "+username);
        List<CommentResponseDto> list = new ArrayList<>();
        list = comments
                .stream()
                .map(comment -> new CommentResponseDto(comment))
                .collect(Collectors.toList());
        list.forEach(l-> System.out.println("리스트 :" +l.getUsername()+" "+l.getContent()));
        return list;

    }

    public List<CommentResponseDto> findAllComments(Long reviewId){
        List<Comment> comments = commentRepository.findAllByReview_Id(reviewId);
        List<CommentResponseDto> list = comments
                .stream()
                .map(comment -> new CommentResponseDto(comment))
                .collect(Collectors.toList());
        return list;
    }

    @Transactional
    public void addComment(CommentRequestDto requestDto){
        Long reviewId = requestDto.getReviewId();
        Review review = reviewRepository.findById(reviewId).get();
        System.out.println("애드코멘트 : "+review.getReviewAlbums().size());
        Comment comment = requestDto.toEntity(requestDto);
        comment.addReview(review);
    }

    @Transactional
    public void updateComment(CommentUpdateRequestDto updateRequestDto){
        Comment comment = commentRepository.findById(updateRequestDto.getId()).get();
        comment.changeContent(updateRequestDto.getContent());

    }

    @Transactional
    public void deleteComment(Long commentId){
        Comment comment = commentRepository.findById(commentId).get();
        commentRepository.delete(comment);
    }

    @Transactional
    public void deleteAllWithReview(List<Long> commentIdList){
        commentRepository.deleteAllByIdInQuery(commentIdList);
    }


}
