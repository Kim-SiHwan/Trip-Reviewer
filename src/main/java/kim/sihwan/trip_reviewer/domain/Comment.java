package kim.sihwan.trip_reviewer.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;


@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;
    private String content;
    private String username;
    private LocalDateTime createDate;


 /*   @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;*/


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id")
    private Review review;

    @Builder
    public Comment(String content,String username, LocalDateTime createDate) {
        this.content = content;
        this.username = username;
        this.createDate = createDate;
    }

    public void changeContent(String content){
        this.content = content;
    }

    //- 연관관계 편의 메소드 -
//    public void addMember(Member member){
//        this.member = member;
//    }
    public void addReview(Review review){
        this.review = review;
        this.review.getComments().add(this);
    }
}
