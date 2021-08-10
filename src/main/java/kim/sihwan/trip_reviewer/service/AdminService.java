package kim.sihwan.trip_reviewer.service;

import kim.sihwan.trip_reviewer.domain.Member;
import kim.sihwan.trip_reviewer.domain.Tag;
import kim.sihwan.trip_reviewer.dto.member.MemberResponseDto;
import kim.sihwan.trip_reviewer.dto.tag.TagResponseDto;
import kim.sihwan.trip_reviewer.exception.cumtomException.ForbiddenAccessException;
import kim.sihwan.trip_reviewer.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminService {
    private final MemberRepository memberRepository;
    private final TagService tagService;

    public List<MemberResponseDto> findAllMembers(){
        checkAdmin();
        List<Member> members = memberRepository.findAll();
        return members
                .stream()
                .map(MemberResponseDto::new)
                .collect(Collectors.toList());
    }

    public List<TagResponseDto> findAllTags(){
        checkAdmin();
        List<Tag> tags = tagService.findAllTag();
        return tags
                .stream()
                .map(TagResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteTag(Long tagId){
        checkAdmin();
        tagService.deleteTag(tagId);
    }

    private void checkAdmin(){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        if(!username.equals("admin4166")){
            throw new ForbiddenAccessException();
        }
    }
}
