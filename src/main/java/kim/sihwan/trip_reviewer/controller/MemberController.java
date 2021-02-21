package kim.sihwan.trip_reviewer.controller;

import kim.sihwan.trip_reviewer.dto.member.MemberJoinDto;
import kim.sihwan.trip_reviewer.dto.member.MemberLoginDto;
import kim.sihwan.trip_reviewer.service.MemberService;
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
    public ResponseEntity save(@RequestBody MemberJoinDto joinDto){
        String result = memberService.save(joinDto);
        return new ResponseEntity(result, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody MemberLoginDto loginDto){
        Map<String,String> result = memberService.login(loginDto);
        return new ResponseEntity(result,HttpStatus.OK);
    }

    @PostMapping("/new_token")
    public ResponseEntity refreshToken(@RequestParam String expiredToken){
        Map<String,String> result = memberService.createNewAccessToken(expiredToken);
        return new ResponseEntity(result,HttpStatus.OK);
    }


}
