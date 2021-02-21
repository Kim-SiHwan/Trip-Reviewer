package kim.sihwan.trip_reviewer;

import kim.sihwan.trip_reviewer.domain.Member;
import kim.sihwan.trip_reviewer.dto.area.AreaRequestDto;
import kim.sihwan.trip_reviewer.dto.area.AreaResponseDto;
import kim.sihwan.trip_reviewer.repository.MemberRepository;
import kim.sihwan.trip_reviewer.service.AreaService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.Assert.assertEquals;

@SpringBootTest
public class AreaServiceTest {
    @Autowired
    AreaService areaService;

    @Autowired
    MemberRepository memberRepository;
    @Test
    void 인증없이_지역구_가져오기(){
    //given
        Member member = memberRepository.findById(1L).get();
        String username = member.getUsername();
    //when
        List<AreaResponseDto> list = areaService.findAllByUsername(username);
    //then
        //멤버 1명당 163개의 지역을 가지고 있음.
        assertEquals(163,list.size());
    }
    @Test
    void 인증없이_지역구_1개_가져오기 (){
    //given
        AreaResponseDto area = areaService.findOneByAreaId(1L);
    //when

    //then
        //1번 지역은 부산.
        assertEquals("부산",area.getName());
    }

    @Test
    void 인증없이_지역구_수정하기 (){
    //given
        AreaRequestDto areaRequestDto = new AreaRequestDto();
        areaRequestDto.setAreaId(1L);
        areaRequestDto.setColor("#000000");
        areaRequestDto.setAccompany("친구");
        areaRequestDto.setVisitDate("2021-02-21");
        areaRequestDto.setTitle("부산에 놀러왔어요");
        //지역구 기본 설정값은 color = #ffffff 를 제외하고 모두 비어있는 상태이다.
        AreaResponseDto beforeArea = areaService.findOneByAreaId(areaRequestDto.getAreaId());
        AreaResponseDto afterArea ;
    //when
        areaService.changeAreaInfo(areaRequestDto);
        afterArea = areaService.findOneByAreaId(areaRequestDto.getAreaId());
    //then
        //변경되기 전
        assertEquals("#ffffff",beforeArea.getColor());

        //변경된 후
        assertEquals("#000000",afterArea.getColor());
        assertEquals("부산에 놀러왔어요",afterArea.getTitle());
        assertEquals("친구",afterArea.getAccompany());
        assertEquals("2021-02-21",afterArea.getVisitDate());


    }

    @Test
    void 인증없이_지역구_초기화 (){
    //given
        AreaRequestDto areaRequestDto = new AreaRequestDto();
        areaRequestDto.setAreaId(1L);
        areaRequestDto.setColor("#000000");
        areaRequestDto.setAccompany("친구");
        areaRequestDto.setVisitDate("2021-02-21");
        areaRequestDto.setTitle("부산에 놀러왔어요");
        areaService.changeAreaInfo(areaRequestDto);

    //when
        areaService.initArea(areaRequestDto.getAreaId());
        AreaResponseDto area = areaService.findOneByAreaId(areaRequestDto.getAreaId());
    //then
        assertEquals("",area.getTitle());
        assertEquals("#ffffff",area.getColor());
        assertEquals("",area.getVisitDate());
        assertEquals("",area.getAccompany());
    }
}
