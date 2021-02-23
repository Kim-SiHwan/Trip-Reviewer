package kim.sihwan.trip_reviewer.dto.comment;

import kim.sihwan.trip_reviewer.domain.Comment;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CommentResponseDto {
    private Long id;
    private String content;
    private String username;//닉네임으로?
    private String createDate;


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
