import review_api from "@/apis/review_api";

const reviewStore={
    state:{
        keyword:'default',
        reviewList:'',
        myReviewList:'',
        reviewInfo:'',
        tag:{
            id:'0',
            tag:''
        }
    },
    mutations:{
        SET_REVIEW_LIST(state,payload){
            state.reviewList=payload;
        },
        SET_REVIEW_INFO(state,payload){
            state.reviewInfo=payload;
        },
        SET_REVIEW_TAG(state,payload){
            state.tag=payload;
        },
        SET_MY_REVIEW_LIST(state,payload){
            state.myReviewList = payload;
        }
    },
    actions:{
        async REQUEST_GET_REVIEW(context,payload){
            try{
                const response = await review_api.getReview(payload);
                context.commit('SET_REVIEW_INFO',response.data);
            }catch(e){
                console.log("리뷰 불러오기 실패");
                context.commit('SET_SNACK_BAR',{
                    msg:'리뷰 불러오기를 실패했습니다.',color:'error'
                });
            }
        },

        async REQUEST_GET_ALL_REVIEWS_BY_TAG(context,payload){
          try{
             console.log(payload);
             const response = await review_api.getReviewsByTag(payload.id);
             context.commit('SET_REVIEW_LIST',response.data);
             context.commit('SET_REVIEW_TAG',payload);
          }catch (e) {
              console.log("리뷰 리스트 불러오기 실패");
              context.commit('SET_SNACK_BAR',{
                  msg:'리뷰 리스트 불러오기를 실패했습니다.',color:'error'
              });
          }
        },
        async REQUEST_UPLOAD_REVIEW(context,payload){

            const addResponse = await review_api.uploadReview(payload);
            if(addResponse){
                context.commit('SET_SNACK_BAR',{
                    msg:'리뷰를 등록했습니다.',color:'success'
                });
            }

        },
        async REQUEST_DELETE_REVIEW(context,payload){
            const deleteResponse = review_api.deleteReview(payload);
            if(deleteResponse){
                context.commit('SET_SNACK_BAR',{
                    msg:'리뷰가 삭제되었습니다.',color:'success'
                });
            }
        },
        async REQUEST_GET_ALL_MY_REVIEWS_BY_USERNAME(context,payload){
            const response = await review_api.getMyReviewsByUsername(payload);
            if(response){
                console.log(response);
                context.commit('SET_MY_REVIEW_LIST',response.data);
            }

        }


    }
}

export default reviewStore;