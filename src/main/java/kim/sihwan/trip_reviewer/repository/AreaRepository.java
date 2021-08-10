package kim.sihwan.trip_reviewer.repository;

import kim.sihwan.trip_reviewer.domain.Area;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AreaRepository extends JpaRepository<Area,Long> {

    @EntityGraph(attributePaths = {"member"})
    List<Area> findAllByMemberUsername(String username);
}
