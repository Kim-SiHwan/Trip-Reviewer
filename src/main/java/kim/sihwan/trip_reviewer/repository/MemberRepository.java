package kim.sihwan.trip_reviewer.repository;

import kim.sihwan.trip_reviewer.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member,Long> {
    Optional<Member> findMemberByUsername(String username);
}
