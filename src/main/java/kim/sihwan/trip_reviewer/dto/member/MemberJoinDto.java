package kim.sihwan.trip_reviewer.dto.member;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberJoinDto {
    private String username;
    private String nickname;
    private String password;
}
