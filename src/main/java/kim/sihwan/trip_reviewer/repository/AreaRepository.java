package kim.sihwan.trip_reviewer.repository;

import kim.sihwan.trip_reviewer.domain.Area;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AreaRepository extends JpaRepository<Area,Long> {
    List<Area> findAllByMember_Username(String username);
}
