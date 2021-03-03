package kim.sihwan.trip_reviewer.controller;

import io.swagger.annotations.Api;
import kim.sihwan.trip_reviewer.dto.member.MemberResponseDto;
import kim.sihwan.trip_reviewer.dto.tag.TagResponseDto;
import kim.sihwan.trip_reviewer.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = {"1. Admin"})
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class AdminController {
    private final AdminService adminService;

    @GetMapping("/member")
    public ResponseEntity<List<MemberResponseDto>> findAllMembers(){
        return new ResponseEntity<>(adminService.findAllMembers(), HttpStatus.OK);
    }

    @GetMapping("/tag")
    public ResponseEntity<List<TagResponseDto>> findAllTags(){
        return new ResponseEntity<>(adminService.findAllTags(),HttpStatus.OK);
    }

    @DeleteMapping("/tag/{tagId}")
    public void deleteTag(@PathVariable Long tagId){
        adminService.deleteTag(tagId);
    }
}
