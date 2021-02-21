<template>
  <v-app id="app">





    <router-link to="/">메인</router-link>
    <router-link to="/login">로그인</router-link>
    <router-link to="/join">회원가입</router-link>
    <router-link @click="alert('as')" to="/reviewList">리뷰</router-link>
    <hr>
    <router-view></router-view>

    <v-snackbar
        v-model="snackBarInfo.open"
        :color="snackBarInfo.color"
        :timeout="snackBarInfo.timeout">
      {{snackBarInfo.text}}
    </v-snackbar>


    <h1>App.vue부분</h1>
  </v-app>


</template>

<script>


export default {
  name: 'App',
  components:{
  },
  methods:{
    hh(){
      this.$store.dispatch('R',5);

    }
  },
  computed:{
    snackBarInfo(){
      return this.$store.state.commonStore.snackBar;
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

}
body{
  width: 100%;
  height: 100%;
}
</style>
