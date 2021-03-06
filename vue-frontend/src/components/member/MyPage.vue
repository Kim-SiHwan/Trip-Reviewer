<template>
  <v-app>
    <v-container>
      <div id="username">
        <router-link to="/admin"
        v-if="this.$store.getters.isAuthenticated && username==='admin'">관리자페이지</router-link>
        <br>
        내가 쓴 리뷰 목록
        <div id="myReviewZone" v-if="myReviewList">
          <div id="reviewsDiv" class="row justify-center mt-15">
            <ul v-for="(list,index) in myReviewList" :key="index"
                style="list-style: none">
              <li id="listDiv">
                <div class="p-5 mb-5 rounded float-left"
                     style="width: 500px; height: 500px; border: 1px solid cornflowerblue">
                  <div class="card-body">
                    <span><strong>{{ list.title }}</strong></span>
                    <br>


                    <span class="float-right card-subtitle">
              <span class="float-right mt-1 mr-3"><small>작성자 : {{ list.username }}</small></span>
              <br>
              <span class="float-right mt-1 mr-3"><small>지역 : {{ list.area }}</small></span>
                  </span>

                    <br>

                    <div id="imgDiv" style="height: 100%; width: 100%">
                      <router-link :to="{path:'/reviewDetail',query:{reviewId:list.id}}">

                        <v-img
                            :src="list.thumbnail"
                            class="mt-15 mr-3 ml-13 black lighten-2"
                            contain
                            height="320"
                            max-height="320"
                            max-width="400"
                        >

                        </v-img>
                      </router-link>

                      <div v-for="(tags,index) in list.tags" :key="index" style="list-style: none; display: inline">
                        <v-chip
                            color="info"
                            class="ml-0 mr-1 pr-2 pl-2"
                            label
                            small>
                          {{tags.tag}}
                        </v-chip>
                      </div>

                      <div id="reviewListIconDiv" class="mt-4 mb-5">
                        <v-icon
                            color="blue darken-4"
                        >
                          mdi-message-text
                        </v-icon>
                        {{ list.commentCount }}

                        <v-icon

                            color="green"
                        >
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

        </div>
        <p v-else>작성한 리뷰가 없습니다.</p>
        <br>
        <hr>
        내가 쓴 댓글 목록
        <hr>
        <div id="myCommentZone">
          <v-data-table
              v-if="myCommentList"
              id="replyDiv"
              :headers="headers"
              :items="myCommentList"
              :items-per-page="itemsPerPage"
              :page.sync="page"
              class="elevation-1"
              hide-default-header
              hide-default-footer
              no-data-text="작성한 댓글이 없습니다."
              @page-count="pageCount= $event">

            <template v-slot:item="commentList">
              <tr>
                <td width="500">

                  <v-textarea
                      no-resize
                      outlined
                      class="mt-5"
                      readonly="readonly"
                      rows="4"
                      v-bind:value="commentList.item.content"
                  >
                  </v-textarea>
                </td>




                <td width="100">{{ commentList.item.username }}</td>
                <td width="100"> 날짜</td>
                <td width="100">


                </td>
              </tr>
            </template>

          </v-data-table>
          <v-pagination
              v-model="page"
              :length="pageCount">

          </v-pagination>

        </div>

      </div>

    </v-container>
  </v-app>

</template>

<script>
export default {
  name: "MyPage",
  data(){
    return{
      itemsPerPage:3,
      pageCount: 0,
      page:1,
      headers: [
        {
          text: '내용',
          align: 'start',
          sortable: false
        },
        {
          text: '글쓴이'
        },
        {
          text: '작성일'
        },
        {
          text: '수정/삭제'
        }
      ],
    }
  },
  created() {
  },
  mounted() {
    this.$store.dispatch('REQUEST_GET_ALL_MY_REVIEWS_BY_USERNAME',this.username);
    this.$store.dispatch('REQUEST_GET_ALL_MY_COMMENTS_BY_USERNAME',this.username);
  },
  computed:{
    username(){
      return this.$store.state.memberStore.username;
    },
    myReviewList(){
      return this.$store.state.reviewStore.myReviewList;
    },
    myCommentList(){
      return this.$store.state.commentStore.myCommentList;
    }
  }
}
</script>

<style scoped>

</style>