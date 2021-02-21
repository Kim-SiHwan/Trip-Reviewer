import comment_api from "@/apis/comment_api";

const commentStore={
    state:{
        commentList:'',

    },
    mutations:{
        SET_COMMENT_LIST(state,payload){
            state.commentList=payload;
        },
        SET_UPDATE_COMMENT(state,payload){
            state.commentList[payload.idx]=payload.commentData;
        }
    },
    actions:{
        async REQUEST_GET_ALL_COMMENTS(context,payload){
            try{
                const response = await comment_api.getAllComments(payload);
                context.commit('SET_COMMENT_LIST',response.data);
            }catch (e) {
                console.log("댓글 불러오기 실패 ")

                context.commit('SET_SNACK_BAR',{
                    msg:'댓글 불러오기를 실패했습니다.',color:'error'
                });
            }
        },
        async REQUEST_ADD_COMMENT(context,payload){
            try{
                const response = await comment_api.addComment(payload);
                context.commit('SET_COMMENT_LIST',response.data);
            }catch (e) {
                console.log("댓글 작성 실패 ")

                context.commit('SET_SNACK_BAR',{
                    msg:'댓글 작성을 실패했습니다.',color:'error'
                });
            }
        },
        async REQUEST_DELETE_COMMENT(context,payload){
            try{
                const response = await comment_api.deleteComment(payload);
                context.commit('SET_COMMENT_LIST',response.data);
            }catch (e) {
                console.log("댓글 삭제 실패 ")

                context.commit('SET_SNACK_BAR',{
                    msg:'댓글 삭제를 실패했습니다.',color:'error'
                });
            }
        },
        async REQUEST_UPDATE_COMMENT(context,payload){
            try{
                const response = await comment_api.updateComment(payload);
                context.commit('SET_COMMENT_LIST',response.data);
            }catch (e) {
                console.log("댓글 수정 실패 ")

                context.commit('SET_SNACK_BAR',{
                    msg:'댓글 수정을 실패했습니다.',color:'error'
                });
            }
        }
    }
}
export default commentStore