package kim.sihwan.trip_reviewer.dto.review;

import kim.sihwan.trip_reviewer.domain.Review;
import kim.sihwan.trip_reviewer.dto.review.reviewAlbum.ReviewAlbumResponseDto;
import kim.sihwan.trip_reviewer.dto.tag.TagResponseDto;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
public class ReviewResponseDto {
    private final Long id;
    private final String area;
    private final String username;
    private final String title;
    private final String content;
    private final String createDate;
    private final List<ReviewAlbumResponseDto> reviewAlbums;
    private final List<TagResponseDto> tags;

    public static ReviewResponseDto toDto(Review review) {
        return ReviewResponseDto.builder()
                .id(review.getId())
                .area(review.getArea())
                .username(review.getMember().getUsername())
                .title(review.getTitle())
                .content(review.getContent())
                .createDate(review.getCreateDate().toString())
                .reviewAlbums(
                        review.getReviewAlbums()
                                .stream()
                                .map(ReviewAlbumResponseDto::new)
                                .collect(Collectors.toList()))
                .tags(review.getReviewTags()
                        .stream()
                        .map(tag -> new TagResponseDto(tag.getTag()))
                        .collect(Collectors.toList()))
                .build();
    }


}
