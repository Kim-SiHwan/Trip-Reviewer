package kim.sihwan.trip_reviewer.dto.comment;

import kim.sihwan.trip_reviewer.domain.Comment;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Getter
@Setter
public class CommentRequestDto {
    private Long reviewId;
    @NotEmpty(message = "작성자는 필수 항목입니다.")
    private String username;
    @NotEmpty(message = "댓글 내용은 필수 항목입니다.")
    @Size(min = 1 , max = 50, message = "댓글 내용은 1 ~ 50자 이내로 작성해야 합니다.")
    private String content;

    public Comment toEntity(CommentRequestDto requestDto){
        return Comment.builder()
                .content(requestDto.getContent())
                .username(requestDto.getUsername())
                .createDate(LocalDateTime.now())
                .build();
    }
}
