package kim.sihwan.trip_reviewer.repository;

import kim.sihwan.trip_reviewer.domain.Comment;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Long> {


    List<Comment> findAllByReview_Id(Long id);

    List<Comment> findAllByUsername(String username);

}
