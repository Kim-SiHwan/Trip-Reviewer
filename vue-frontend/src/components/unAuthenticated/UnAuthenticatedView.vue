<template>
  <v-app>
    <h3 style="margin-top: 70px">자신만의 여행 지도를 가꾸고 추억을 간직하세요</h3>
    <v-container style="margin-top: 40px">
      <v-carousel height="700">
        <v-carousel-item
            v-for="(item,i) in items"
            :key="i"
            :src="item.src">
        </v-carousel-item>
      </v-carousel>
    </v-container>
  </v-app>
</template>

<script>
export default {
  name: "UnAuthenticatedView",
  data () {
    return {
      items: [
        {
          src: 'https://cdn.vuetifyjs.com/images/carousel/squirrel.jpg'
        },
        {
          src: 'https://cdn.vuetifyjs.com/images/carousel/sky.jpg'
        },
        {
          src: 'https://cdn.vuetifyjs.com/images/carousel/bird.jpg'
        },
        {
          src: 'https://cdn.vuetifyjs.com/images/carousel/planet.jpg'
        }
      ]
    }
  },

}
</script>

<style scoped>

</style>