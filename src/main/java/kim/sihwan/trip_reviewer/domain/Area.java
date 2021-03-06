package kim.sihwan.trip_reviewer.domain;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@NoArgsConstructor
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

    public void changeTitle(String title){
        this.title = title;
    }
    public void changeColor(String color){
        this.color = color;
    }
    public void changeAccompany(String accompany){
        this.accompany = accompany;
    }
    public void changeVisitDate(String visitDate){
        this.visitDate = visitDate;
    }

    public void updateArea(String title, String color, String accompany, String visitDate){
        this.title = title;
        this.color = color;
        this.accompany = accompany;
        this.visitDate = visitDate;
    }

    public void initArea(){
        this.title="";
        this.color="#ffffff";
        this.accompany="";
        this.visitDate="";
    }

    //- 연관관계 편의 메소드 -
    public void addMember(Member member){
        this.member = member;
    }

}
