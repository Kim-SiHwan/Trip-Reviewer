package kim.sihwan.trip_reviewer.dto.area;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AreaRequestDto {

    private Long areaId;
    private String title;
    private String color;
    private String accompany;
    private String visitDate;
}
