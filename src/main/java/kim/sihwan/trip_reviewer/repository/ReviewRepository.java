package kim.sihwan.trip_reviewer.repository;

import kim.sihwan.trip_reviewer.domain.Review;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review,Long> {

    @EntityGraph(attributePaths = {"member","reviewAlbums","reviewTags","reviewTags.tag"},type = EntityGraph.EntityGraphType.LOAD)
    Optional<Review> findReviewById(Long id);

    @Override
    @EntityGraph( attributePaths = {"member","reviewAlbums","comments","reviewTags","reviewTags.tag"},type = EntityGraph.EntityGraphType.LOAD)
    List<Review> findAll();

    @EntityGraph(attributePaths = {"member","reviewAlbums","comments","reviewTags","reviewTags.tag"},type = EntityGraph.EntityGraphType.LOAD)
    List<Review> findAllByMemberUsername(String username);

    @Override
    void deleteById(Long reviewId);
}
