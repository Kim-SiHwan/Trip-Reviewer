package kim.sihwan.trip_reviewer.service;

import kim.sihwan.trip_reviewer.domain.Album;
import kim.sihwan.trip_reviewer.domain.Area;
import kim.sihwan.trip_reviewer.dto.area.AreaRequestDto;
import kim.sihwan.trip_reviewer.dto.area.AreaResponseDto;
import kim.sihwan.trip_reviewer.exception.cumtomException.AreaNotFoundException;

import kim.sihwan.trip_reviewer.repository.AlbumRepository;
import kim.sihwan.trip_reviewer.repository.AreaRepository;
import lombok.RequiredArgsConstructor;
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
        return areaRepository.findAllByMember_Username(username)
                .stream()
                .map(AreaResponseDto::toDto)
                .collect(Collectors.toList());
    }

    public AreaResponseDto findOneByAreaId(Long areaId){
        Area area = areaRepository.findById(areaId)
                .orElseThrow(AreaNotFoundException::new);
        return AreaResponseDto.toDto(area);
    }

    @Transactional
    public void changeAreaInfo(AreaRequestDto requestDto){
        Area area = areaRepository.findById(requestDto.getAreaId())
                .orElseThrow(AreaNotFoundException::new);

        area.updateArea(requestDto.getTitle(), requestDto.getColor(), requestDto.getAccompany(), requestDto.getVisitDate());
/*
        if(!requestDto.getTitle().isEmpty())
            area.changeTitle(requestDto.getTitle());
        if(!requestDto.getColor().isEmpty())
            area.changeColor(requestDto.getColor());
        if(!requestDto.getAccompany().isEmpty())
            area.changeAccompany(requestDto.getAccompany());
        if(!requestDto.getVisitDate().isEmpty())
            area.changeVisitDate(requestDto.getVisitDate());*/

    }

    // 람다표현식
    @Transactional
    public void initArea(Long areaId){
        Area area = areaRepository.findById(areaId)
                .orElseThrow(AreaNotFoundException::new);
        area.initArea();
        List<Long> albumIdList = area.getAlbums()
                .stream()
                .map(Album::getId)
                .collect(Collectors.toList());
        albumRepository.deleteAllAlbumByQuery(albumIdList);
    }
}
