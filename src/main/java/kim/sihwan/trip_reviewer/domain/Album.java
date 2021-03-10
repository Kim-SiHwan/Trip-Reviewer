package kim.sihwan.trip_reviewer.domain;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Entity
@Getter
@NoArgsConstructor
public class Album {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "album_id")
    private Long id;
    private String url;
    private String originFilename;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "area_id")
    private Area area;

    @Builder
    public Album(String url,  String originFilename) {
        this.url = url;
        this.originFilename = originFilename;
    }

    //- 연관관계 편의 메소드 -

    public void addArea(Area area) {
        this.area = area;
        this.area.getAlbums().add(this);
    }
}
