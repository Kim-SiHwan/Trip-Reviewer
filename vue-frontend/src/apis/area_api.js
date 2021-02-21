
import Send from "@/apis/common_api";

function getAreas(){
    return Send({
        url:'/api/area',
        method:'GET'
    })
}

function getArea(areaId){
    return Send({
        url:'/api/area/'+areaId,
        method:'GET',
    })
}

function changeAreaInfo(areaInfo){
    return Send({
        url:'/api/area',
        method:'PUT',
        data:areaInfo

    })
}

function initAreaInfo(areaId){
    return Send({
        url:'/api/area/'+areaId,
        method:'PUT',
    })
}


export default {getAreas,getArea,changeAreaInfo,initAreaInfo}