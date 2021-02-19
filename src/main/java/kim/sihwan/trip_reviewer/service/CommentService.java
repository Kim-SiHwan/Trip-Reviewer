package kim.sihwan.trip_reviewer.service;

import kim.sihwan.trip_reviewer.domain.Comment;
import kim.sihwan.trip_reviewer.domain.Review;
import kim.sihwan.trip_reviewer.dto.comment.CommentRequestDto;
import kim.sihwan.trip_reviewer.dto.comment.CommentResponseDto;
import kim.sihwan.trip_reviewer.repository.CommentRepository;
import kim.sihwan.trip_reviewer.repository.MemberRepository;
import kim.sihwan.trip_reviewer.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public List<CommentResponseDto> findAllComments(Long reviewId){
        List<Comment> comments = commentRepository.findAll();
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
        Comment comment = requestDto.toEntity(requestDto);
        comment.addReview(review);
        commentRepository.save(comment);
    }

    @Transactional
    public void deleteComment(Long commentId){
        //이것도 쿼리 2번 나가는게 싫으면 쿼리작성 해보기
        Comment comment = commentRepository.findById(commentId).get();
        commentRepository.delete(comment);
    }
}
