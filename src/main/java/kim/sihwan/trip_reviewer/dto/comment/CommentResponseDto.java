package kim.sihwan.trip_reviewer.dto.comment;

import kim.sihwan.trip_reviewer.domain.Comment;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CommentResponseDto {
    private final Long id;
    private final String content;
    private final String username;
    private final String createDate;


    public static CommentResponseDto toDto(Comment comment){
        return CommentResponseDto
                .builder()
                .id(comment.getId())
                .content(comment.getContent())
                .username(comment.getUsername())
                .createDate(comment.getCreateDate().toString())
                .build();
    }
}
