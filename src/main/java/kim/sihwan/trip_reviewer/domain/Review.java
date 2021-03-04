package kim.sihwan.trip_reviewer.domain;


import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
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

    @OneToMany(mappedBy="review", cascade = CascadeType.ALL)
    //private List<Comment> comments = new ArrayList<>();
    private Set<Comment> comments = new HashSet<>();

    @OneToMany(mappedBy="review", cascade = CascadeType.ALL)
    //private List<ReviewAlbum> reviewAlbums = new ArrayList<>();
    private Set<ReviewAlbum> reviewAlbums = new HashSet<>();

    @OneToMany(mappedBy="review", cascade = CascadeType.ALL)
    private Set<ReviewTag> reviewTags = new HashSet<>();

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
    public void changeTitle(String title){
        this.title = title;
    }
    public void changeContent(String content){
        this.content = content;
    }

    //- 연관관계 편의 메소드 -
    public void addMember(Member member){
        this.member=member;
    }




}
