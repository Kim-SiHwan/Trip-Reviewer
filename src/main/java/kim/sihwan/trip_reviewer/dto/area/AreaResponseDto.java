package kim.sihwan.trip_reviewer.dto.area;

import kim.sihwan.trip_reviewer.domain.Area;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AreaResponseDto {
    private Long id;
    private int idx;
    private String name;
    private String title;
    private String color;
    private String accompany;
    private String visitDate;

    public AreaResponseDto(Area area){
        id = area.getId();
        idx = area.getIdx();
        name= area.getName();
        title = area.getTitle();
        color = area.getColor();
        accompany = area.getAccompany();
        visitDate = area.getVisitDate();
    }

}
