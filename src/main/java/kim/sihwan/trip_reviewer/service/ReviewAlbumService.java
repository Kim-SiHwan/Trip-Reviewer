package kim.sihwan.trip_reviewer.service;

import kim.sihwan.trip_reviewer.repository.ReviewAlbumRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewAlbumService {
    private final ReviewAlbumRepository reviewAlbumRepository;


    @Transactional
    public void deleteReviewAlbum(List<Long> reviewAlbumIdList){
        reviewAlbumRepository.deleteAllByIdInQuery(reviewAlbumIdList);
    }

}
