package kim.sihwan.trip_reviewer.domain;


import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;


@Entity
@Getter
@NoArgsConstructor
public class ReviewTag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_tag_id")
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id")
    private Review review;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tag_id")
    private Tag tag;

    //- 연관관계 편의 메소드 -
    public void addReview(Review review){
        this.review = review;
        this.review.getReviewTags().add(this);
    }

    public void addTag(Tag tag){
        this.tag = tag;
    }

}
