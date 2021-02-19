package kim.sihwan.trip_reviewer.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long id;
    private String area;
    private String title;
    private String content;
    private String thumbnail;
    private LocalDateTime createDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy="review")
    //private List<Comment> comments = new ArrayList<>();
    private Set<Comment> comments = new HashSet<>();

    @OneToMany(mappedBy="review", cascade = CascadeType.PERSIST)
    //private List<ReviewAlbum> reviewAlbums = new ArrayList<>();
    private Set<ReviewAlbum> reviewAlbums = new HashSet<>();

    @OneToMany(mappedBy="review")
    private List<ReviewTag> reviewTags = new ArrayList<>();

    @Builder
    public Review(String area, String title, String content, LocalDateTime createDate) {
        this.area = area;
        this.title = title;
        this.content = content;
        this.createDate = createDate;
    }

    public void addThumbnail(String thumbnailUrl) {
        this.thumbnail = thumbnailUrl;
    }

    //- 연관관계 편의 메소드 -
    public void addMember(Member member){
        this.member=member;
    }


}
