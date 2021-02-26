import comment_api from "@/apis/comment_api";

const commentStore={
    state:{
        commentList:'',
        myCommentList:''

    },
    mutations:{
        SET_COMMENT_LIST(state,payload){
            state.commentList=payload;
        },
        SET_MY_COMMENT_LIST(state,payload){
            state.myCommentList = payload;
        }
    },
    actions:{
        async REQUEST_GET_ALL_COMMENTS(context,payload){
            const response = await comment_api.getAllComments(payload);
            if(response){
                context.commit('SET_COMMENT_LIST',response.data);
            }

        },
        async REQUEST_ADD_COMMENT(context,payload){
            const addCommentResponse = await comment_api.addComment(payload);
            if(addCommentResponse){
                const response = await comment_api.getAllComments(payload.reviewId);
                    context.commit('SET_COMMENT_LIST', response.data);
                    context.commit('SET_SNACK_BAR', {
                        msg: '댓글이 등록되었습니다.', color: 'success'
                    })
            }

        },
        async REQUEST_DELETE_COMMENT(context,payload){
            const deleteCommentResponse = await comment_api.deleteComment(payload.commentId);
            if(deleteCommentResponse){
                const response = await comment_api.getAllComments(payload.reviewId);
                context.commit('SET_COMMENT_LIST', response.data);
                context.commit('SET_SNACK_BAR', {
                    msg: '댓글이 삭제되었습니다.', color: 'success'
                })
            }
        },
        async REQUEST_UPDATE_COMMENT(context,payload){
            const updateCommentResponse = await comment_api.updateComment(payload);
            if(updateCommentResponse){
                const response = await comment_api.getAllComments(payload.reviewId);
                context.commit('SET_COMMENT_LIST', response.data);
                context.commit('SET_SNACK_BAR', {
                    msg: '댓글이 수정되었습니다.', color: 'success'
                })
            }
        },
        async REQUEST_GET_ALL_MY_COMMENTS_BY_USERNAME(context,payload){
            const response = await comment_api.getMyCommentsByUsername(payload);
            if(response){
                context.commit('SET_MY_COMMENT_LIST',response.data);
            }

        }
    }
}
export default commentStore