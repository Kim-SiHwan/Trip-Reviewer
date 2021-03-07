<template>
  <v-app>

    <p><small v-if="reviewList">{{ reviewList.length }}개의 리뷰가 있습니다</small></p>

    <hr>
    <div>
      <v-chip
          v-if="tag.id!=0"
          close
          color="info"
          label
          small
          @click:close="clickTag({
          id:0,
          tag:''
          })"
      >
        {{ tag.tag }}
      </v-chip>
    </div>

    <div id="reviewsDiv" class="row justify-center mt-15">
      <ul v-for="(list,index) in reviewList" :key="index"
          style="list-style: none">
        <li id="listDiv">
          <div class="p-5 mb-5 rounded float-left"
               style="width: 500px; height: 600px; border: 1px solid cornflowerblue">
            <div class="card-body" style="margin-top: 50px">
              <span><strong>&lt;&nbsp; {{ list.title }} &gt;&nbsp;</strong></span>
              <br>
              <span class="float-right card-subtitle">
              <span class="float-right mt-1 mr-3"><small>작성자 : {{ list.username }}</small></span>
              <br>
              <span class="float-right mt-1 mr-3"> <small>작성일 : {{
                  reviewInfo.createDate.substring(0, 10)
                }}</small></span>
              <br>
              <span class="float-right mt-1 mr-3"><small>지역 : {{ list.area }}</small></span>
              </span>
              <br>

              <div id="imgDiv" style="height: 100%; width: 100%">
                <router-link :to="{path:'/reviewDetail',query:{reviewId:list.id}}">
                  <v-img
                      :src="list.thumbnail"
                      aspect-ratio="1"
                      class="mt-15 mr-3 ml-13 grey lighten-3"
                      width="400"
                  >

                  </v-img>
                </router-link>

                <div v-for="(tags,index) in list.tags" :key="index" style="list-style: none; display: inline">
                  <v-chip
                      class="ml-0 mr-1 pr-2 pl-2"
                      color="info"
                      label
                      small
                      @click="clickTag(tags)">
                    {{ tags.tag }}
                  </v-chip>
                </div>

                <div id="reviewListIconDiv" class="mt-4 mb-5">
                  <v-icon
                      color="blue darken-4">
                    mdi-message-text
                  </v-icon>
                  {{ list.commentCount }}

                  <v-icon
                      color="green">
                    mdi-image-multiple
                  </v-icon>
                  {{ list.reviewAlbumsCount }}
                </div>
              </div>
            </div>
          </div>
        </li>
      </ul>
    </div>

  </v-app>
</template>

<script>
export default {
  name: "reviewList",
  methods: {
    showReviewList() {
      this.$store.dispatch('REQUEST_GET_ALL_REVIEWS_BY_TAG', this.tag);
    },
    clickTag(tag) {
      this.$store.dispatch('REQUEST_GET_ALL_REVIEWS_BY_TAG', tag);
    }

  },
  computed: {
    reviewList() {
      return this.$store.state.reviewStore.reviewList;
    },
    reviewInfo() {
      return this.$store.state.reviewStore.reviewInfo;
    },
    tag() {
      return this.$store.state.reviewStore.tag;
    }
  },
  mounted() {
    this.showReviewList();

  }
}
</script>

<style scoped>

small {
  color: black;
}

</style>