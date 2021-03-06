<template>
  <v-app style="height: 1200px">
    <h3 style="margin-top: 70px">자신만의 여행 지도를 가꾸고 추억을 간직하세요</h3>

    <v-container style="margin-top: 40px">
      <v-carousel height="800">
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
          src: 'https://kim-si-hwan-trip-s3.s3.ap-northeast-2.amazonaws.com/static/mainImages/main1.jpg'
        },
        {
          src: 'https://kim-si-hwan-trip-s3.s3.ap-northeast-2.amazonaws.com/static/mainImages/main2.jpg'
        },
        {
          src: 'https://kim-si-hwan-trip-s3.s3.ap-northeast-2.amazonaws.com/static/mainImages/q3.jpg'
        },
        {
          src: 'https://kim-si-hwan-trip-s3.s3.ap-northeast-2.amazonaws.com/static/mainImages/q4.jpg'
        },
        {
          src: 'https://kim-si-hwan-trip-s3.s3.ap-northeast-2.amazonaws.com/static/mainImages/q9.PNG'
        }
      ]
    }
  },

}
</script>

<style scoped>

</style>