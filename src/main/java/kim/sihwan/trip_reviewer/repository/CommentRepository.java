package kim.sihwan.trip_reviewer.repository;

import io.lettuce.core.dynamic.annotation.Param;
import kim.sihwan.trip_reviewer.domain.Comment;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Long> {

    @Transactional
    @Modifying
    @Query("DELETE FROM Comment c WHERE c.id IN :ids")
    void deleteAllByIdInQuery(@Param("ids")List<Long> ids);

    @EntityGraph(attributePaths = {"review"} , type = EntityGraph.EntityGraphType.LOAD)
    List<Comment> findAllByReview_Id(Long id);

    List<Comment> findAllByUsername(String username);
}
