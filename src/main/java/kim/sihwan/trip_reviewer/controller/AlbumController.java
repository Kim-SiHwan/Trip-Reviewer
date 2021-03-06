package kim.sihwan.trip_reviewer.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import kim.sihwan.trip_reviewer.config.awsS3.S3Uploader;
import kim.sihwan.trip_reviewer.dto.area.album.AlbumRequestDto;
import kim.sihwan.trip_reviewer.dto.area.album.AlbumResponseDto;
import kim.sihwan.trip_reviewer.service.AlbumService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@Api(tags = {"3. Album"})
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/album")
public class AlbumController {

    private final AlbumService albumService;
    private final S3Uploader s3Uploader;
    @ApiImplicitParam(name = "AUTHORIZATION", value = "Bearer +로그인 후 access_token", required = true, dataType = "String", paramType = "header", defaultValue = "Bearer ")
    @ApiOperation(value = "사진첩 조회",notes = "지역구 id로 해당 지역의 사진첩을 조회한다.")
    @GetMapping("/{areaId}")
    public ResponseEntity<List<AlbumResponseDto>> findAllAlbumByAreaId(@PathVariable Long areaId){
        return new ResponseEntity<>(albumService.findAllAlbumByAreaId(areaId),HttpStatus.OK);
    }

    @PostMapping("/upload")
    public String upload(@RequestParam("data")MultipartFile multipartFile) throws IOException{
        return s3Uploader.upload(multipartFile, "static");
    }

    @ApiImplicitParam(name = "AUTHORIZATION", value = "Bearer +로그인 후 access_token", required = true, dataType = "String", paramType = "header", defaultValue = "Bearer ")
    @ApiOperation(value = "사진 추가",notes = "지역구 id와 사진 리스트를 입력받아 사진첩에 추가한다.")
    @PostMapping
    public void addAlbum(@ModelAttribute @Valid AlbumRequestDto albumRequestDto) throws IOException {
        albumService.addAlbum(albumRequestDto);
    }

    @ApiImplicitParam(name = "AUTHORIZATION", value = "Bearer +로그인 후 access_token", required = true, dataType = "String", paramType = "header", defaultValue = "Bearer ")
    @ApiOperation(value = "사진 삭제",notes = "사진 id 리스트로 사진을 삭제한다.")
    @DeleteMapping
    public void deleteAlbumByAlbumIds(@RequestBody @Valid AlbumUpdateDto updateDto){
        albumService.deleteAlbum(updateDto.getIds());
    }


    @Getter
    @Setter
    static class AlbumUpdateDto{
        private Long areaId;
        private List<Long> ids;

    }

}
