<template>
  <v-app>
    <v-container>

      <h2>{{reviewInfo.area}}</h2>
      <small>{{reviewInfo.createDate.substring(0,10)}} {{reviewInfo.createDate.substring(11,16)}}</small>
      <h3 v-if="reviewInfo.title">
        <small v-if="!updateFlag">&lt;&nbsp;{{reviewInfo.title}}&nbsp;&gt;</small>
        <v-text-field
            v-else
            v-model="updateForm.title"
            outlined
            label="수정할 제목을 입력해주세요."
        ></v-text-field>
      </h3>
      <div v-for="(tags,index) in reviewInfo.tags" :key="index"
           style="list-style: none; display: inline;">
        <v-chip
            color="info"
            class="ml-0 mr-1 pr-2 pl-2"
            label
            small>
          {{tags.tag}}
        </v-chip>
      </div>
      <v-row justify="center" class="mt-5">
      <v-col

          v-for="(file,index) in reviewInfo.reviewAlbums" :key="index"
          class="d-flex child-flex"
          cols="4"
      >
        <div id="reviewFileImgDiv">
        <v-img
            :src="file.url"
            class="grey lighten-4"
            contain
            width="500"
            aspect-ratio="1.2"
        >

        </v-img>
        </div>

      </v-col>
    </v-row>


    <v-textarea
      v-if="reviewInfo && !updateFlag"
      v-bind:value="reviewInfo.content"
      v-bind:rows="reviewInfo.content.length/5"
      readonly="readonly"
      no-resize
      outlined
      class="mt-10"
      background-color="white"
      ></v-textarea>
      <v-textarea
        v-else
        v-model="updateForm.content"
        outlined
        label="수정할 내용을 입력해주세요."
        v-bind:value="reviewInfo.content"
        class="mt-10"
        background-color="white">

      </v-textarea>
      <div
          v-if="reviewInfo.username === username || username === 'admin4166' "

          id="reviewDetailBtnDiv" class="float-right">
        <v-btn
            v-if="!updateFlag"
            @click="updateReviewForm"
            color="info"
        >
          수정
        </v-btn>
        <v-btn
            color="warning"
            class="mr-5"
            v-else
            @click="updateFlag=false"
        >
          취소
        </v-btn>
        <v-btn
            v-if="updateFlag"
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
  data(){
    return{
      updateFlag:false,
      updateForm:{
        reviewId:'',
        title:'',
        content:'',
      },
      selectedImageIds:[],

    }
  },
  components:{
    'comment': Comment
  },
  methods:{
    updateReviewForm(){
      this.updateFlag=true;
    },
    updateReview(){
      this.updateForm.reviewId = this.reviewInfo.id;
      this.$store.dispatch('REQUEST_UPDATE_REVIEW',this.updateForm);
      this.$store.dispatch('REQUEST_GET_REVIEW',this.updateForm.reviewId);
      this.updateFlag=false;
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
    },
    username(){
      return this.$store.state.memberStore.username;
    }

  },

}
</script>

<style scoped>

</style>