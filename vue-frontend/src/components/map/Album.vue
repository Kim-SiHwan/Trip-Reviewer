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
      selectedImageIds:[],
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
      let targetImg = this.selectedImageIds.indexOf(fileId);
      if(targetImg!=-1){
        console.log("타겟있음" +targetImg)
        this.selectedImageIds.splice(targetImg,1);
      }else{
        console.log("타겟없음")
        this.selectedImageIds.push(fileId);

      }
      console.log(this.selectedImageIds);
    },
    updateAlbum(){
      let sendForm={
        areaId : this.selectedArea.id,
        ids : this.selectedImageIds
      }
      this.$store.dispatch('REQUEST_UPDATE_ALBUM',sendForm);
    },
    upload(formData){
      formData.append('areaId',this.selectedArea.id);
      this.$store.dispatch('REQUEST_UPLOAD_FILES',formData);
    },
    selectedFile(event){
      const files = event;
      let formData = new FormData;
      for(let file in files){
        formData.append('files',files[file]);
      }
      this.sendFileData= formData;
    }
  },
  created() {
    this.$store.dispatch('REQUEST_GET_FILES',this.selectedArea.id);
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

