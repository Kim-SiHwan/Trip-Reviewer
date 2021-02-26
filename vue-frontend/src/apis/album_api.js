
import Send from "@/apis/common_api";

function getFiles(payload){
    return Send({
        url:'/api/album/'+payload,
        method:'GET'
    })
}

function uploadFiles(payload){
    return Send({
        url:'/api/album/',
        data:payload,
        method:'POST'

    })
}

function deleteFiles(payload){
    return Send({
        url:'/api/album',
        method:'DELETE',
        data:payload
    })
}



export default {getFiles,uploadFiles,deleteFiles}