package kim.sihwan.trip_reviewer.controller;

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

    @GetMapping
    public ResponseEntity<List<AreaResponseDto>> findAllByUsername(){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return new ResponseEntity<>(areaService.findAllByUsername(username),HttpStatus.OK);
    }

    @GetMapping("/{areaId}")
    public ResponseEntity<AreaResponseDto> findOneByAreaId(@PathVariable Long areaId){
        return new ResponseEntity<>(areaService.findOneByAreaId(areaId),HttpStatus.OK);
    }

    @PatchMapping//여기 패치로 바꿨음.
    public void updateAreaInfo(@RequestBody AreaRequestDto requestDto){
        areaService.changeAreaInfo(requestDto);
    }

    @PutMapping("/{areaId}")
    public void initArea(@PathVariable Long areaId){
        areaService.initArea(areaId);
    }

}
