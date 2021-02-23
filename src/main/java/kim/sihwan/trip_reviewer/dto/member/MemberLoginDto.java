package kim.sihwan.trip_reviewer.dto.member;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class MemberLoginDto {
    @NotEmpty(message = "아이디는 필수 요소입니다.")
    private String username;

    @NotEmpty(message = "비밀번호는 필수 요소입니다.")
    private String password;
}
