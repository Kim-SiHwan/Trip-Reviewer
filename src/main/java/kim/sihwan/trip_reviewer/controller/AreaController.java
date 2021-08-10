package kim.sihwan.trip_reviewer.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import kim.sihwan.trip_reviewer.dto.area.AreaRequestDto;
import kim.sihwan.trip_reviewer.dto.area.AreaResponseDto;
import kim.sihwan.trip_reviewer.service.AreaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "3. Area")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/area")
public class AreaController {

    private final AreaService areaService;

    @ApiImplicitParam(name = "AUTHORIZATION", value = "Bearer +로그인 후 access_token", required = true, dataType = "String", paramType = "header", defaultValue = "Bearer ")
    @ApiOperation(value = "전체 지역 조회",notes = "현재 로그인되어있는 유저의 지역들을 검색한다")
    @GetMapping
    public ResponseEntity<List<AreaResponseDto>> findAllByUsername(){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return new ResponseEntity<>(areaService.findAllAreasByUsername(username),HttpStatus.OK);
    }

    @ApiImplicitParam(name = "AUTHORIZATION", value = "Bearer +로그인 후 access_token", required = true, dataType = "String", paramType = "header", defaultValue = "Bearer ")
    @ApiOperation(value = "단일 지역 조회",notes = "지역구 id로 해당 지역의 정보를 조회한다.")
    @GetMapping("/{areaId}")
    public ResponseEntity<AreaResponseDto> findOneByAreaId(@PathVariable Long areaId){
        return new ResponseEntity<>(areaService.findOneByAreaId(areaId),HttpStatus.OK);
    }

    @ApiImplicitParam(name = "AUTHORIZATION", value = "Bearer +로그인 후 access_token", required = true, dataType = "String", paramType = "header", defaultValue = "Bearer ")
    @ApiOperation(value = "지역구 수정",notes = "변경할 정보를 입력받아 지역구의 속성값을 수정한다.")
    @PatchMapping
    public void updateAreaInfo(@RequestBody AreaRequestDto requestDto){
        areaService.changeAreaInfo(requestDto);
    }

    @ApiImplicitParam(name = "AUTHORIZATION", value = "Bearer +로그인 후 access_token", required = true, dataType = "String", paramType = "header", defaultValue = "Bearer ")
    @ApiOperation(value = "지역구 초기화",notes = "지역구 id로 해당 지역의 모든 정보를 초기화한다.")
    @PutMapping("/{areaId}")
    public void initArea(@PathVariable Long areaId){
        areaService.initArea(areaId);
    }

}
