package kim.sihwan.trip_reviewer.config;


import kim.sihwan.trip_reviewer.domain.Area;
import kim.sihwan.trip_reviewer.domain.Member;
import kim.sihwan.trip_reviewer.repository.AreaRepository;
import kim.sihwan.trip_reviewer.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileReader;

@Component
@RequiredArgsConstructor
public class AppConfig implements ApplicationRunner {
    private final MemberRepository memberRepository;
    private final AreaRepository areaRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        Member member = Member
                .builder()
                .username("admin")
                .password(passwordEncoder.encode("admin"))
                .nickname("HELLO ADMIN")
                .role("ROLE_ADMIN")
                .build();
        memberRepository.save(member);
        init(member);

        Member member2 = Member
                .builder()
                .username("user")
                .password(passwordEncoder.encode("user"))
                .nickname("HELLO USER")
                .role("ROLE_USER")
                .build();
        memberRepository.save(member2);
        init(member2);
        Member member3 = Member
                .builder()
                .username("test")
                .password("test")
                .nickname("HELLO TEST")
                .role("ROLE_USER")
                .build();
        memberRepository.save(member3);
        init(member3);
    }

    public void init(Member member) {
        try {
            JSONParser jsonParser = new JSONParser();
//            String path = AppConfig.class.getResource("").getPath();
//            Object obj = jsonParser.parse(new FileReader(path+"custom.json"));
            Object obj = jsonParser.parse(new FileReader("C:\\Users\\김시환\\Desktop\\Git\\Trip-Reviewer\\src\\main\\java\\kim\\sihwan\\trip_reviewer\\config\\custom.json"));
            JSONObject jsonObj = (JSONObject) jsonParser.parse(obj.toString());
            JSONArray array = (JSONArray) jsonObj.get("features");
            for (int i = 0; i < array.size(); i++) {
                JSONObject jj = (JSONObject) array.get(i);
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
