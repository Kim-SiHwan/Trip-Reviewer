package kim.sihwan.trip_reviewer.service;

import kim.sihwan.trip_reviewer.domain.Album;
import kim.sihwan.trip_reviewer.domain.Area;
import kim.sihwan.trip_reviewer.dto.area.album.AlbumRequestDto;
import kim.sihwan.trip_reviewer.dto.area.album.AlbumResponseDto;
import kim.sihwan.trip_reviewer.dto.exception.FileSizeLimitExceededException;
import kim.sihwan.trip_reviewer.repository.AlbumRepository;
import kim.sihwan.trip_reviewer.repository.AreaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AlbumService {
    private final AlbumRepository albumRepository;
    private final AreaRepository areaRepository;

    public List<AlbumResponseDto> findAllAlbumByAreaId(Long areaId) {
        return albumRepository.findAllByArea_Id(areaId)
                .stream()
                .map(AlbumResponseDto::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public void addAlbum(AlbumRequestDto albumRequestDto) {

        String fileUrl = "C:\\Users\\김시환\\Desktop\\Git\\Trip-Reviewer\\src\\main\\resources\\static\\albumImages\\";
        String saveUrl = "http://localhost:8080/api/album/download?filename=";
        try {
            Area area = areaRepository.findById(albumRequestDto.getAreaId()).get();
            for(MultipartFile file : albumRequestDto.getFiles()) {
                String newFilename = createNewFilename(file.getOriginalFilename());
                System.out.println("새이름 : "+newFilename);
                File dest = new File(fileUrl + newFilename);
                file.transferTo(dest);
                Album album = Album
                        .builder()
                        .url(saveUrl + newFilename)
                        .filename(newFilename)
                        .originFilename(file.getOriginalFilename())
                        .build();
                album.addArea(area);
            }
        }catch (org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException e) {
            System.out.println("파일 업로드 중 오류 발생");
            e.printStackTrace();
            throw new FileSizeLimitExceededException("업로드 하는 사진은 1MB 미만이어야 합니다.");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("파일 업로드 중 오류 발생");
        }
    }

    @Transactional
    public void deleteAlbum(List<Long> albumIdList){
        albumRepository.deleteAllAlbumByQuery(albumIdList);
    }

    public String createNewFilename(String filename){
        UUID uuid = UUID.randomUUID();
        return uuid.toString() +"_" + filename;
    }



}
