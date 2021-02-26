<template>
  <v-app>
    <v-container id="commentContainer">

      <v-icon
          color="blue darken-2"
      >
        mdi-message-text
      </v-icon>
      <p v-if="commentList">
      {{commentList.length}}개의 댓글이 달렸습니다
      </p>
      <hr>

      <v-data-table
          v-if="commentList"
          id="replyDiv"
          :headers="headers"
          :items="commentList"
          :items-per-page="itemsPerPage"
          :page.sync="page"
          class="elevation-1"
          hide-default-header
          hide-default-footer
          no-data-text="첫 댓글을 작성해보세요!"
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

              <v-icon
                  small
                  class="mr-2"
                  color="blue"
                  @click="updateCommentForm(
                      commentData={
                      commentId:commentList.item.id,
                      commentContent:commentList.item.content,
                      username:commentList.item.username
                      }

                      )"
              >
                mdi-pencil
              </v-icon>

              <v-icon
                  small
                  color="red"
                  @click="deleteComment(commentList.item.id,commentList.item.username)"
              >
                mdi-delete
              </v-icon>
            </td>
          </tr>
        </template>

      </v-data-table>
      <v-pagination
          v-model="page"
          :length="pageCount">

      </v-pagination>

      <div
          id="addCommentDiv"
>        <v-textarea
            v-model="content"
            label="Content"
            no-resize
            outlined
            rows="3"
            v-on:keyup.enter="addComment">

        </v-textarea>


        <v-btn
            @click="addComment"
            color="primary"
            class="float-right">
          <v-icon dark>
            mdi-pencil
          </v-icon>
        </v-btn>
      </div>



      <v-row justify="center">
        <v-dialog v-model="dialog" persistent max-width="450">
          <v-card>
            <v-card-title class="headline"><small>댓글수정</small></v-card-title>
            <v-textarea
                style="width: 90%; margin-left: 17px"
                v-model="updateContent"
                label="수정할 내용을 입력해주세요."
                no-resize
                outlined
                rows="4"
                v-bind:placeholder="updateForm.commentContent"
            >

            </v-textarea>
            <v-card-actions>
              <v-spacer></v-spacer>
              <v-btn
                  color="primary"
                  @click="updateComment(updateForm.commentId)">수정하기</v-btn>
              <v-btn
                  color="error"
                  @click="dialog = false">취소하기</v-btn>
            </v-card-actions>
          </v-card>
        </v-dialog>
      </v-row>

    </v-container>
    </v-app>
</template>

<script>
export default {
  name: "comment",
  props:['review-infos'],
  data(){
    return{
      reviewId:'',
      sendForm:'',
      updateContent:'',
      content:'',
      dialog: false,
      itemsPerPage:12,
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
      updateForm:{
        commentId:'',
        commentContent:'',
        username:''

      }
    }
  },
  methods:{
    deleteComment(commentId,username) {
      if(this.username == username){
        this.sendForm={
          reviewId : this.reviewId,
          commentId : commentId
        };
        this.$store.dispatch('REQUEST_DELETE_COMMENT',this.sendForm);

      }else{
        this.$store.commit('SET_SNACK_BAR',
            {msg:'작성자만 삭제 할 수 있습니다.', color:'error'}
        );
        return false;
      }
    },
    updateCommentForm(commentData){
      console.log(commentData.commentIdx)

      if(this.username !== commentData.username){
        this.$store.commit('SET_SNACK_BAR',
            {msg:'작성자만 수정 할 수 있습니다.', color:'error'}
        );
        return false;
      }
      this.updateForm={
        commentId:commentData.commentId,
        commentContent:commentData.commentContent,
        username:commentData.username,
        reviewId:this.$route.query.reviewId
      }

      this.dialog=true;

    },
    updateComment(commentId){
      console.log(commentId);
        let data={
          id:commentId,
          content:this.updateContent,
          reviewId:this.$route.query.reviewId
        }
        this.$store.dispatch('REQUEST_UPDATE_COMMENT',data);
        this.dialog=false;

    },
    addComment(){
      this.sendForm={
        reviewId : this.reviewInfo.id,
        username : this.username,
        content : this.content
      }
      this.$store.dispatch('REQUEST_ADD_COMMENT',this.sendForm);
      this.content='';
    }
  },
  created() {
    console.log(this.$route.query.reviewId);
    this.reviewId = this.$route.query.reviewId;


  },
  mounted() {
     this.$store.dispatch('REQUEST_GET_ALL_COMMENTS',this.$route.query.reviewId);
  },
  computed:{
    reviewInfo(){
      return this.$store.state.reviewStore.reviewInfo;
    },
    commentList(){
      return this.$store.state.commentStore.commentList;
    },
    username(){
      return this.$store.state.memberStore.username;
    },
    isAuthenticated(){
      return this.$store.getters.isAuthenticated;
    }

  }
}
</script>

<style scoped>
#commentContainer{
  overflow: auto;
}

</style>