package kim.sihwan.trip_reviewer.controller;

import kim.sihwan.trip_reviewer.dto.area.album.AlbumRequestDto;
import kim.sihwan.trip_reviewer.dto.area.album.AlbumResponseDto;
import kim.sihwan.trip_reviewer.service.AlbumService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
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

    @GetMapping("/{areaId}")
    public ResponseEntity<List<AlbumResponseDto>> findAllAlbumByAreaId(@PathVariable("areaId") Long areaId){
        return new ResponseEntity<>(getAlbums(areaId),HttpStatus.OK);
    }

    @GetMapping(value = "/download", produces = {MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE})
    public ResponseEntity<Resource> downloadAlbumImage(@RequestParam("filename")String filename){
        final String PATH = "C:\\Users\\김시환\\Desktop\\Git\\Trip-Reviewer\\src\\main\\resources\\static\\albumImages\\"+filename;
        Resource resource = new FileSystemResource(PATH);
        return new ResponseEntity<>(resource,HttpStatus.OK);
    }

    @PostMapping
    public void addAlbum(@ModelAttribute AlbumRequestDto albumRequestDto){
        albumService.addAlbum(albumRequestDto);
    }

    @DeleteMapping
    public void deleteAlbumByAlbumIds(@RequestBody AlbumUpdateDto updateDto){
        albumService.deleteAlbum(updateDto.getIds());
    }

    private List<AlbumResponseDto> getAlbums(Long areaId){
        return albumService.findAllAlbumByAreaId(areaId);
    }

    @Getter
    @Setter
    static class AlbumUpdateDto{
        private Long areaId;
        private List<Long> ids;

    }

}
