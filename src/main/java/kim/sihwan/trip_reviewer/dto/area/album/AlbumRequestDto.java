package kim.sihwan.trip_reviewer.dto.area.album;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
public class AlbumRequestDto {
    private Long areaId;
    private List<MultipartFile> files;

}
