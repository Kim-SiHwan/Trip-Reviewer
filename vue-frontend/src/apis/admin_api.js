import Send from "./common_api"

function getMembers(){
    return Send({
        url:'/api/admin/member',
        method:'GET'
    });
}

function getTags(){
    return Send({
        url:'/api/admin/tag',
        method:'GET'
    })
}

function deleteTag(payload){
    return Send({
        url:'/api/admin/tag/'+payload,
        method:'DELETE'
    })
}

export default {getMembers,getTags,deleteTag}



