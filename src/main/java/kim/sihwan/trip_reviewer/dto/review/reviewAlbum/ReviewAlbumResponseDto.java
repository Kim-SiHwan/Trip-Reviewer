package kim.sihwan.trip_reviewer.dto.review.reviewAlbum;

import kim.sihwan.trip_reviewer.domain.ReviewAlbum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewAlbumResponseDto {
    private Long id;
    private String url;
    private String filename;
    private String originFilename;

    public ReviewAlbumResponseDto(ReviewAlbum reviewAlbum){
        id = reviewAlbum.getId();
        url = reviewAlbum.getUrl();
        filename = reviewAlbum.getFilename();
        originFilename = reviewAlbum.getOriginFilename();
    }
}
