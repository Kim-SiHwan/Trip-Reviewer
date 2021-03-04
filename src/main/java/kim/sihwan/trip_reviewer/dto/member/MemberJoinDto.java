package kim.sihwan.trip_reviewer.dto.member;

import kim.sihwan.trip_reviewer.domain.Member;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints.NotEmpty;

@Getter
@Builder
public class MemberJoinDto {
    @NotEmpty(message = "아이디는 필수 항목입니다.")
    private String username;

    @NotEmpty(message = "비밀번호는 필수 항목입니다.")
    private String password;

    public Member toEntity(MemberJoinDto joinDto, PasswordEncoder passwordEncoder){
        return Member
                .builder()
                .username(joinDto.getUsername())
                .password(passwordEncoder.encode(joinDto.getPassword()))
                .role("ROLE_USER")
                .build();
    }
}
