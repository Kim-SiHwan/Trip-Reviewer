<template>
  <v-app>
    <v-snackbar
        v-model="snackBarInfo.open"
        :color="snackBarInfo.color"
        :timeout="snackBarInfo.timeout">
      {{snackBarInfo.text}}
    </v-snackbar>
  </v-app>
</template>

<script>
export default {
  name: "SnackBar",
  computed:{
    snackBarInfo(){
      return this.$store.state.commonStore.snackBar;
    }
  }
}
</script>

<style scoped>
body{
}

</style>