package kim.sihwan.trip_reviewer.dto.review;

import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
public class ReviewUpdateRequestDto {
    private Long reviewId;
    private String title;
    private String content;

}
