package kim.sihwan.trip_reviewer.repository;

import kim.sihwan.trip_reviewer.domain.Review;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review,Long> {

    @EntityGraph(attributePaths = {"member","reviewAlbums","comments","reviewTags"},type = EntityGraph.EntityGraphType.LOAD)
    //성능 저하 비교는 findById
    Optional <Review> findReviewById(Long id);
    //이거 써서 쿼리 1번으로 줄임.
    //성능 저하 비교는 그냥 findAll
    @Override
    @EntityGraph( attributePaths = {"member","reviewAlbums","comments","reviewTags"},type = EntityGraph.EntityGraphType.LOAD)
    List<Review> findAll();

    @EntityGraph(attributePaths = {"member","reviewAlbums","comments","reviewTags"},type = EntityGraph.EntityGraphType.LOAD)
    List<Review> findAllByMember_Username(String username);

    @Override
    @EntityGraph(attributePaths = {"member","reviewAlbums","comments","reviewTags"},type = EntityGraph.EntityGraphType.LOAD)
    void deleteById(Long reviewId);
}
