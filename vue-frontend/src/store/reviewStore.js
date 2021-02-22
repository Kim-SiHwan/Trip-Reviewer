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
        async REQUEST_GET_ALL_REVIEWS(context,payload){
            try{
                console.log(payload);
                const response = await review_api.getAllReviews(payload);
                context.commit('SET_REVIEW_LIST',response.data);
                console.log(response);
            }catch (e) {
                console.log("리뷰 리스트 불러오기 실패");
                context.commit('SET_SNACK_BAR',{
                    msg:'리뷰 리스트 불러오기를 실패했습니다.',color:'error'
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
            try{
                console.log(payload);
                console.log(payload.tags)
                const response = await review_api.uploadReview(payload);
                context.commit('SET_REVIEW_LIST',response.data);
                console.log(response);
                context.commit('SET_SNACK_BAR',{
                    msg:'리뷰를 등록했습니다.',color:'success'
                });
            }catch(e){
                console.log("리뷰 업로드 실패");
                console.log(e);
                context.commit('SET_SNACK_BAR',{
                    msg:'리뷰 등록을 실패했습니다.',color:'error'
                });
            }
        },
        async REQUEST_DELETE_REVIEW(context,payload){
            try{
                console.log(payload);
                const response = await review_api.deleteReview(payload);
                context.commit('SET_REVIEW_LIST',response.data);
                context.commit('SET_SNACK_BAR',{
                    msg:'리뷰를 삭제했습니다.',color:'success'
                });
            }catch (e) {
                context.commit('SET_SNACK_BAR',{
                    msg:'리뷰 삭제를 실패했습니다..',color:'error'
                });
            }
        },
        async REQUEST_GET_ALL_MY_REVIEWS_BY_USERNAME(context,payload){
            try{
                console.log(payload);
                const response = await review_api.getMyReviewsByUsername(payload);
                console.log(response);
                context.commit('SET_MY_REVIEW_LIST',response.data);
            }catch(e){
                console.log("리뷰 리스트 불러오기 실패");
                context.commit('SET_SNACK_BAR',{
                    msg:'리뷰 리스트 불러오기를 실패했습니다.',color:'error'
                });
            }
        }


    }
}

export default reviewStore;