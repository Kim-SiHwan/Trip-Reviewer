
import Send from "@/apis/common_api";

function getReview(payload){
    return Send({
        url:'/api/review/'+payload,
        method:'GET'
    })
}

function getAllReviews(){
    return Send({
        url:'/api/review/all/0',
        method:'GET',

    })
}

function getReviewsByTag(payload){
    return Send({
        url:'/api/review/all/'+payload,
        method:'GET'
    })
}

function getMyReviewsByUsername(payload){
    return Send({
        url:'/api/review/my/'+payload,
        method:'GET'
    })
}

function uploadReview(payload){

    return Send({
        url:'/api/review',
        method:'POST',
        headers:{'Content-Type': 'multipart/form-data'},
        data: payload
    })
}

function deleteReview(payload){
    return Send({
        url:'/api/review/'+payload,
        method:'DELETE'
    })
}







export default {getReview,getAllReviews,getReviewsByTag,uploadReview,deleteReview, getMyReviewsByUsername};