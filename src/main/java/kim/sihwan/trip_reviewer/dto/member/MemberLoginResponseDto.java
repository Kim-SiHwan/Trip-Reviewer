package kim.sihwan.trip_reviewer.dto.member;

import lombok.Getter;

@Getter
public class MemberLoginResponseDto {
    private String token;
    private String username;

    public MemberLoginResponseDto(String token, String username){
        this.token = token;
        this.username = username;
    }
}
