package kim.sihwan.trip_reviewer.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kim.sihwan.trip_reviewer.dto.member.MemberJoinDto;
import kim.sihwan.trip_reviewer.dto.member.MemberLoginDto;
import kim.sihwan.trip_reviewer.dto.member.MemberLoginResponseDto;
import kim.sihwan.trip_reviewer.service.MemberService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Api(tags = {"1. Member"})
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/member")
public class MemberController {

    private final MemberService memberService;

    @ApiOperation(value = "회원가입",notes = "회원 정보를 입력해 저장한다.")
    @PostMapping("/save")
    public void save(@RequestBody @Valid MemberJoinDto joinDto){
        memberService.save(joinDto);
    }

    @ApiOperation(value = "로그인",notes = "회원 정보를 입력해 로그인한다.")
    @PostMapping("/login")
    public ResponseEntity<MemberLoginResponseDto> login(@RequestBody @Valid MemberLoginDto loginDto){
        log.info("로그인 : "+ loginDto.getUsername() +" "+loginDto.getPassword());
        return new ResponseEntity<>(memberService.login(loginDto),HttpStatus.OK);
    }

    @ApiOperation(value = "로그아웃",notes = "로그아웃")
    @PostMapping("/logout")
    public void logout(){
        memberService.logout();
    }

}
