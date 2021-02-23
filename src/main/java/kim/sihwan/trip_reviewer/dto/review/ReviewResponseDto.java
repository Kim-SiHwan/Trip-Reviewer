package kim.sihwan.trip_reviewer.dto.review;

import kim.sihwan.trip_reviewer.domain.Review;
import kim.sihwan.trip_reviewer.dto.review.reviewAlbum.ReviewAlbumResponseDto;
import kim.sihwan.trip_reviewer.dto.tag.TagResponseDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class ReviewResponseDto {
    private Long id;
    private String area;
    private String username;
    private String title;
    private String content;
    private String createDate;
    private List<ReviewAlbumResponseDto> reviewAlbums;
    private List<TagResponseDto> tags;
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

        tags = review.getReviewTags()
                .stream()
                .map(tag -> new TagResponseDto(tag.getTag()))
                .collect(Collectors.toList());

    }
}
