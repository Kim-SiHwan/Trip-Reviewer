package kim.sihwan.trip_reviewer.repository;

import kim.sihwan.trip_reviewer.domain.Album;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlbumRepository extends JpaRepository<Album,Long> {
}
