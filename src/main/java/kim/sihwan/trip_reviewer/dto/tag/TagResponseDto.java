
package kim.sihwan.trip_reviewer.dto.tag;

import kim.sihwan.trip_reviewer.domain.Tag;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TagResponseDto {
    private Long id;
    private String tag;

    public TagResponseDto(Tag tag) {
        this.id = tag.getId();
        this.tag = tag.getTag();
    }
}
