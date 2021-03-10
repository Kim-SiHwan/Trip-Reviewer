package kim.sihwan.trip_reviewer.dto.review;

import kim.sihwan.trip_reviewer.domain.Review;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Getter
@Setter
public class ReviewRequestDto {
    @NotEmpty(message = "지역이름은 필수 항목입니다.")
    private String area;

    @NotEmpty(message = "리뷰 제목은 필수 항목입니다.")
    private String title;

    @NotEmpty(message = "리뷰 내용은 필수 항목입니다.")
    private String content;

    @NotEmpty(message = "리뷰 작성자는 필수 항목입니다.")
    private String username;

    @NotEmpty(message = "사진은 필수 항목입니다.")
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
