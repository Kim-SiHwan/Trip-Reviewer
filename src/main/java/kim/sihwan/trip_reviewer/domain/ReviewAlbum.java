package kim.sihwan.trip_reviewer.domain;


import lombok.*;

import javax.persistence.*;


@Entity
@Getter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class ReviewAlbum {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_album_id")
    private Long id;
    private String url;
    private String filename;
    private String originFilename;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id")
    private Review review;

    @Builder
    public ReviewAlbum(String url, String filename, String originFilename) {
        this.url = url;
        this.filename = filename;
        this.originFilename = originFilename;
    }

    //- 연관관계 편의 메소드 -
    public void addReview(Review review){
        System.out.println(review.getId());
        this.review = review;
        this.review.getReviewAlbums().add(this);
        System.out.println(this.review.getReviewAlbums().size());
    }
}
