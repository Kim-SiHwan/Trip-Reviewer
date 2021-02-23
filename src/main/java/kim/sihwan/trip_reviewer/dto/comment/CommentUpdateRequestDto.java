package kim.sihwan.trip_reviewer.dto.comment;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class CommentUpdateRequestDto {
    @NotEmpty(message = "댓글아이디는 필수 요소입니다.")
    private Long id;

    @NotEmpty(message = "리뷰아이디는 필수 요소입니다.")
    private Long reviewId;

    @NotEmpty(message = "수정할 댓글 내용은 필수 요소입니다.")
    private String content;
}
