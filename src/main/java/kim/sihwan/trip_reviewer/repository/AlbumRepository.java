package kim.sihwan.trip_reviewer.repository;

import io.lettuce.core.dynamic.annotation.Param;
import kim.sihwan.trip_reviewer.domain.Album;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface AlbumRepository extends JpaRepository<Album,Long> {
    List<Album> findAllByArea_Id(Long id);

    @Transactional
    @Modifying
    @Query("DELETE FROM Album a WHERE a.id IN :ids")
    void deleteAllAlbumByQuery(@Param("ids") List<Long> ids);
}
