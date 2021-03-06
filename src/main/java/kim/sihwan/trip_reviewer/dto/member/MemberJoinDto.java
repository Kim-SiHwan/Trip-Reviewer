package kim.sihwan.trip_reviewer.dto.member;

import kim.sihwan.trip_reviewer.domain.Member;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints.*;

@Getter
@Builder
public class MemberJoinDto {
    @NotBlank
    @NotEmpty(message = "아이디는 필수 항목입니다.")
    @Pattern(regexp = "^(?=\\S+$)(?=.*?[a-z])(?=.*?[0-9]).{6,13}$", message ="아이디는 공백 없이 특수문자를 제외한 영문자와 숫자 조합으로  6~12글자로 작성해주세요." )
    private String username;

    @NotNull
    @NotEmpty(message = "비밀번호는 필수 항목입니다.")
    @Pattern(regexp = "^(?=\\S+$)(?=.*?[a-z])(?=.*?[0-9]).{6,13}$" , message ="비밀번호는 공백 없이 영문자와 숫자 조합으로 6~12글자로 작성해주세요.."
    )
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
