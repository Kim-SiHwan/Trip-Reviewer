package kim.sihwan.trip_reviewer.service;

import kim.sihwan.trip_reviewer.config.jwt.JwtFilter;
import kim.sihwan.trip_reviewer.config.jwt.JwtTokenProvider;
import kim.sihwan.trip_reviewer.domain.Area;
import kim.sihwan.trip_reviewer.domain.Member;
import kim.sihwan.trip_reviewer.dto.member.MemberJoinDto;
import kim.sihwan.trip_reviewer.dto.member.MemberLoginDto;
import kim.sihwan.trip_reviewer.repository.AreaRepository;
import kim.sihwan.trip_reviewer.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileReader;
import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService implements UserDetailsService {
    private final AuthenticationManagerBuilder managerBuilder;
    private final RedisTemplate redisTemplate;
    private final JwtTokenProvider tokenProvider;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final AreaRepository areaRepository;

    @Transactional
    public String save(MemberJoinDto joinDto){
        Member member = Member.builder()
                .username(joinDto.getUsername())
                .password(passwordEncoder.encode(joinDto.getPassword()))
                .nickname(joinDto.getNickname())
                .role("ROLE_USER")
                .build();
        if(validateDuplicateMember(member)){
            memberRepository.save(member);
            init(member);
            return "정상적으로 가입되었습니다.";
        }else{
            return "이미 존재하는 회원입니다.";
        }

    }

    @Transactional
    public Map login(MemberLoginDto loginDto){
        Map<String,String> map = new HashMap<>();
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(loginDto.getUsername(),loginDto.getPassword());
        Authentication auth = managerBuilder.getObject().authenticate(token);
        String jwt = tokenProvider.createToken(auth);
        String rJwt = tokenProvider.createRefreshToken(auth);
        ValueOperations<String,String> vo = redisTemplate.opsForValue();
        vo.set(auth.getName()+"a", jwt);
        vo.set(auth.getName()+"r", rJwt);/*
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer "+jwt);*/
        map.put("token",jwt);
        map.put("username",auth.getName());

        return map;
    }

    private Boolean validateDuplicateMember(Member member) {
        Optional getMember = memberRepository.findMemberByUsername(member.getUsername());
        if (!getMember.isEmpty()) {
            return false;
        }
        return true;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        Member member = memberRepository.findMemberByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("해당 유저가 없습니다."));
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(member.getRole()));
        return new User(member.getUsername(), member.getPassword(), authorities);
    }

    public void init(Member member){
        try{
            System.out.println("init! ");
            JSONParser jsonParser = new JSONParser();
            Object obj= jsonParser.parse(new FileReader("C:\\test\\custom.json"));

            JSONObject jsonObj = (JSONObject) jsonParser.parse(obj.toString());

            JSONArray array= (JSONArray) jsonObj.get("features");

            for(int i=0; i<array.size(); i++){
                JSONObject jj = (JSONObject)array.get(i);
                JSONObject jj2 = (JSONObject)jj.get("properties");

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
            System.out.println("에러 ");
            e.printStackTrace();
        }

    }
}
