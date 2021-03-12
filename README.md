# TripReviewer
## 소개
TripReviewer는 대한민국 행정구역 데이터를 시각화 해 
자신만의 지도를 생성하고, 방문 표시와 사진첩에 사진 삽입 등
가꾸며 여행지의 리뷰를 통한 다른 사람들과의 소통을 할 수 있는
프로젝트입니다. 

---
### 프로젝트 사용
<a> http://ec2-13-125-95-122.ap-northeast-2.compute.amazonaws.com:8080/#/ </a> <br>
지도 기능은 로그인한 사용자만 사용할 수 있습니다.<br>
회원가입 : 공백이 없는 알파벳, 숫자를 이용한 6 - 12자 이내<br>
비밀번호 : 공백이 없는 알파벳, 숫자 ( 특수문자 가능 )를 이용한 6 - 12자 이내

---
### 배포 아키텍처
![아키텍처](https://user-images.githubusercontent.com/66605925/110914846-43a34180-835a-11eb-8b48-dba8297c9933.PNG)

---
### 실행 GIF
![실행 GIF](https://user-images.githubusercontent.com/66605925/110914755-21a9bf00-835a-11eb-8776-6cccc1543a5c.gif)

---
### 개발 환경
Back
+ SpringWeb 2.4.3
+ SpringBoot 2.4.3
+ SpringDataJpa 2.4.3
+ SpringSecurity 2.4.3
+ MariaDB 2.7.2
+ Swagger2 2.6.1

Front
+ node v15.2.0
+ npm 7.0.8
+ vue 2.6.11
+ vue-router 3.4.7
+ vuetify 2.4.0
+ vuex 3.6.2
+ vuex-persistedstate 4.0.0-beta.3
+ axios 0.20.0
+ d3 3.1.7

Deploy
+ AWS EC2
+ AWS S3

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

- Tag (해시태그)⇒ 실제 사용될 tag를 가지고 있다.

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
AWS S3를 사용해 사진 저장, 사진 조회
+ 사진 추가
+ 사진 조회
+ 사진 삭제

###### 리뷰
+ 리뷰 생성
+ 리뷰 조회
+ 리뷰 수정
+ 리뷰 삭제
+ 리뷰 사진 추가 ( AWS S3를 사용해 사진 저장,  )
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


---
### 문제점 및 해결 방법
- 핵심 기능인 지도 그려내기의 막막함
  - 행정구역 JSON 파일을 QGIS를 통해 지저분한 부분을 커스텀 한 뒤 D3 라이브러리를 이용해 시각화
- JPA 사용 시 1+N 문제로, 쿼리가 매우 많이 나가는 현상 발생
  - 객체 참조를 LAZY 설정
  - EntityGraph를 사용해 객체 그래프 탐색을 한번에 마치도록 변경
  - '''     @EntityGraph(attributePaths = {"member","reviewAlbums","reviewTags","reviewTags.tag"},type = EntityGraph.EntityGraphType.LOAD)
    Optional<Review> findReviewById(Long id); '''

  - 
