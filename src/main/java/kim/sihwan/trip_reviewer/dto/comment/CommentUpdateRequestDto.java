package kim.sihwan.trip_reviewer.dto.comment;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class CommentUpdateRequestDto {

    private Long id;

    private Long reviewId;

    @NotEmpty(message = "수정할 댓글 내용은 필수 항목입니다.")
    private String content;
}
