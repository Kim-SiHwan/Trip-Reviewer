package kim.sihwan.trip_reviewer.dto.review;

import kim.sihwan.trip_reviewer.domain.Review;
import kim.sihwan.trip_reviewer.dto.review.reviewAlbum.ReviewAlbumResponseDto;
import kim.sihwan.trip_reviewer.dto.tag.TagResponseDto;
import lombok.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
    private List<TagResponseDto> tags;


    public static ReviewListResponseDto toDto(Review review){
        return ReviewListResponseDto
                .builder()
                .id(review.getId())
                .area(review.getArea())
                .username(review.getMember().getUsername())
                .title(review.getTitle())
                .content(review.getContent())
                .createDate(review.getCreateDate().toString())
                .thumbnail(review.getThumbnail())
                .reviewAlbumsCount(review.getReviewAlbums().size())
                .commentCount(review.getComments().size())
                .tags(review.getReviewTags()
                    .stream()
                    .map(tag-> new TagResponseDto(tag.getTag()))
                    .collect(Collectors.toList()))
                .build();
    }

}
