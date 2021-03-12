# TripReviewer
## 소개
TripReviewer는 대한민국 행정구역 데이터를 시각화 해 
자신만의 지도를 생성하고, 방문 표시와 사진첩에 사진 삽입 등
가꾸며 여행지의 리뷰를 통한 다른 사람들과의 소통을 할 수 있는
프로젝트입니다. 

---
### 프로젝트 사용
<a> http://ec2-13-125-95-122.ap-northeast-2.compute.amazonaws.com:8080/#/ </a>
지도 기능은 로그인한 사용자만 사용할 수 있습니다.
회원가입 : 공백이 없는 알파벳, 숫자를 이용한 6~12자 이내
비밀번호 : 공백이 알파벳, 숫자 ( 특수문자 가능 )를 이용한 6~12자 이내

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
<details>
<summary>회원</summary>
<div markdown="1">       
+ 회원 등록
+ 회원 인증
+ 로그인
+ 로그아웃
+ 개인 지도 생성
</div>
</details>
<details>
  
<summary>지역</summary>
<div markdown="1">     
+ 전체 지역구 조회 ( 지도 )
+ 단일 지역구 조회
+ 단일 지역구에 대한 방문 표시 ( 색상, 여행 제목, 방문 일자, 동행 여부, 사진 삽입 )
</div>
</details>


