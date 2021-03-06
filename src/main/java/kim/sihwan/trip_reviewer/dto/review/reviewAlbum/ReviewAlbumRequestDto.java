package kim.sihwan.trip_reviewer.dto.review.reviewAlbum;

import kim.sihwan.trip_reviewer.domain.ReviewAlbum;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class ReviewAlbumRequestDto {
    @NotEmpty(message = "리뷰아이디는 필수 항목입니다.")
    private Long reviewId;

    private String url;
    private String originFilename;

    public ReviewAlbum toEntity(ReviewAlbumRequestDto requestDto){
        return ReviewAlbum.builder()
                .url(requestDto.getUrl())
                .originFilename(requestDto.getOriginFilename())
                .build();
    }

}
