<template>
  <v-app>
    <v-container>


    <h2>{{selectedArea.name}}</h2>
    <h3 v-if="selectedArea.title"><small>&lt;{{selectedArea.title}}&gt;</small></h3>
    <h3 v-else><small>&lt;타이틀이 없습니다&gt;</small></h3>
      <small>{{selectedArea.accompany}} {{selectedArea.visitDate}}</small>
      {{size}}

    <div>
      <svg id="ww" viewBox="450 0 500 500">
        <path></path>
      </svg>



      <div id="btnDiv" class="float-right">
        <v-btn
            @click="goUploadReview"
            class="mr-7"
            color="primary">리뷰쓰기</v-btn>
        <v-btn
            @click="goGallery"
            color="success">사진첩</v-btn>
      </div>

      <br><br><br>



      <br>
      <v-row justify="center">
        <v-text-field
            style="width: 1500px"
            v-model="title"
            v-bind:label="selectedArea.name +'여행의 제목을 입력해주세요.'"
        ></v-text-field>
        <br>


        <v-color-picker
            class="ma-2"
            width="1500px"
            hide-inputs
            hide-canvas
            show-swatches
            swatches-max-height="97px"
            @update:color="setColor"
        ></v-color-picker>


        <div id="detailBtnDiv" style="width: 100%">
          <v-btn
              @click="changeAreaInfo"
              color="primary">
            설정하기
          </v-btn>

          <v-btn
              @click="initAreaInfo"
              color="error"
              class="float-right">
            초기화
          </v-btn>
        </div>

      </v-row>
      <v-layout justify-center>
        <v-flex>
          <v-row class="">
            <div id="dd" style="width: 100%">

            <v-col
                cols="5"
                sm="6"
            >
              <v-select
                  hide-details
                  style="width: 100px"
                  :items="items"
                  label="동행"
                  v-model="selectedItems"
                  dense
              ></v-select>
            </v-col>

            <v-col
                cols="10"
            >
              <v-menu
                  nudge-width="40"
                  v-model="menu"
                  offset-y
                  min-width="auto"
                  :close-on-content-click="false"
                  transition="scale-transition"
              >
                <template v-slot:activator="{ on, attrs }">
                  <v-text-field
                      v-model="date"
                      style="width: 200px"
                      label="방문일을 선택해주세요"
                      prepend-icon="mdi-calendar"
                      readonly
                      v-bind="attrs"
                      v-on="on"
                  ></v-text-field>
                </template>
                <v-date-picker
                    v-model="date"
                    @input="menu = false"
                ></v-date-picker>
              </v-menu>
            </v-col>            </div>

          </v-row>

        </v-flex>
      </v-layout>
    </div>

    </v-container>
  </v-app>

</template>

<script>
import * as d3 from 'd3';
export default {
  name: "detail",
  data(){
    return{
      sendInfo:{
        title:'',
        areaId:'',
        areaIdx:''
      },
      color:'',
      title:'',
      size:'',

      date:new Date().toISOString().substring(0,10),
      menu:false,
      items: ['단독','애인','친구','가족','기타'],
      selectedItems:''
    }
  },
  methods:{
    changeAreaInfo(){
      this.sendInfo={
        areaId : this.selectedArea.id,
        areaIdx : this.selectedArea.idx,
        title : this.title === '' ? this.selectedArea.title : this.title,
        color : this.color === '#FF0000' ? this.selectedArea.color : this.color,
        visitDate: this.date === '' ? this.selectedArea.visitDate : this.date,
        accompany : this.selectedItems === '' ? this.selectedArea.accompany : this.selectedItems
      };


      this.$store.dispatch('REQUEST_CHANGE_AREA_INFO',this.sendInfo);

    },
    initAreaInfo(){
      this.sendInfo={
        areaId : this.selectedArea.id,
        areaIdx : this.selectedArea.idx
      }
      this.$store.dispatch('REQUEST_INIT_AREA_INFO',this.sendInfo);

    },

    setColor(color){
      this.color=color.hex;
      this.drawArea(color.hex);
    },
    goUploadReview(){
      this.$router.push('/uploadReview');
    },
    goGallery(){
      this.$router.push('/album')
    },
    drawArea(color){
      let _this=this;
      let width=600, height=600;
      let projection = d3.geo.mercator()
          .scale(1)
          .translate([0,0]);
      const path = d3.geo.path().projection(projection);
      const bounds = path.bounds(_this.selectedPath);
      const widthScale = (bounds[1][0] - bounds[0][0]) / width;
      const heightScale = (bounds[1][1] - bounds[0][1]) / height;
      const scale = (0.6 / Math.max(widthScale, heightScale));
      const xOffset = width/2 - scale * (bounds[1][0] + bounds[0][0]) /2 + 80;
      const yOffset = height/2 - scale * (bounds[1][1] + bounds[0][1])/2 + 50;
      const offset = [xOffset+300, yOffset-100];
      projection.scale(scale).translate(offset);

      let svg2= d3.select('#ww').attr('width','100%').attr('height',600)

      svg2
          .select('path')
          .data(_this.selectedPath.features)
          .attr('d',path)
          .style('fill', color)
          .attr('stroke','black');


    },

  },
  mounted() {
    this.drawArea(this.selectedArea.color);

  },
  computed:{
    selectedArea(){
      return this.$store.state.areaStore.selectedArea;
    },
    selectedPath(){
      return this.$store.state.areaStore.selectedPath;
    },

  }

}

</script>

<style scoped>

</style>