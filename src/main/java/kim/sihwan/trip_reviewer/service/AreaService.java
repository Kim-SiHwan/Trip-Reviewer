package kim.sihwan.trip_reviewer.service;

import kim.sihwan.trip_reviewer.domain.Area;
import kim.sihwan.trip_reviewer.dto.area.AreaRequestDto;
import kim.sihwan.trip_reviewer.dto.area.AreaResponseDto;
import kim.sihwan.trip_reviewer.repository.AlbumRepository;
import kim.sihwan.trip_reviewer.repository.AreaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AreaService {
    private final AreaRepository areaRepository;
    private final AlbumRepository albumRepository;

    public List<AreaResponseDto> findAllByUsername(String username){
        List<Area> areas = areaRepository.findAllByMember_Username(username);
        List<AreaResponseDto> list = areas
                .stream()
                .map(area -> new AreaResponseDto(area))
                .collect(Collectors.toList());
        return list;
    }

    public AreaResponseDto findOneByAreaId(Long areaId){
        Area area = areaRepository.findById(areaId).get();
        AreaResponseDto areaResponseDto = new AreaResponseDto(area);
        return areaResponseDto;
    }

    @Transactional
    public void changeAreaInfo(AreaRequestDto requestDto){
        Area area = areaRepository.findById(requestDto.getAreaId()).get();
        if(!requestDto.getTitle().isEmpty())
            area.changeTitle(requestDto.getTitle());
        if(!requestDto.getColor().isEmpty())
            area.changeColor(requestDto.getColor());
        if(!requestDto.getAccompany().isEmpty())
            area.changeAccompany(requestDto.getAccompany());
        if(!requestDto.getVisitDate().isEmpty())
            area.changeVisitDate(requestDto.getVisitDate());

    }

    @Transactional
    public void initArea(Long areaId){
        Area area = areaRepository.findById(areaId).get();
        area.initArea();
        List<Long> albumIdList = area.getAlbums()
                .stream()
                .map(album -> album.getId())
                .collect(Collectors.toList());
        albumRepository.deleteAllAlbumByQuery(albumIdList);

    }

}
