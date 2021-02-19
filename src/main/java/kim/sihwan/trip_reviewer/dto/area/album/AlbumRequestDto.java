package kim.sihwan.trip_reviewer.dto.area.album;

import kim.sihwan.trip_reviewer.domain.Album;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AlbumRequestDto {
    private Long areaId;
    private String url;
    private String filename;
    private String originFilename;

    public Album toEntity(AlbumRequestDto requestDto){
        return Album.builder()
                .url(requestDto.getUrl())
                .filename(requestDto.getFilename())
                .originFilename(requestDto.getOriginFilename())
                .build();
    }

}
