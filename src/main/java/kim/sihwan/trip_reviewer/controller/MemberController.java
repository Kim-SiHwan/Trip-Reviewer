package kim.sihwan.trip_reviewer.controller;

import kim.sihwan.trip_reviewer.dto.member.MemberJoinDto;
import kim.sihwan.trip_reviewer.dto.member.MemberLoginDto;
import kim.sihwan.trip_reviewer.service.MemberService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
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
    public ResponseEntity save(@RequestBody MemberJoinDto joinDto){
        String result = memberService.save(joinDto);
        return new ResponseEntity(result, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody MemberLoginDto loginDto){
        System.out.println(loginDto.getUsername()+" "+loginDto.getPassword());
        Map<String,String> result = memberService.login(loginDto);
        return new ResponseEntity(result,HttpStatus.OK);
    }

    @PostMapping("/logout/{username}")
    public void logout(@PathVariable("username")String username){

    }

    @PostMapping("/new_token")
    public ResponseEntity refreshToken(@RequestBody NewTokenDto tokenDto){
        Map<String, String> result = new HashMap<>();
        result = memberService.createNewAccessToken(tokenDto.expiredToken);
        if(result.get("msg").equals("다시 로그인을 진행해주세요."))
            return new ResponseEntity(result, HttpStatus.BAD_REQUEST);

        return new ResponseEntity(result, HttpStatus.OK);
    }

    @Getter
    static class NewTokenDto{
        private String expiredToken;

    }

}
