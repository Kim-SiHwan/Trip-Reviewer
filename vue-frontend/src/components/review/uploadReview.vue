<template>
  <v-app>
    <v-container>
      <h2>{{selectedArea.name}}</h2>
      <h3 v-if="selectedArea.title"><small >&lt;{{selectedArea.title}}&gt;</small></h3>
      <h3 v-else><small>&lt;타이틀이 없습니다&gt;</small></h3>


        <v-row align="center" class="fill-height" justify="center" >
          <div id="uploadReviewDiv" class="elevation-5" style="width: 800px; height: 600px" >

            <div class="mt-15 ml-15 mr-15 mb-15">
              <v-text-field
                  v-bind:label="selectedArea.name+'여행 리뷰의 제목을 입력해주세요 '"
                  v-model="reviewData.title">

              </v-text-field>
              <v-textarea
                  no-resize
                  v-model="reviewData.content"
                  label="내용을 입력해주세요.">

              </v-textarea>

              <v-file-input
                  chips
                  multiple
                  @change="selectedFile"
                  label="사진을 1장 이상 선택해주세요.">

              </v-file-input>
              <v-text-field
                  v-if="this.reviewData.tags.length<3"
                  v-on:keyup.enter="addTags" v-model="tagName" label="태그입력해주세요 [ 3개 까지의 태그만 입력가능합니다 ]"></v-text-field>
              <div v-for="(tag,index) in reviewData.tags" :key="index" style="display: inline">
                <v-chip
                  class="mr-3"
                  close
                  label
                  small
                  @click:close="deleteTag(index)"
                  color="info">
                  {{tag}}
                </v-chip>
              </div>
            </div>

      </div>
        </v-row>

      <v-btn
          large
          color="success"
          @click="upload(sendFileData)">등록</v-btn>

    </v-container>
  </v-app>

</template>

<script>
export default {
  name: "uploadReview",
  data(){
  return{
    reviewData:{
      area:'',
      title:'',
      username:'',
      content:'',
      tags:[],

    },
    sendFileData:'',
    tagName:''
  }
},
methods:{
  addTags(){
    this.reviewData.tags.push(this.tagName);
    this.tagName='';
    console.log(this.reviewData.tags);
  },
  deleteTag(tagIdx){
    this.reviewData.tags.splice(tagIdx,1);
  },

  upload(formData){

    console.log(formData);
    if(formData===''){
      this.$store.commit('SET_SNACK_BAR',{
        msg:'사진을 1장 이상 선택해주세요.',color:'warning'
      });
      return false;


    }
    formData.append('area',this.reviewData.area);
    formData.append('title',this.reviewData.title);
    formData.append('content',this.reviewData.content);
    formData.append('username',this.reviewData.username);
    formData.append('tags',this.reviewData.tags);

    console.log(this.reviewData.areaName);
    this.$store.dispatch('REQUEST_UPLOAD_REVIEW',formData);
/*
    axios.request({
      url:'/api/review',
      method:'POST',
      headers:{'Content-Type' : 'multipart/form-data'},
      data: formData

    })*/

    /*axios.request({
      url:'/api/file/'+this.$route.query.areaId,
      method:'POST',
      data : formData
    }).then(res=>{
      console.log(res);
    })*/
  },
  selectedFile(event){
    const files = event;
    console.log(files);
    console.log("uplOAD")
    let formData = new FormData;
    for(let file in files){
      formData.append('files',files[file]);
    }

    this.sendFileData= formData;
  }
},
  created() {
    this.reviewData.area = this.selectedArea.name;
    this.reviewData.username = this.$store.state.memberStore.username;
  },
  computed:{
    selectedArea(){
      return this.$store.state.areaStore.selectedArea;
    }
  }


}
</script>

<style scoped>

</style>