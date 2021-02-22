package kim.sihwan.trip_reviewer.dto.comment;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentUpdateRequestDto {
    private Long id;
    private Long reviewId;
    private String content;
}
