package kim.sihwan.trip_reviewer.dto.review.reviewAlbum;

import kim.sihwan.trip_reviewer.domain.ReviewAlbum;
import lombok.Getter;

@Getter
public class ReviewAlbumResponseDto {
    private final Long id;
    private final String url;
    private final String originFilename;

    public ReviewAlbumResponseDto(ReviewAlbum reviewAlbum){
        id = reviewAlbum.getId();
        url = reviewAlbum.getUrl();
        originFilename = reviewAlbum.getOriginFilename();
    }
}
