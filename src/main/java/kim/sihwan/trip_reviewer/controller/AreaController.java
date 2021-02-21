package kim.sihwan.trip_reviewer.controller;

import kim.sihwan.trip_reviewer.domain.Area;
import kim.sihwan.trip_reviewer.dto.area.AreaRequestDto;
import kim.sihwan.trip_reviewer.dto.area.AreaResponseDto;
import kim.sihwan.trip_reviewer.service.AreaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/area")
public class AreaController {
    private final AreaService areaService;

    //쿼리1
    @GetMapping
    public List<AreaResponseDto> findAllByUsername(){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        List<AreaResponseDto> list = areaService.findAllByUsername(username);
        return list;
    }

    //쿼리 1
    @GetMapping("/{areaId}")
    public AreaResponseDto findOneByAreaId(@PathVariable("areaId")Long areaId){
        AreaResponseDto area = areaService.findOneByAreaId(areaId);
        return area;
    }

    @PutMapping
    public ResponseEntity updateAreaInfo(@RequestBody AreaRequestDto requestDto){
        areaService.changeAreaInfo(requestDto);
        return new ResponseEntity(areaService.findOneByAreaId(requestDto.getAreaId()), HttpStatus.OK);
    }

    @PutMapping("/{areaId}")
    public ResponseEntity initArea(@PathVariable("areaId")Long areaId){
        areaService.initArea(areaId);
        return new ResponseEntity(areaService.findOneByAreaId(areaId),HttpStatus.OK);
    }
}
