package kim.sihwan.trip_reviewer.service;

import kim.sihwan.trip_reviewer.domain.Review;
import kim.sihwan.trip_reviewer.domain.ReviewTag;
import kim.sihwan.trip_reviewer.domain.Tag;
import kim.sihwan.trip_reviewer.dto.review.ReviewRequestDto;
import kim.sihwan.trip_reviewer.repository.ReviewTagRepository;
import kim.sihwan.trip_reviewer.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TagService {

    private final TagRepository tagRepository;
    private final ReviewTagRepository reviewTagRepository;

    public List<ReviewTag> getReviewIdsByTagId(Long tagId){
        return reviewTagRepository.findAllByTag_Id(tagId);
    }

    @Transactional
    public void addReviewTag(Review review , ReviewRequestDto requestDto){

        Set<String> tags = requestDto.getTags();

        System.out.println("addReviewTag: " +tags);
        tags.forEach(tagName->{
            ReviewTag reviewTag = new ReviewTag();
            Tag tag = addTag(tagName);
            reviewTag.addReview(review);
            reviewTag.addTag(tag);
        });

    }

    @Transactional
    public Tag addTag(String tagName){
        Tag tag = tagRepository.findTagByTag(tagName)
                .orElse(Tag.builder().tag(tagName).build());
        return tagRepository.save(tag);
    }




}
