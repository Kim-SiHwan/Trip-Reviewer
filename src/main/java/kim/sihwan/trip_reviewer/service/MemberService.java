package kim.sihwan.trip_reviewer.service;

import io.jsonwebtoken.ExpiredJwtException;
import kim.sihwan.trip_reviewer.config.jwt.JwtFilter;
import kim.sihwan.trip_reviewer.config.jwt.JwtTokenProvider;
import kim.sihwan.trip_reviewer.domain.Area;
import kim.sihwan.trip_reviewer.domain.Member;
import kim.sihwan.trip_reviewer.dto.member.MemberJoinDto;
import kim.sihwan.trip_reviewer.dto.member.MemberLoginDto;
import kim.sihwan.trip_reviewer.repository.AreaRepository;
import kim.sihwan.trip_reviewer.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

import javax.persistence.Table;
import java.io.FileReader;
import java.util.*;

@Slf4j
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
    public void logout(String username){
        ValueOperations<String,String> vo = redisTemplate.opsForValue();
        vo.set(username+"a","");
        vo.set(username+"r","");
        log.info(username+"로그아웃.");
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

    @Transactional
    public Map createNewAccessToken(String expiredToken){
        System.out.println("만료된 토큰  : "+expiredToken);
        Map<String,String> map = new HashMap<>();
        String username="";
        String jwt = expiredToken.substring(7,expiredToken.length());
        ValueOperations<String,String> vo = redisTemplate.opsForValue();
        try{
            System.out.println(username+"의 토큰 검증");
            username = tokenProvider.getAuthentication(jwt).getName();
            System.out.println(username+"의 토큰 검증2");
            log.info(username+"의 토큰 검증.");
            System.out.println(username+"의 토큰 검증3");

        }catch (ExpiredJwtException e){
            System.out.println("유저네임 : "+username);
            String tempName = e.getClaims().getSubject();
            System.out.println("유저네임 : "+tempName);

            String refreshToken = vo.get(tempName+"r");
            if(!tokenProvider.validateToken(refreshToken)){
                System.out.println("리프레시 토큰이 만료되었음");
                log.info("리프레시 토큰이 만료되었기에 재 로그인이 필요합니다.");
                map.put("msg","다시 로그인을 진행해주세요.");
            }

            if(vo.get(tempName+"a").equals(jwt) && tokenProvider.validateToken(refreshToken)){
                log.info("액세스 토큰은 만료되었으나 리프레시 토큰은 만료되지 않아 재발급이 가능합니다.");
                System.out.println("액세스토큰 재발급");
                Authentication authentication = tokenProvider.getAuthentication(refreshToken);
                String newAccessToken = tokenProvider.createToken(authentication);
                map.put("msg", "새로운 토큰이 발급되었습니다.");
                map.put("newAccessToken", newAccessToken);
                System.out.println("쌔 토큰 : "+newAccessToken);
                vo.set(tempName+"a",newAccessToken);
                map.put("username", authentication.getName());
            }
        }
        if(map.isEmpty()){
            map.put("msg","아직 만료되지 않은 토큰입니다.");
        }
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
