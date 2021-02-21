package kim.sihwan.trip_reviewer.dto.review;

import kim.sihwan.trip_reviewer.domain.Review;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Getter
@Setter
public class ReviewRequestDto {
    private String area;
    private String title;
    private String content;
    private String username;
    private List<MultipartFile> files;
    private Set<String> tags;

    public Review toEntity(ReviewRequestDto requestDto){
        return Review.builder()
                .area(requestDto.getArea())
                .title(requestDto.getTitle())
                .content(requestDto.getContent())
                .createDate(LocalDateTime.now())
                .build();
    }
}
