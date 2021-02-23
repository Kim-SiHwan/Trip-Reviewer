import Send from "@/apis/common_api";

function requestJoin(member){
    return Send({
        url:'/api/member/save',
        method:'POST',
        data:member
    })
}

function requestLogin(member){
    return Send({
        url:'/api/member/login',
        method:'POST',
        data:member
    })
}

function requestLogout(username){
    return Send({
        url:'/api/member/logout/'+username,
        method:'POST',
    })
}

export default {requestJoin,requestLogin,requestLogout};