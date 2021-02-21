package kim.sihwan.trip_reviewer.repository;

import io.lettuce.core.dynamic.annotation.Param;
import kim.sihwan.trip_reviewer.domain.Album;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface AlbumRepository extends JpaRepository<Album,Long> {
// 굳이?   @EntityGraph(attributePaths = {"area"}, type = EntityGraph.EntityGraphType.LOAD)
    List<Album> findAllByArea_Id(Long id);

    //이걸로 delete는 쿼리 한번으로 퉁.
    //성능 저하용은 deleteEntity
    @Transactional
    @Modifying
    @Query("DELETE FROM Album a WHERE a.id IN :ids")
    void deleteAllAlbumByQuery(@Param("ids") List<Long> ids);
}
