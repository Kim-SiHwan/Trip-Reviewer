package kim.sihwan.trip_reviewer.controller;

import kim.sihwan.trip_reviewer.dto.member.MemberJoinDto;
import kim.sihwan.trip_reviewer.dto.member.MemberLoginDto;
import kim.sihwan.trip_reviewer.service.MemberService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member")
public class MemberController {

    private final MemberService memberService;

    @GetMapping
    public ResponseEntity test(){
        String msg = "TEST!";
        return new ResponseEntity(msg,HttpStatus.OK);
    }

    @PostMapping("/save")
    public void save(@RequestBody MemberJoinDto joinDto){
        memberService.save(joinDto);
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody MemberLoginDto loginDto){
        System.out.println(loginDto.getUsername()+" "+loginDto.getPassword());
        Map<String,String> result = memberService.login(loginDto);
        return new ResponseEntity(result,HttpStatus.OK);
    }

    @PostMapping("/logout/{username}")
    public void logout(@PathVariable("username")String username){
        memberService.logout(username);
    }

/*    @PostMapping("/new_token")
    public ResponseEntity refreshToken(@RequestBody NewTokenDto tokenDto){
        String result = memberService.createNewAccessToken(tokenDto.expiredToken);
        return new ResponseEntity(result,HttpStatus.OK);

*//*        if("다시 로그인을 진행해주세요.".equals(result.getOrDefault("msg",""))) {
            return new ResponseEntity(result, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(result, HttpStatus.OK);*//*
    }*/

    @Getter
    static class NewTokenDto{
        private String expiredToken;

    }

}
