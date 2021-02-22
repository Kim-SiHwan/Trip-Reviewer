package kim.sihwan.trip_reviewer.repository;

import io.lettuce.core.dynamic.annotation.Param;
import kim.sihwan.trip_reviewer.domain.ReviewTag;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ReviewTagRepository extends JpaRepository<ReviewTag,Long> {
    @Transactional
    @Modifying
    @Query("DELETE FROM ReviewTag r WHERE r.id IN :ids")
    void deleteAllByIdInQuery(@Param("ids") List<Long> ids);

    @EntityGraph(attributePaths = {"review","tag","review.reviewAlbums","review.member","review.comments"} , type = EntityGraph.EntityGraphType.LOAD)
    List<ReviewTag> findAllByTag_Id(Long id);
}
