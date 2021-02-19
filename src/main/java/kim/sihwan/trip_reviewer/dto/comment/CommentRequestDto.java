package kim.sihwan.trip_reviewer.dto.comment;

import kim.sihwan.trip_reviewer.domain.Comment;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CommentRequestDto {
    private Long reviewId;
    private String username;
    private String content;

    public Comment toEntity(CommentRequestDto requestDto){
        return Comment.builder()
                .content(requestDto.getContent())
                .username(requestDto.getUsername())
                .createDate(LocalDateTime.now())
                .build();
    }
}
