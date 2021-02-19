package kim.sihwan.trip_reviewer.controller;

import kim.sihwan.trip_reviewer.domain.Area;
import kim.sihwan.trip_reviewer.dto.area.AreaResponseDto;
import kim.sihwan.trip_reviewer.service.AreaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/area")
public class AreaController {
    private final AreaService areaService;

    //쿼리1
    @GetMapping
    public List<AreaResponseDto> findAllByUsername(){
        List<AreaResponseDto> list = areaService.findAllByUsername("kim");
        return list;
    }

    //쿼리 1
    @GetMapping("/{areaId}")
    public AreaResponseDto findOneByAreaId(@PathVariable("areaId")Long areaId){
        AreaResponseDto area = areaService.findOneByAreaId(areaId);
        return area;
    }
}
