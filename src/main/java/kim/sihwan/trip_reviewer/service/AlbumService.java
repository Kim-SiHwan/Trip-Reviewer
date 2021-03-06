package kim.sihwan.trip_reviewer.service;

import kim.sihwan.trip_reviewer.config.awsS3.S3Uploader;
import kim.sihwan.trip_reviewer.domain.Album;
import kim.sihwan.trip_reviewer.domain.Area;
import kim.sihwan.trip_reviewer.dto.area.album.AlbumRequestDto;
import kim.sihwan.trip_reviewer.dto.area.album.AlbumResponseDto;
import kim.sihwan.trip_reviewer.exception.cumtomException.AreaNotFoundException;
import kim.sihwan.trip_reviewer.exception.cumtomException.FileSizeOverException;
import kim.sihwan.trip_reviewer.repository.AlbumRepository;
import kim.sihwan.trip_reviewer.repository.AreaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AlbumService {
    private final AlbumRepository albumRepository;
    private final AreaRepository areaRepository;
    private final S3Uploader s3Uploader;
    public List<AlbumResponseDto> findAllAlbumByAreaId(Long areaId) {
        return albumRepository.findAllByArea_Id(areaId)
                .stream()
                .map(AlbumResponseDto::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public void addAlbum(AlbumRequestDto albumRequestDto) throws IOException {
        Area area = areaRepository.findById(albumRequestDto.getAreaId())
                .orElseThrow(AreaNotFoundException::new);
        for(MultipartFile file : albumRequestDto.getFiles()){
            String saveUrl = s3Uploader.upload(file,"static");
            Album album = Album
                    .builder()
                    .url(saveUrl)
                    .filename("")
                    .originFilename(file.getOriginalFilename())
                    .build();
            album.addArea(area);
        }

/*        String fileUrl = "C:\\Users\\김시환\\Desktop\\Git\\Trip-Reviewer\\src\\main\\resources\\images\\albumImages\\";


        String saveUrl = "http://localhost:8080/api/album/download?filename=";
        try {
            Area area = areaRepository.findById(albumRequestDto.getAreaId()).orElseThrow(AreaNotFoundException::new);
            for(MultipartFile file : albumRequestDto.getFiles()) {
                String newFilename = createNewFilename(file.getOriginalFilename());
                System.out.println(file);
                File dest = new File(fileUrl + newFilename);


                file.transferTo(dest);
                System.out.println(file);
                System.out.println(dest);
                System.out.println(file.getSize()+" "+dest.length());
                Album album = Album
                        .builder()
                        .url(saveUrl + newFilename)
                        .filename(newFilename)
                        .originFilename(file.getOriginalFilename())
                        .build();
                album.addArea(area);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }*/
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
