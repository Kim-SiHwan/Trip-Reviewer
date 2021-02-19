package kim.sihwan.trip_reviewer.dto.review;

import kim.sihwan.trip_reviewer.domain.Review;
import kim.sihwan.trip_reviewer.dto.review.reviewAlbum.ReviewAlbumResponseDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class ReviewResponseDto {
    private Long id;
    private String area;
    private String username;
    private String title;
    private String content;
    private String createDate;
    private List<ReviewAlbumResponseDto> reviewAlbums;
    public ReviewResponseDto (Review review){
        id = review.getId();
        area = review.getArea();
        username = review.getMember().getUsername();
        title = review.getTitle();
        content = review.getContent();
        createDate = review.getCreateDate().toString();
        reviewAlbums = review.getReviewAlbums()
                .stream()
                .map(m->new ReviewAlbumResponseDto(m))
                .collect(Collectors.toList());
    }
}
