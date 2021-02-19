package kim.sihwan.trip_reviewer.controller;

import kim.sihwan.trip_reviewer.dto.area.album.AlbumRequestDto;
import kim.sihwan.trip_reviewer.dto.area.album.AlbumResponseDto;
import kim.sihwan.trip_reviewer.service.AlbumService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/album")
public class AlbumController {
    private final AlbumService albumService;

    public List<AlbumResponseDto> getAlbums(Long areaId){
        List<AlbumResponseDto> list = albumService.findAllAlbumByAreaId(areaId);
        return list;
    }

    @GetMapping("/{areaId}")
    public ResponseEntity findAllAlbumByAreaId(@PathVariable("areaId") Long areaId){
        List<AlbumResponseDto> list = getAlbums(areaId);
        return new ResponseEntity(list, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity addAlbum(@ModelAttribute AlbumRequestDto albumRequestDto){
        Long areaId = albumRequestDto.getAreaId();
        albumService.addAlbum(albumRequestDto);
        return new ResponseEntity(getAlbums(areaId), HttpStatus.OK);
    }

    @GetMapping(value = "/download", produces = {MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE})
    public ResponseEntity<Resource> downloadAlbumImage(@RequestParam("filename")String filename){
        Resource resource = new FileSystemResource("C:\\Users\\김시환\\Desktop\\Git\\Trip-Reviewer\\src\\main\\resources\\static\\albumImages\\" + filename);
        return new ResponseEntity(resource,HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity deleteAlbumByAlbumIds(@RequestBody dto d){
        albumService.deleteAlbum(d.ids);
        return new ResponseEntity(getAlbums(d.areaId),HttpStatus.OK);
    }

    @Data
    static class dto{
        private Long areaId;
        private List<Long> ids;

    }

}
