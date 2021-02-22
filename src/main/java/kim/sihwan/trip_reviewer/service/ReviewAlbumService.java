package kim.sihwan.trip_reviewer.service;

import kim.sihwan.trip_reviewer.domain.Review;
import kim.sihwan.trip_reviewer.domain.ReviewAlbum;
import kim.sihwan.trip_reviewer.dto.review.ReviewRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewAlbumService {

    @Transactional
    public Review addReviewAlbums(ReviewRequestDto requestDto){
        Review review = requestDto.toEntity(requestDto);
        String fileUrl = "C:\\Users\\김시환\\Desktop\\Git\\Trip-Reviewer\\src\\main\\resources\\static\\reviewImages\\";
        String saveUrl = "http://localhost:8080/api/review/download?filename=";

        try{
            String newFilename="";
            for(MultipartFile file : requestDto.getFiles()){
                newFilename = createNewFilename(file.getOriginalFilename());
                File dest = new File(fileUrl + newFilename);
                file.transferTo(dest);
                ReviewAlbum reviewAlbum = ReviewAlbum
                        .builder()
                        .url(saveUrl + newFilename)
                        .filename(newFilename)
                        .originFilename(file.getOriginalFilename())
                        .build();
                reviewAlbum.addReview(review);
            }
            review.addThumbnail(saveUrl + newFilename);
        }catch (Exception e){
            System.out.println("리뷰 앨범 생성 중 오류 발생");
        }
        return review;
    }
    public String createNewFilename(String filename){
        UUID uuid = UUID.randomUUID();
        return uuid.toString() +"_" + filename;
    }


}
