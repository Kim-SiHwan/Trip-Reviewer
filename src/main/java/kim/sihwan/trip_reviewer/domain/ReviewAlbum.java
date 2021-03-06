package kim.sihwan.trip_reviewer.domain;


import lombok.*;

import javax.persistence.*;


@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewAlbum {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_album_id")
    private Long id;
    private String url;
    private String originFilename;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "review_id")
    private Review review;

    @Builder
    public ReviewAlbum(String url, String originFilename) {
        this.url = url;
        this.originFilename = originFilename;
    }

    //- 연관관계 편의 메소드 -
    public void addReview(Review review){
        this.review = review;
        this.review.getReviewAlbums().add(this);
    }
}
