package kim.sihwan.trip_reviewer.dto.area.album;

import kim.sihwan.trip_reviewer.domain.Album;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AlbumResponseDto {
    private Long id;
    private String url;
    private String originFilename;

    public static AlbumResponseDto toDto(Album album){
        return AlbumResponseDto
                .builder()
                .id(album.getId())
                .url(album.getUrl())
                .originFilename(album.getOriginFilename())
                .build();
    }
}
