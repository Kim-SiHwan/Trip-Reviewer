package kim.sihwan.trip_reviewer.dto.tag;

import kim.sihwan.trip_reviewer.domain.Tag;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TagResponseDto {
    private Long id;
    private String tag;

    public TagResponseDto (Tag tag) {
        this.id = tag.getId();
        //여기서 쿼리 한번씩.
        //총 게시글만큼의 태그가 나갈것으로 예쌍됨.
        //태그 조회는 딱 1번만 되는걸로 확인했음. 02-20-04:33기준, 
        this.tag = tag.getTag();
    }
}
