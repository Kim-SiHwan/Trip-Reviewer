<template>
  <v-app>
    <v-container>
      <div id="userInfo">
        아이디 : {{userInfo}}
        <br>
        닉네임 : 닉네임
        <br>
        내가 쓴 리뷰 목록
        <div id="myReviewZone">
          {{myReviewList}}

        </div>
        <br>
        내가 쓴 댓글 목록
        <div id="myCommentZone">
          {{myCommentList}}
        </div>
        <br>
        내가 방문한 여행지
        <div id="myTripZone">

        </div>
      </div>

    </v-container>
  </v-app>

</template>

<script>
export default {
  name: "MyPage",
  data(){
    return{
      t:'뭔데'
    }
  },
  created() {
    console.log("크리에이트 마이페이지")
  },
  mounted() {
    console.log("마운트! 마이페이지")
    this.$store.dispatch('REQUEST_GET_ALL_MY_REVIEWS_BY_USERNAME',this.userInfo);
    this.$store.dispatch('REQUEST_GET_ALL_MY_COMMENTS_BY_USERNAME',this.userInfo);
  },
  computed:{
    userInfo(){
      return this.$store.state.memberStore.username;
    },
    myReviewList(){
      return this.$store.state.reviewStore.myReviewList;
    },
    myCommentList(){
      return this.$store.state.commentStore.myCommentList;
    }
  }
}
</script>

<style scoped>

</style>