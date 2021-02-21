package kim.sihwan.trip_reviewer.service;

import kim.sihwan.trip_reviewer.domain.Review;
import kim.sihwan.trip_reviewer.domain.ReviewTag;
import kim.sihwan.trip_reviewer.domain.Tag;
import kim.sihwan.trip_reviewer.dto.review.ReviewRequestDto;
import kim.sihwan.trip_reviewer.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TagService {
    private final TagRepository tagRepository;

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
        /*tags.stream()
                .map(tagName -> {
                    Tag tag = addTag(tagName);
                    reviewTag.addReview(review);
                    reviewTag.addTag(tag);
                    return tag;
                }).collect(Collectors.toSet());
*/
    }

    @Transactional
    public Tag addTag(String tagName){
        Tag tag = tagRepository.findTagByTag(tagName)
                .orElse(Tag.builder().tag(tagName).build());
        return tagRepository.save(tag);
    }

    @Transactional
    public void deleteAllWithReview(List<Long> reviewTagIdList){
        tagRepository.deleteAllByIdInQuery(reviewTagIdList);
    }






}
