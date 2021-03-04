<template>
  <v-app>
    <div id="app" style="">


      <h1 >{{info}}</h1>

      <div id="mapDiv" class="map-wrapper">
        <svg id="mainMap" viewBox="-100 400 2000 1600">

        </svg>
      </div>


      <v-row id="dialogRow" style="margin-top: 500px">
        <v-dialog id="dialogBody" v-model="dialog" max-width="600"
        eager="eager">
          <v-card id="dialogCard">
            <div id="modalDiv" class="modalDiv">
              <br><br>
              <h2>{{ infos }}</h2>
              <br>
              <h2 v-if="selectedArea.title"><small>&lt;{{selectedArea.title}}&gt;</small></h2>
              <h2 v-else><small>&lt;타이틀이 없습니다&gt;</small></h2>
            </div>

            <svg id="area" viewBox="70 0 500 500">
              <path>
              </path>
            </svg>

            <v-card-actions>
              <v-spacer></v-spacer>
              <router-link to="/detail" style="text-decoration: none" @click="dialog=false">
                <v-btn color="blue darken-1" text >수정하기</v-btn>
              </router-link>
              <v-btn color="red darken-1" text @click="dialog=false">취소하기</v-btn>
            </v-card-actions>
          </v-card>
        </v-dialog>
      </v-row>
    </div>

  </v-app>


</template>

<script>
import * as d3 from 'd3';

const MAP = require('./custom.json');


export default {
  name: 'sample',
  data() {
    return {
      dialog:false,
      areaId:'',
      title:'',
      loginDto:'',
      info:'',
      geoJSON : {
        type: "FeatureCollection",
        features: [
          {
            type: "Feature",
            geometry: {
              type: "MultiPolygon",
              coordinates: '',
            },
            properties: {}
          }
        ]
      }


    }
  },
  methods:{
    selectArea(area){
      this.info=area;
    },

    drawMap(){
      const geojson = MAP;
      const width= 1500;
      const height = 1700;
      this.idx=-1;
      let svg= d3.select('#mainMap')
          .attr('width',width)
          .attr('height',height);


      let projection = d3.geo.mercator()
          .scale(1)
          .translate([0,0]);
      const path = d3.geo.path().projection(projection);
      const bounds = path.bounds(geojson);
      const widthScale = (bounds[1][0] - bounds[0][0]) / width;
      const heightScale = (bounds[1][1] - bounds[0][1]) / height;
      const scale = (1.2 / Math.max(widthScale, heightScale));
      const xOffset = width/2 - scale * (bounds[1][0] + bounds[0][0]) /2 ;
      const yOffset = height/2 - scale * (bounds[1][1] + bounds[0][1])/2 ;
      const offset = [xOffset+100, yOffset+300];
      projection.scale(scale).translate(offset);

      const _this= this;

      function fillFn(d){
        let idx= d.properties.idx;
        return _this.areas[idx-1].color;
      }

      function mouseover(d){

        _this.prevColor=this.style.fill;
        this.infos=d.properties.SIG_KOR_NM;
        d3.select(this).style('fill', '#1483ce');
        if(d) {
          _this.selectArea(d.properties.SIG_KOR_NM);
        }
      }

      function mouseout(){
        d3.select(this).style('fill',_this.prevColor);
      }

      function mouseclick(d) {
        console.log(d);

        console.log(d.properties.idx);
        _this.areaId = _this.areas[d.properties.idx-1].id;

        console.log(_this.prevColor);
        _this.code=d.properties.SIG_CD;
        console.log(_this.selectedPath);

        _this.geoJSON.features[0].geometry.coordinates= d.geometry.coordinates;
        _this.getArea();

        let width2=600, height2=600;
        let projection2 = d3.geo.mercator()
            .scale(1)
            .translate([0,0]);

        const path2 = d3.geo.path().projection(projection2);
        const bounds2 = path2.bounds(_this.geoJSON);
        const widthScale2 = (bounds2[1][0] - bounds2[0][0]) / width2;
        const heightScale2 = (bounds2[1][1] - bounds2[0][1]) / height2;
        const scale2 = (0.6 / Math.max(widthScale2, heightScale2));
        const xOffset2 = width2/2 - scale2 * (bounds2[1][0] + bounds2[0][0]) /2 + 80;
        const yOffset2 = height2/2 - scale2 * (bounds2[1][1] + bounds2[0][1])/2 + 50;
        const offset2 = [xOffset2-60, yOffset2-100];
        projection2.scale(scale2).translate(offset2);

        _this.dialog=true;
        if(_this.dialog){
          let svg2= d3.select('#area').attr('width',600).attr('height',600);

          svg2
              .select('path')
              .data(_this.selectedPath.features)
              .attr('d',path2)
              .style('fill', _this.prevColor)
              .attr('stroke','black');



        }

      }

      svg
          .selectAll('path')
          .data(geojson.features)
          .enter().append('path')
          .attr('d', path)
          .attr('vector-effect', 'non-scaling-stroke')
          .attr('stroke','black')
          .style('fill', fillFn)
          .on('mouseover',mouseover)
          .on('mouseout',mouseout)
          .on('click',mouseclick);

      svg
          .selectAll('text')
          .data(geojson.features)
          .enter()
          .append('text')
          .attr('x',function (d){
            if(d.properties.SIG_KOR_NM==="과천"){
              return path.centroid(d)[0]-15;
            }
            if(d.properties.SIG_KOR_NM==="부천"){
              return path.centroid(d)[0]-15;
            }
            if(d.properties.SIG_KOR_NM==="인천"){
              return path.centroid(d)[0];
            }
            if(d.properties.SIG_KOR_NM==="강화"){
              return path.centroid(d)[0]-7;
            }
            if(d.properties.SIG_KOR_NM==="신안"){
              return path.centroid(d)[0]-40;
            }
            if(d.properties.SIG_KOR_NM==="목포"){
              return path.centroid(d)[0]-15;
            }
            if(d.properties.SIG_KOR_NM==="장성"){
              return path.centroid(d)[0]-13;
            }
            if(d.properties.SIG_KOR_NM==="전주"){
              return path.centroid(d)[0]-13;
            }
            if(d.properties.SIG_KOR_NM==="군포"){
              return path.centroid(d)[0]-10;
            }
            if(d.properties.SIG_KOR_NM==="시흥"){
              return path.centroid(d)[0]-8;
            }
            if(d.properties.SIG_KOR_NM==="안양"){
              return path.centroid(d)[0]-12;
            }
            if(d.properties.SIG_KOR_NM==="속초"){
              return path.centroid(d)[0]-5;
            }
            return path.centroid(d)[0]-20;})
          .attr('y',function (d){
            if(d.properties.SIG_KOR_NM==="부천"){
              return path.centroid(d)[1]+3;
            }
            if(d.properties.SIG_KOR_NM==="안산"){
              return path.centroid(d)[1]+5;
            }
            if(d.properties.SIG_KOR_NM==="수원"){
              return path.centroid(d)[1]+3;
            }
            if(d.properties.SIG_KOR_NM==="오산"){
              return path.centroid(d)[1]+3;
            }
            if(d.properties.SIG_KOR_NM==="하남"){
              return path.centroid(d)[1]+4;
            }
            if(d.properties.SIG_KOR_NM==="태안"){
              return path.centroid(d)[1]-20;
            }
            if(d.properties.SIG_KOR_NM==="예산"){
              return path.centroid(d)[1]-8;
            }
            if(d.properties.SIG_KOR_NM==="보령"){
              return path.centroid(d)[1]-7;
            }
            if(d.properties.SIG_KOR_NM==="군산"){
              return path.centroid(d)[1]+10;
            }
            if(d.properties.SIG_KOR_NM==="목포"){
              return path.centroid(d)[1]+5;
            }
            if(d.properties.SIG_KOR_NM==="진도"){
              return path.centroid(d)[1]+10;
            }
            if(d.properties.SIG_KOR_NM==="완도"){
              return path.centroid(d)[1]+5;
            }
            if(d.properties.SIG_KOR_NM==="강진"){
              return path.centroid(d)[1]-15;
            }
            if(d.properties.SIG_KOR_NM==="장흥"){
              return path.centroid(d)[1]-25;
            }
            if(d.properties.SIG_KOR_NM==="고흥"){
              return path.centroid(d)[1]+20;
            }
            if(d.properties.SIG_KOR_NM==="남해"){
              return path.centroid(d)[1]+15;
            }
            if(d.properties.SIG_KOR_NM==="거제"){
              return path.centroid(d)[1]+8;
            }
            if(d.properties.SIG_KOR_NM==="창원"){
              return path.centroid(d)[1]-6;
            }
            if(d.properties.SIG_KOR_NM==="사천"){
              return path.centroid(d)[1]-10;
            }
            if(d.properties.SIG_KOR_NM==="동해"){
              return path.centroid(d)[1]+3;
            }
            if(d.properties.SIG_KOR_NM==="부산"){
              return path.centroid(d)[1]+10;
            }
            if(d.properties.SIG_KOR_NM==="청도"){
              return path.centroid(d)[1]+10;
            }
            if(d.properties.SIG_KOR_NM==="경산"){
              return path.centroid(d)[1]+10;
            }
            if(d.properties.SIG_KOR_NM==="포항"){
              return path.centroid(d)[1]-15;
            }


            return path.centroid(d)[1];})
          .style('font-size',13)
          .text(function (d){return d.properties.SIG_KOR_NM});

    },
    getArea(){
      this.$store.dispatch('REQUEST_GET_AREA',this.areaId);
      this.$store.dispatch('REQUEST_GET_PATH',this.geoJSON);

    },
    initData(){
      this.$store.dispatch('REQUEST_GET_AREAS');
    },
    logout(){
      this.idx=-1;
      this.$store.dispatch('REQUEST_LOGOUT',this.username);

    }

  },
  mounted() {
    this.drawMap();
  },
  computed:{
    infos(){
      return this.info;
    },
    areas(){
      return this.$store.state.areaStore.areas;
    },
    username(){
      return this.$store.state.memberStore.username;
    },
    selectedArea(){
      return this.$store.state.areaStore.selectedArea;
    },
    selectedPath(){
      return this.$store.state.areaStore.selectedPath;
    },
    authenticated(){
      return this.$store.state.memberStore.authenticated;
    }

  }



}

</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}

#mainMap{
  overflow: visible;
  white-space: nowrap;
}
</style>
