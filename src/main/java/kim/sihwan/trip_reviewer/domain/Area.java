package kim.sihwan.trip_reviewer.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Area {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "area_id")
    private Long id;
    private int idx;
    private String name;
    private String color;
    private String title;
    private String visitDate;
    private String accompany;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy="area",cascade =CascadeType.ALL)
    private List<Album> albums = new ArrayList<>();

    @Builder
    public Area(int idx, String name, String color, String title, String visitDate, String accompany) {
        this.idx = idx;
        this.name = name;
        this.color = color;
        this.title = title;
        this.visitDate = visitDate;
        this.accompany = accompany;
    }

    //- 연관관계 편의 메소드 -
    public void addMember(Member member){
        this.member = member;
    }

}
