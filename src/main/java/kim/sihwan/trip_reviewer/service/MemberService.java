package kim.sihwan.trip_reviewer.service;

import kim.sihwan.trip_reviewer.config.jwt.JwtTokenProvider;
import kim.sihwan.trip_reviewer.domain.Area;
import kim.sihwan.trip_reviewer.domain.Member;
import kim.sihwan.trip_reviewer.dto.exception.*;
import kim.sihwan.trip_reviewer.dto.member.MemberJoinDto;
import kim.sihwan.trip_reviewer.dto.member.MemberLoginDto;
import kim.sihwan.trip_reviewer.repository.AreaRepository;
import kim.sihwan.trip_reviewer.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileReader;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService implements UserDetailsService {
    private final AuthenticationManagerBuilder managerBuilder;
    private final RedisTemplate<String, String> redisTemplate;
    private final JwtTokenProvider tokenProvider;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final AreaRepository areaRepository;

    private final String ACCESS_KEY="_access_key";
    private final String REFRESH_KEY="_refresh_key";

    @Transactional
    public void save(MemberJoinDto joinDto) {

        Member member = joinDto.toEntity(joinDto,passwordEncoder);
        // 객체지향 체조 원칙 10가지
        if (isValidateDuplicateMember(member)) {
            memberRepository.save(member);
            init(member);
            System.out.println("정상 가입");
        }
        throw new UsernameDuplicatedException();

    }

    @Transactional
    public void logout(String username) {
        ValueOperations<String, String> vo = redisTemplate.opsForValue();
        vo.set(username + ACCESS_KEY, "");
        vo.set(username + REFRESH_KEY, "");
        log.info(username + "로그아웃.");
    }


    @Transactional
    public Map<String, String> login(MemberLoginDto loginDto) {
        Map<String, String> map = new HashMap<>();
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword());
        Authentication auth = managerBuilder.getObject().authenticate(token);
        String jwt = tokenProvider.createToken(auth);
        String rJwt = tokenProvider.createRefreshToken(auth);
        ValueOperations<String, String> vo = redisTemplate.opsForValue();
        vo.set(auth.getName() + ACCESS_KEY, jwt);
        vo.set(auth.getName() + REFRESH_KEY, rJwt);
        map.put("token", jwt);
        map.put("username", auth.getName());

        return map;
    }


/*
    @Transactional
    public String createNewAccessToken(String expiredToken) {
        if(expiredToken.length()<10 || expiredToken.isEmpty())
            throw new InvalidTokenException();

        System.out.println("만료된 토큰  : " + expiredToken);
        String newAccessToken ="";
        String username = "";
        String jwt = expiredToken.substring(7);
        ValueOperations<String, String> vo = redisTemplate.opsForValue();

        try {
            */
/*
            요청마다 필터에 넣으니까, validateToken 에 걸리는 것임.

                    *//*

            System.out.println(username + "의 토큰 검증");
            username = tokenProvider.getAuthentication(jwt).getName();
            System.out.println(username + "의 토큰 검증2");
            log.info(username + "의 토큰 검증.");
            System.out.println(username + "의 토큰 검증3");

        } catch (ExpiredJwtException e) {
            System.out.println("유저네임 : " + username);
            String tempName = e.getClaims().getSubject();
            System.out.println("유저네임 : " + tempName);

            String refreshToken = vo.get(tempName + REFRESH_KEY);
            if (!tokenProvider.validateToken(refreshToken)) {
                System.out.println("리프레시 토큰이 만료되었음");
                log.info("리프레시 토큰이 만료되었기에 재 로그인이 필요합니다.");
                throw new CRefreshTokenExpiredException();
            }

            if (Objects.equals(vo.get(tempName + ACCESS_KEY), jwt) && tokenProvider.validateToken(refreshToken)) {
                log.info("액세스 토큰은 만료되었으나 리프레시 토큰은 만료되지 않아 재발급이 가능합니다.");
                System.out.println("액세스토큰 재발급");
                Authentication authentication = tokenProvider.getAuthentication(refreshToken);
                newAccessToken = tokenProvider.createToken(authentication);
                System.out.println("쌔 토큰 : " + newAccessToken);
                vo.set(tempName + ACCESS_KEY, newAccessToken);
            }

        }
        //서명,이상한 토큰 넘길 때 토큰 파기하는 catch 추가하기.
        return newAccessToken;
    }
*/

    private Boolean isValidateDuplicateMember(Member member)  {
        Optional<Member> getMember = memberRepository.findMemberByUsername(member.getUsername());
        return getMember.isEmpty();
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        Member member = memberRepository.findMemberByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("유저가 없습니다."));
        //불변객체
        return new User(member.getUsername(), member.getPassword(),Collections.singleton(new SimpleGrantedAuthority(member.getRole())));
    }

    public void init(Member member) {
        try {
            System.out.println("init! ");
            JSONParser jsonParser = new JSONParser();
            Object obj = jsonParser.parse(new FileReader("C:\\test\\custom.json"));

            JSONObject jsonObj = (JSONObject) jsonParser.parse(obj.toString());

            JSONArray array = (JSONArray) jsonObj.get("features");

            // Gson, objectMapper

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
            System.out.println("에러 ");
            e.printStackTrace();
        }

    }
}
