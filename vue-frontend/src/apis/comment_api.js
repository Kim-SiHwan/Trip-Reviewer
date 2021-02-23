import Send from './common_api'

function getAllComments(reviewId){
    return Send({
        url:'/api/comment/'+reviewId,
        method:'GET'
    })
}

function addComment(data){
    return Send({
        url:'/api/comment',
        method:'POST',
        data:data
    })
}

function getMyCommentsByUsername(username){
    return Send({
        url:'/api/comment/my/'+username,
        method:'GET'
    })
}

function deleteComment(commentId){
    return Send({
        url:'/api/comment/'+commentId,
        method:'DELETE'
    })
}

function updateComment(data){
    return Send({
        url:'/api/comment',
        method:'PUT',
        data:data
    })
}
export default {getAllComments,addComment,deleteComment,updateComment,getMyCommentsByUsername}