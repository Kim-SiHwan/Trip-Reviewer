package kim.sihwan.trip_reviewer.dto.review.reviewAlbum;

import kim.sihwan.trip_reviewer.domain.ReviewAlbum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewAlbumRequestDto {
    private Long reviewId;
    private String url;
    private String filename;
    private String originFilename;

    public ReviewAlbum toEntity(ReviewAlbumRequestDto requestDto){
        return ReviewAlbum.builder()
                .url(requestDto.getUrl())
                .filename(requestDto.getFilename())
                .originFilename(requestDto.getOriginFilename())
                .build();
    }

}
