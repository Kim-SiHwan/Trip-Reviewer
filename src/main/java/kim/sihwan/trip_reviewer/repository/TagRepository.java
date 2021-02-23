package kim.sihwan.trip_reviewer.repository;

import kim.sihwan.trip_reviewer.domain.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag,Long> {

    Optional<Tag> findTagByTag(String tag);


}
