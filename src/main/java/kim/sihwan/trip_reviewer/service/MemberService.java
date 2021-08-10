package kim.sihwan.trip_reviewer.service;

import kim.sihwan.trip_reviewer.config.jwt.JwtTokenProvider;
import kim.sihwan.trip_reviewer.domain.Area;
import kim.sihwan.trip_reviewer.domain.Member;
import kim.sihwan.trip_reviewer.dto.member.MemberJoinDto;
import kim.sihwan.trip_reviewer.dto.member.MemberLoginDto;
import kim.sihwan.trip_reviewer.dto.member.MemberLoginResponseDto;
import kim.sihwan.trip_reviewer.exception.cumtomException.UserNotFoundException;
import kim.sihwan.trip_reviewer.exception.cumtomException.UsernameDuplicatedException;
import kim.sihwan.trip_reviewer.repository.AreaRepository;
import kim.sihwan.trip_reviewer.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService implements UserDetailsService {
    private final AuthenticationManagerBuilder managerBuilder;
    private final JwtTokenProvider tokenProvider;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final AreaRepository areaRepository;

    @Transactional
    public void save(MemberJoinDto joinDto) {

        Member member = joinDto.toEntity(joinDto, passwordEncoder);
        if (!isValidateDuplicateMember(member)) {
            throw new UsernameDuplicatedException();
        }

        memberRepository.save(member);
        init(member);

    }

    @Transactional
    public void logout() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        log.info(username + "로그아웃.");
    }


    @Transactional
    public MemberLoginResponseDto login(MemberLoginDto loginDto) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword());
        Authentication auth = managerBuilder.getObject().authenticate(token);
        String jwt = tokenProvider.createToken(auth);
        return new MemberLoginResponseDto(jwt,auth.getName());
    }


    private Boolean isValidateDuplicateMember(Member member) {
        Optional<Member> getMember = memberRepository.findMemberByUsername(member.getUsername());
        return getMember.isEmpty();
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        Member member = memberRepository.findMemberByUsername(username)
                .orElseThrow(UserNotFoundException::new);
        return new User(member.getUsername(), member.getPassword(), Collections.singleton(new SimpleGrantedAuthority(member.getRole())));
    }

    public void init(Member member) {
        try {
            JSONParser jsonParser = new JSONParser();
            InputStream in5 = this.getClass().getResourceAsStream("/custom.json");

            Object obj = jsonParser.parse(new InputStreamReader(in5));

            JSONObject jsonObj = (JSONObject) jsonParser.parse(obj.toString());
            JSONArray array = (JSONArray) jsonObj.get("features");
            for (Object o : array) {

                JSONObject jj = (JSONObject) o;
                JSONObject jj2 = (JSONObject) jj.get("properties");

                Area area = Area.builder()
                        .name(jj2.get("SIG_KOR_NM").toString())
                        .idx(Integer.parseInt(jj2.get("idx").toString()))
                        .accompany("")
                        .title("")
                        .color("#ffffff")
                        .visitDate("")
                        .build();

                area.addMember(member);
                areaRepository.save(area);

            }


        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
