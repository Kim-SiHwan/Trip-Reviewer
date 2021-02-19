package kim.sihwan.trip_reviewer.dto.area.album;

import kim.sihwan.trip_reviewer.domain.Album;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AlbumResponseDto {
    private Long id;
    private String url;
    private String filename;
    private String originFilename;

    public AlbumResponseDto(Album album){
        id= album.getId();;
        url= album.getUrl();
        filename= album.getFilename();
        originFilename= album.getOriginFilename();
    }
}
