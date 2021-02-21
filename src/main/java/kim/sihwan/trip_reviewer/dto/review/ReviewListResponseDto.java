package kim.sihwan.trip_reviewer.dto.review;

import kim.sihwan.trip_reviewer.domain.Review;
import kim.sihwan.trip_reviewer.dto.review.reviewAlbum.ReviewAlbumResponseDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class ReviewListResponseDto {
    private Long id;
    private String area;
    private String username;
    private String title;
    private String content;
    private String createDate;
    private String thumbnail;
    private int reviewAlbumsCount;
    private int commentCount;
    public ReviewListResponseDto (Review review){
        System.out.println("리뷰리스트 디티오!!! : " +review.getReviewAlbums().size());
        id = review.getId();
        area = review.getArea();
        username = review.getMember().getUsername();
        title = review.getTitle();
        content = review.getContent();
        createDate = review.getCreateDate().toString();
        thumbnail = review.getThumbnail();
        reviewAlbumsCount = review.getReviewAlbums().size();
        commentCount = review.getComments().size();
    }
}