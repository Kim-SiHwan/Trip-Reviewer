package kim.sihwan.trip_reviewer.service;

import kim.sihwan.trip_reviewer.domain.Area;
import kim.sihwan.trip_reviewer.dto.area.AreaResponseDto;
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

}
