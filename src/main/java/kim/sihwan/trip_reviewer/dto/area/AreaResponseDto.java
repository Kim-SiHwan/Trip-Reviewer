package kim.sihwan.trip_reviewer.dto.area;

import kim.sihwan.trip_reviewer.domain.Area;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AreaResponseDto {

    private final Long id;
    private final int idx;
    private final String name;
    private final String title;
    private final String color;
    private final String accompany;
    private final String visitDate;

    public static AreaResponseDto toDto(Area area){
        return AreaResponseDto.builder()
                .id(area.getId())
                .idx(area.getIdx())
                .name(area.getName())
                .title(area.getTitle())
                .color(area.getColor())
                .accompany(area.getAccompany())
                .visitDate(area.getVisitDate())
                .build();
    }
}
