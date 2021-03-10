package kim.sihwan.trip_reviewer.dto.area.album;

import kim.sihwan.trip_reviewer.domain.Album;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AlbumResponseDto {
    private final Long id;
    private final String url;
    private final String originFilename;

    public static AlbumResponseDto toDto(Album album){
        return AlbumResponseDto
                .builder()
                .id(album.getId())
                .url(album.getUrl())
                .originFilename(album.getOriginFilename())
                .build();
    }
}
