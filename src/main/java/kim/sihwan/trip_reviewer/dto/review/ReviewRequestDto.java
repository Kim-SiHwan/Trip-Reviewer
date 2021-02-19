package kim.sihwan.trip_reviewer.dto.review;

import kim.sihwan.trip_reviewer.domain.Review;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ReviewRequestDto {
    private String area;
    private String title;
    private String content;

    public Review toEntity(ReviewRequestDto requestDto){
        return Review.builder()
                .area(requestDto.getArea())
                .title(requestDto.getTitle())
                .content(requestDto.getContent())
                .createDate(LocalDateTime.now())
                .build();
    }
}
