package kim.sihwan.trip_reviewer.dto.member;

import kim.sihwan.trip_reviewer.domain.Member;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberResponseDto {
    private Long id;
    private String username;
    private String createDate;

    public MemberResponseDto(Member member) {
        username = member.getUsername();
        createDate = member.getCreateDate().toString();
    }
}
