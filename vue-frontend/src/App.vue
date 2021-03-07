<template>
  <v-app id="app">

    <v-app-bar id="bar"
        absolute
        color="orange lighten-2"
        elevation="10"
        dark>

      <v-toolbar-title>
        <router-link :to="{path:'/'}"
                     style="color: white; text-decoration: none">
          Trip-Reviewer <small>당신만의 여행지도</small>
        </router-link>
      </v-toolbar-title>

      <v-spacer></v-spacer>
      <v-spacer></v-spacer>

      <v-btn
          color="orange lighten-1"
          class="ml-4"
          small
          v-if="$store.getters.isAuthenticated">
        <router-link to="/main"
                     style="color: white; text-decoration: none">지도보기</router-link>
      </v-btn>
      <v-btn
          color="orange lighten-1"
          class="ml-4"
          small
          v-if="!$store.getters.isAuthenticated">
        <router-link to="/login"
                     style="color: white; text-decoration: none">로그인</router-link>
      </v-btn>

      <v-btn
          color="orange lighten-1"
          class="ml-4 pt-4"
          small
          v-else @click="logout" >
        <p style="color: white; text-decoration: none">로그아웃</p>
      </v-btn>
      <router-link to="/join"
                   style="color: white; text-decoration: none">
        <v-btn
          color="orange lighten-1"
          small
          class="ml-4">회원가입</v-btn>

      </router-link>
      <router-link to="/reviewList"
                   style="color: white; text-decoration: none">
      <v-btn
          color="orange lighten-1"
          small
          class="ml-4">리뷰</v-btn>

      </router-link>

      <router-link to="/my"
                   style="color: white; text-decoration: none">
        <v-btn
            v-if="$store.getters.isAuthenticated"
            color="orange lighten-1"
            small
            class="ml-4">마이페이지</v-btn>

      </router-link>
    </v-app-bar>




    <hr>
    <router-view style="margin-top: 70px"></router-view>

    <v-snackbar
        v-model="snackBarInfo.open"
        :color="snackBarInfo.color"
        :timeout="snackBarInfo.timeout"
        top>
      {{snackBarInfo.text}}
    </v-snackbar>

  </v-app>


</template>

<script>


export default {
  name: 'App',
  components:{
  },
  methods:{
    logout(){
      this.$store.dispatch('REQUEST_LOGOUT');
    },
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
#bar{
  width: 100%;
}
</style>
