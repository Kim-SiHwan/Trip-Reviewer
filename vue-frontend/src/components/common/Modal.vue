<template>
  <v-app>
    <v-container>
      <v-row id="dialogRow" style="margin-top: 500px">
        <v-dialog id="dialogBody" v-model="dialog" max-width="600">
          <v-card id="dialogCard">
            <div id="modalDiv" class="modalDiv">
              <br><br>
              <h2>{{ selectedArea.name }}</h2>
              <br>
              <h2 v-if="selectedArea.title"><small>&lt;{{selectedArea.title}}&gt;</small></h2>
              <h2 v-else><small>&lt;타이틀이 없습니다&gt;</small></h2>
            </div>

            <svg id="ww" viewBox="70 0 500 500">
              <path d="">
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
    </v-container>
  </v-app>

</template>

<script>
export default {
  name: "Modal",
  data(){
    return{
      dialog:false
    }
  },
  computed:{
    selectedArea(){
      return this.$store.state.areaStore.selectedArea;
    },
    selectedPath(){
      return this.$store.state.areaStore.selectedPath;
    }
  }
}
</script>

<style scoped>

</style>