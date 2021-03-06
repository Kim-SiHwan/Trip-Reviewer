package kim.sihwan.trip_reviewer.service;

import kim.sihwan.trip_reviewer.domain.Comment;
import kim.sihwan.trip_reviewer.domain.Review;
import kim.sihwan.trip_reviewer.dto.comment.CommentRequestDto;
import kim.sihwan.trip_reviewer.dto.comment.CommentResponseDto;
import kim.sihwan.trip_reviewer.dto.comment.CommentUpdateRequestDto;
import kim.sihwan.trip_reviewer.exception.cumtomException.CommentNotFoundException;
import kim.sihwan.trip_reviewer.exception.cumtomException.DifferentUsernameException;
import kim.sihwan.trip_reviewer.exception.cumtomException.ReviewNotFoundException;
import kim.sihwan.trip_reviewer.repository.CommentRepository;
import kim.sihwan.trip_reviewer.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
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




//     * @Slf4j << log4j,logback,log4j2

    public List<CommentResponseDto> findAllCommentsByUsername(String username) {
        return commentRepository.findAllByUsername(username)
                .stream()
                .map(CommentResponseDto::toDto)
                .collect(Collectors.toList());

    }

    public List<CommentResponseDto> findAllComments(Long reviewId) {
        return commentRepository.findAllByReview_Id(reviewId)
                .stream()
                .map(CommentResponseDto::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public void addComment(CommentRequestDto requestDto) {
        Long reviewId = requestDto.getReviewId();
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(ReviewNotFoundException::new);
        Comment comment = requestDto.toEntity(requestDto);
        comment.addReview(review);

    }

    @Transactional
    public void updateComment(CommentUpdateRequestDto updateRequestDto) {
        Comment comment = commentRepository.findById(updateRequestDto.getId())
                .orElseThrow(CommentNotFoundException::new);
        comment.changeContent(updateRequestDto.getContent());

    }

    @Transactional
    public void deleteComment(Long commentId) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(CommentNotFoundException::new);
        if(!username.equals("admin4166") && !username.equals(comment.getUsername())){
            throw new DifferentUsernameException();
        }commentRepository.delete(comment);

    }

}
