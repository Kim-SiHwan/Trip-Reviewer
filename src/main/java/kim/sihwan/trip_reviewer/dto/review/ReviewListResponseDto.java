package kim.sihwan.trip_reviewer.dto.review;

import kim.sihwan.trip_reviewer.domain.Review;
import kim.sihwan.trip_reviewer.dto.tag.TagResponseDto;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
public class ReviewListResponseDto {

    private final Long id;
    private final String area;
    private final String username;
    private final String title;
    private final String content;
    private final String createDate;
    private final String thumbnail;
    private final int reviewAlbumsCount;
    private final int commentCount;
    private final List<TagResponseDto> tags;


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
