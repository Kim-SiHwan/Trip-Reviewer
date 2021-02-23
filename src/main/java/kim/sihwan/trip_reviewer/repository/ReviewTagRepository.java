package kim.sihwan.trip_reviewer.repository;

import kim.sihwan.trip_reviewer.domain.ReviewTag;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewTagRepository extends JpaRepository<ReviewTag,Long> {

    @EntityGraph(attributePaths = {"review","tag","review.reviewAlbums","review.member","review.comments"} , type = EntityGraph.EntityGraphType.LOAD)
    List<ReviewTag> findAllByTag_Id(Long id);

}
