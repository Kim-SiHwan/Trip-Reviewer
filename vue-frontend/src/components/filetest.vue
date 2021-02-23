<template>
  <v-app>
    <v-container>
    <h2>{{selectedArea.name}}</h2>
    <h3 v-if="selectedArea.title"><small>&lt;{{selectedArea.title}}&gt;</small></h3>
    <h3 v-else><small>&lt;타이틀이 없습니다&gt;</small></h3>

      <v-row justify="center">
      <v-col
          v-for="(file,index) in fileInfo" :key="index"
          class="d-flex child-flex"
          cols="4"
      >
        <div id="galleryImgDiv" >
        <v-img
            :src="file.url"
            contain
            height="320"
            @click="selectedImg(file.id,$event.target)"
            max-height="320"
            max-width="400"
            class="grey lighten-2"
        ></v-img>
        </div>

      </v-col>
    </v-row>

    <v-file-input
      chips
      multiple
      @change="selectedFile"
      label="파일 올려주세요">

    </v-file-input>
    <v-btn
        color="success"
        @click="upload(sendFileData)">
      추가하기
    </v-btn>
      <v-btn
          color="success"
          @click="updateAlbumForm">
        수정하기
      </v-btn>
      <v-btn
          color="success"
          v-if="updateFlag"
          @click="updateAlbum">
        수정전송
      </v-btn>
<!--    <v-img :src="`http://localhost:8080/api/download?fileName=${test.png}`"></v-img>-->

<!--    <v-img :src="fileInfo.fileUrl"></v-img>-->
<!--    <v-img src="http://localhost:8080/api/file/download?fileName=test.png"></v-img>-->
    </v-container>
  </v-app>
</template>

<script>
export default {
  name: "filetest",
  data(){
    return{
      sendFileData:'',
      url:'http://localhost:8080/api/file/w',
      selectedImgInfo:[],
      updateFlag:false
    }
  },
  methods:{
    updateAlbumForm(){
      if(this.updateFlag)
        this.updateFlag=false;
      else
        this.updateFlag=true;
    },
    selectedImg(fileId,event){
      console.log(event);
      if(!this.updateFlag)
        return false;
      let targetImg = this.selectedImgInfo.indexOf(fileId);
      if(targetImg!=-1){
        console.log("타겟있음" +targetImg)
        this.selectedImgInfo.splice(targetImg,1);
      }else{
        console.log("타겟없음")
        this.selectedImgInfo.push(fileId);

      }
      console.log(this.selectedImgInfo);
    },
    updateAlbum(){
      let sendForm={
        areaId : this.selectedArea.id,
        fileList : this.selectedImgInfo
      }
      this.$store.dispatch('REQUEST_UPDATE_ALBUM',sendForm);
      this.$store.dispatch('REQUEST_GET_FILES',this.selectedArea.id);
    },
    upload(formData){
      formData.append('areaId',this.selectedArea.id);
      this.$store.dispatch('REQUEST_UPLOAD_FILES',formData);
      this.$store.dispatch('REQUEST_GET_FILES',this.selectedArea.id);
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
    console.log(this.$route.query.areaId);
    this.$store.dispatch('REQUEST_GET_FILES',this.selectedArea.id);
/*    axios.request({
      url:'/api/file/all',
      params:{
        areaId:this.$route.query.areaId
      },
      method:'GET'
    }).then(res=>{
      this.fileInfo= res.data;
      console.log(res);
    })*/

  },
  computed:{
    selectedArea(){
      return this.$store.state.areaStore.selectedArea;
    },
    fileInfo(){
      return this.$store.state.albumStore.fileInfo;
    }
  }
}
</script>

<style scoped>

</style>

