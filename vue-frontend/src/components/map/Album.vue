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
            width="500"
            aspect-ratio="1.2"
            @click="selectedImg(file.id,$event)"
            class="grey lighten-3"
        >
          <p style="color: red; visibility: hidden">선택</p>
        </v-img>
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
        class="mr-5"
        @click="upload(sendFileData)">
      추가하기
    </v-btn>
      <v-btn
          color="success"
          v-if="!updateFlag"
          @click="updateAlbumForm">
        수정하기
      </v-btn>
      <v-btn
          color="warning"
          class="mr-5"
          v-else
          @click="updateFlag=false"
      >
        취소하기
      </v-btn>
      <v-btn
          color="error"
          v-if="updateFlag"
          @click="updateAlbum">
        삭제하기
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
      this.updateFlag = !this.updateFlag;
    },
    selectedImg(fileId,event){
      if(!this.updateFlag)
        return false;
      let targetImg = this.selectedImageIds.indexOf(fileId);
      let x= event.target.childNodes[0];
      if(targetImg!==-1){
        console.log("타겟있음" +targetImg)
        console.log(document.getElementById('pa'));
        console.log(x);
        x.style.visibility = 'hidden';
        this.selectedImageIds.splice(targetImg,1);
      }else{
        console.log("타겟없음")
        x.style.visibility = 'visible';
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
      if(formData==='') {
        this.$store.commit('SET_SNACK_BAR', {
          msg: '사진을 1장 이상 선택해주세요.', color: 'warning'
        });
        return false;
      }

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

