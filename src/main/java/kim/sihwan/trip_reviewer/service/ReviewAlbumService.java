package kim.sihwan.trip_reviewer.service;

import kim.sihwan.trip_reviewer.config.awsS3.S3Uploader;
import kim.sihwan.trip_reviewer.domain.Review;
import kim.sihwan.trip_reviewer.domain.ReviewAlbum;
import kim.sihwan.trip_reviewer.dto.review.ReviewRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewAlbumService {
    private final S3Uploader s3Uploader;

    @Transactional
    public Review addReviewAlbums(ReviewRequestDto requestDto) throws IOException {
        Review review = requestDto.toEntity(requestDto);
       for (MultipartFile file : requestDto.getFiles()) {
            String saveUrl = s3Uploader.upload(file, "static");
            ReviewAlbum reviewAlbum = ReviewAlbum
                    .builder()
                    .url(saveUrl)
                    .originFilename(file.getOriginalFilename())
                    .build();
            reviewAlbum.addReview(review);
        }
        return review;
    }



}
