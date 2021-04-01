# TripReviewer
## 소개
TripReviewer는 대한민국 행정구역 데이터를 시각화 해 
자신만의 지도를 생성하고, 방문 표시와 사진첩에 사진 삽입 등
가꾸며 여행지의 리뷰를 통한 다른 사람들과의 소통 할 수 있는
프로젝트입니다. 

---
### 프로젝트 사용
<a> http://ec2-13-125-95-122.ap-northeast-2.compute.amazonaws.com:8080/#/ </a> <br>
지도 기능은 <b>로그인한 사용자</b>만 사용할 수 있습니다.<br>
회원가입 : 공백이 없는 알파벳, 숫자를 이용한 6 - 12자 이내<br>
비밀번호 : 공백이 없는 알파벳, 숫자 ( 특수문자 가능 )를 이용한 6 - 12자 이내

---
### 배포 아키텍처
![아키텍처](https://user-images.githubusercontent.com/66605925/110928942-c3390c80-836a-11eb-8940-ab709f17d4c2.PNG)


---
### 실행 GIF
![TRGIF](https://user-images.githubusercontent.com/66605925/111725293-287c8880-88aa-11eb-8efa-056cbc3b9346.gif)


---
### 개발 환경
Back <br>
`Spring Web` `Spring Boot` `Spring Data JPA` `Spring Security` `Maria DB` `Swagger2`

Front <br>
`Node` `Npm` `Vue` `Vue-Router` `Vuetify` `Vuex` `Vuex - Persisted State` `Axios` `D3`

Deploy <br>
`AWS EC2` `AWS S3`

---
### 프로젝트 설계
###### 도메인 객체
![엔티티 객체](https://user-images.githubusercontent.com/66605925/110919736-ee6a2e80-835f-11eb-8233-06d6a896db74.PNG)

###### 테이블 설계
![테이블 설계](https://user-images.githubusercontent.com/66605925/110919758-f4f8a600-835f-11eb-9814-1fa46361b3cc.PNG)
###### 도메인 객체 분석
- Member (회원)⇒ 아이디와 닉네임, 비밀번호를 가지며 인증을 위한 권한 정보 role또한 가지고 있다.

- Area (지역구)⇒ Member와 지역구 이름, 방문표시를 위한 색, 제목, 방문일자, 동행 여부와 보다 쉽게 처리하기 위해 idx를( 행정구역 그리는 json 데이터와 일치시킴 )가지고 있다.

- Album (앨범)⇒ Area와 사진을 내려주기위한 url, 중복을 방지하는 filenae과 사진의 기존 이름 originFilename을 가지고 있다.

- Review (리뷰)⇒ 지역구의 이름, 리뷰의 내용과 제목 생성일자와 썸네일 표시를 위한 사진의 url 그리고 ReviewAlbum, Review Tag, Comment를 List 형식으로 가지고 있다.

- ReviewAlbum (리뷰 앨범)⇒ Review와 url, filename, originFilename을 가지고 있다.

- ReviewTag (리뷰에 달린 태그)⇒ 다대다 매핑을 풀어낸 테이블로 Review와 Tag를 가지고 있다.

- Tag (해시태그)⇒ 리뷰에 매핑해 사용될 tag를 가지고 있다.

- Comment (댓글)⇒ 작성자와 내용, 생성일자를 가지고 있다.

###### 연관관계 매핑 분석
- Area와 Member ⇒ 다대일 단방향 관계로, Area에서 Member를
 참조한다.<br>

- Area와 Album ⇒ 일대다 양방향 관계로, 외래키가 있는 Album을 
연관관계의 주인으로 설정하고, Area는 List의 형태로 Album을 참조한다.<br>

- Review와 ReviewAlbum ⇒ 일대다 양방향 관계로, 외래키가 있는 
ReviewAlbum을 연관관계의 주인으로 설정한다. Review는 List의 형태로 ReviewAlbum을 참조한다.<br>

- Review와 Comment ⇒ 위와 동일하다.<br>

- Review와 ReviewTag, Tag ⇒ Review와 Tag 다대다 관계를 
Review와 ReviewTag를 일대다 양방향 관계,
ReviewTag와 Tag를 다대일 단방향 관계로 풀어낸 것으로
외래키가 있는 ReviewTag를 연관관계의 주인으로 설정한다. Review는 List의 형태로 ReviewTag를 참조한다.

---
### API
![전체](https://user-images.githubusercontent.com/66605925/110917180-f83e6280-835c-11eb-945f-42ab8e144502.PNG)
![어드민](https://user-images.githubusercontent.com/66605925/110917274-1015e680-835d-11eb-9a04-f8ce571c7875.PNG)
![멤버](https://user-images.githubusercontent.com/66605925/110917287-16a45e00-835d-11eb-85b2-59a9ed2cb37e.PNG)
![지역](https://user-images.githubusercontent.com/66605925/110917295-1a37e500-835d-11eb-8160-c554f4e069fa.PNG)
![사진첩](https://user-images.githubusercontent.com/66605925/110917306-1e640280-835d-11eb-93fd-2590938534d5.PNG)
![리뷰](https://user-images.githubusercontent.com/66605925/110917318-215ef300-835d-11eb-8899-0637e769447c.PNG)
![댓글](https://user-images.githubusercontent.com/66605925/110917328-2459e380-835d-11eb-86c2-317ddfdb1e32.PNG)
![홈](https://user-images.githubusercontent.com/66605925/110917360-2b80f180-835d-11eb-91d4-da4eb1fbac94.PNG)

---
### 구현 기능
###### 회원
+ 회원 등록
+ 회원 인증
+ 로그인
+ 로그아웃
+ 개인 지도 생성

###### 지역
+ 전체 지역구 조회 ( 지도 )
+ 단일 지역구 조회
+ 단일 지역구에 대한 방문 표시 ( 색상, 여행 제목, 방문 일자, 동행 여부, 사진 삽입 )
+ 지역구 초기화 ( 사진첩을 포함한 모두 초기화 )

###### 앨범 
AWS S3 저장소를 사용해 사진 저장, 사진 조회
+ 사진 추가
+ 사진 조회
+ 사진 삭제

###### 리뷰
+ 리뷰 생성
+ 리뷰 조회
+ 리뷰 수정
+ 리뷰 삭제
+ 리뷰 사진 추가 ( AWS S3 저장소를 사용해 사진 저장,  )
+ 리뷰 태그 추가
+ 자신이 쓴 리뷰 목록 조회

###### 댓글
+ 댓글 생성
+ 댓글 조회
+ 댓글 수정
+ 댓글 삭제
+ 자신이 쓴 댓글 목록 조회

###### 태그
+ 태그 생성 ( 3개 제한 )
+ 태그 삭제 ( 관리자 권한 )
+ 태그를 통한 리뷰 조회 ( 태그 클릭으로 해당 태그가 존재하는 전체 리뷰 조회 )

###### 유효성 검증
Spring Validate를 사용한 유효성 검증
+ 회원가입
+ 로그인
+ 리뷰 생성, 수정
+ 댓글 생성, 수정

###### 사용자 인증
Spring Security , JsonWebToken을 사용한 사용자 인증
- 회원가입, 로그인, 리뷰 조회, 댓글 조회를 제외한 모든 작업에 대해 인증


---
### 문제점 및 해결 방법
- 핵심 기능인 지도 그려내기의 막막함
  - CSS 등을 사용해 하나하나 그려 해결해볼까 했으나 행정구역 JSON 파일을 구해 QGIS를 사용함으로 직접 지도를 커스텀 한 뒤 D3 라이브러리를 이용해 시각화
- JPA 사용 시 N+1 문제로, 쿼리가 매우 많이 나가는 현상 발생
  - 객체 참조를 LAZY 설정
  - EntityGraph를 사용해 객체 그래프 탐색을 한번에 마치도록 변경해 한방 쿼리로 작성
- 배포를 위한 빌드를 할 때  Gradle 오류가 발생했으나 해결하지 못했음
  - Gradle을 Maven으로 변경해 배포 완료
- 배포 시 이미지 저장경로 등에 대한 고민
  - AWS의 S3 버킷을 사용해 이미지 저장과 조회 구현

---
### 아쉬운 부분 
- 구현하고자 하는 핵심 기능들은 모두 구현 했으나, D3와 같은 새로운 라이브러리를 사용함으로서 Front단에서 시간이 많이 들었음
- CI / CD , Nginx 등을 사용해 무중단 배포 구축하지 못한 것 ( 추가 예정 )
- 조금 더 Optional스럽게 작성하지 못했다고 생각
- 테이블 수도 많고 조금 더 체계적인 서비스를 제작해보고 싶음

---
### 프로젝트를 진행하며 느낀 점
- 새로이 알게된 D3와 같은 기술들을 적용하기 위해 알아가는 것이 재밌었음
- 기존 코드를 리팩토링 함으로 좀 더 깔끔하게 정돈되어 가는 모습이 좋았고 조금이지만 더 효율적으로 작성하는 방법을 알게 되었음
- JPA의 편리함을 알았으나 위험 요소 ( N+1, 잘못된 연관관계 매핑 등 )들을 잘 파악하고 사용해야 겠다고 
- AWS를 통해 배포를 진행하며 S3, EC2, RDS 등을 글로만 보다 실제로 적용해봐 좋았고, 제대로 배포가 진행된 것 같아서 좋았음
- Back과 Front 두 부분을 진행해봐 의미 있는 경험이었다고 생각함
- 조금 더 처리할 데이터도 많고 복잡한 프로젝트를 진행해보고 싶다고 느낌
