<template>
  <v-app>
    <v-container>


      <h2>{{reviewInfo.area}}</h2>
      <h3 v-if="reviewInfo.title"><small >&lt;{{reviewInfo.title}}&gt;</small></h3>
      <h3 v-else><small>&lt;타이틀이 없습니다&gt;</small></h3>
    <v-row justify="center">
      <v-col

          v-for="(file,index) in reviewInfo.reviewAlbums" :key="index"
          class="d-flex child-flex"
          cols="3"
      >
        <div id="reviewFileImgDiv" style="width: 100%; height: 100%;">
        <v-img
            :src="file.url"

            class="grey lighten-2"
            contain

            height="320"
            max-height="320"
            max-width="400"

        ></v-img>
        </div>

      </v-col>
    </v-row>
    <v-textarea
      v-if="reviewInfo"
      v-bind:value="reviewInfo.content"
      v-bind:rows="reviewInfo.content.length/5"
      readonly="readonly"
      no-resize
      outlined
      class="mt-10"
      background-color="white"
      ></v-textarea>
      <div
          v-if="reviewInfo.username === this.$store.state.memberStore.username"
          id="reviewDetailBtnDiv" class="float-right">
        <v-btn
            @click="updateReview"
            color="info"
        >
          수정
        </v-btn>
        <v-btn
            @click="deleteReview"
            class="mr-3 ml-5"
            color="error"
          >
          삭제
        </v-btn>
      </div>
      <br><br>
    <comment></comment>
    </v-container>

  </v-app>
</template>

<script>
 import Comment from './Comment'
export default {
  name: "reviewDetail",
  components:{
    'comment': Comment
  },
  methods:{
    updateReview(){

    },
    deleteReview(){
      this.$store.dispatch('REQUEST_DELETE_REVIEW',this.reviewInfo.id);
      this.$store.dispatch('REQUEST_GET_ALL_REVIEWS_BY_TAG', this.tag);

    }
  },
  created() {
    this.$store.dispatch('REQUEST_GET_REVIEW',this.$route.query.reviewId);
  },
  computed:{
    reviewInfo(){
      return this.$store.state.reviewStore.reviewInfo;
    },
    tag(){
      return this.$store.state.reviewStore.tag;
    }

  },

}
</script>

<style scoped>

</style>