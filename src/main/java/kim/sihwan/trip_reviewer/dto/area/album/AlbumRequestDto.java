package kim.sihwan.trip_reviewer.dto.area.album;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
public class AlbumRequestDto {

    @NotNull(message = "지역 아이디는 필수 항목입니다.")
    private Long areaId;
    @NotNull(message = "1장이상의 사진은 필수 항목입니다.")
    private List<MultipartFile> files;




}
