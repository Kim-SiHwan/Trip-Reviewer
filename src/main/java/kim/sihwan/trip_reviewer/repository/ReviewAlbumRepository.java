package kim.sihwan.trip_reviewer.repository;

import io.lettuce.core.dynamic.annotation.Param;
import kim.sihwan.trip_reviewer.domain.ReviewAlbum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ReviewAlbumRepository extends JpaRepository<ReviewAlbum,Long> {

    @Transactional
    @Modifying
    @Query("DELETE FROM ReviewAlbum r WHERE r.id IN :ids")
    void deleteAllByIdInQuery(@Param("ids")List<Long>ids);

}
