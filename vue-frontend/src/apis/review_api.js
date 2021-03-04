
import Send from "@/apis/common_api";

function getReview(payload){
    return Send({
        url:'/api/review/'+payload,
        method:'GET'
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

function updateReview(payload){
    return Send({
        url:'/api/review',
        method:'PATCH',
        data:payload
    })
}







export default {getReview,updateReview,getReviewsByTag,uploadReview,deleteReview, getMyReviewsByUsername};