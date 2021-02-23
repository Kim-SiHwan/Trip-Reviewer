import axios from "axios";
import store from "@/store/store";
import router from "@/routes/index";
const instance = axios.create();

instance.interceptors.request.use(
    async config=>{
        const token = localStorage.getItem("access_token");
        console.log(config.url);
        console.log(token);
        if(!token && (!config.url.includes("review") || !config.url.includes("comment")) && config.method !='GET'){
            console.log("거렸음");
        }
        config.headers={'Authorization' : token}
        return config;
    },
    error => {
        console.log(error);
    }
)
instance.interceptors.response.use(
    response=>{
        return response;
    },
    async error=>{
        console.log(error)
        //액세스토큰이 만료되었음이 나오면?
        console.log(error.response.data.status);
        console.log(error.response.message);
        console.log(error.response.msg);
        const status = error.response.data.status;
        if(status === 450){
            console.log("토큰이 만료되었음.");
            store.commit('SET_SNACK_BAR',{
                msg:"토큰이 만료되었으니 로그인을 다시 시도해주세요.",color:'info'
            })
            store.commit('LOGOUT');
            router.push('/login');
        }else if(status === 451 || status === 452 || status ===453){
            console.log("정상적이지 않은 토큰.");

            store.commit('SET_SNACK_BAR',{
                msg:"비정상적 토큰이므로 로그인을 다시 시도해주세요.",color:'info'
            })
            store.commit('LOGOUT');
            router.push('/login');
        }


        if(error.response.status===401){
            console.log(error.response);
            console.log("토큰이만료되었음.");
           /* await requestRefreshToken(localStorage.getItem('access_token'))
                .then(res=>{
                    console.log(res.data);
                    //현재 리프레시 만료되었을 때 한번에 처리를 못함. 아래 캐치에서 500에러뜰 때 진행되니까
                    //msg를 검사하던, 컨트롤러에서 토큰이 만료되었을 때 에러를 던지던 깔끔하게 처리를 합시다.
                    localStorage.setItem('access_token','Bearer '+res.data)
                    console.log(res.data);
                    console.log(localStorage.getItem("access_token"));
                    console.log(store.state.token);
                    store.commit('SET_SNACK_BAR',{
                        msg:"토큰이 재발급 되었습니다! 다시 진행해주세요.",color:'info'
                    })
                }).catch(err=>{
                    console.log(err);
                    console.log(err.response.data);
                    console.log(error.response.data);
                    store.commit('SET_SNACK_BAR',{
                        msg:"토큰이 만료되었으니 로그인을 다시 시도해주세요.",color:'info'
                    })
                    store.commit('LOGOUT');
                    router.push('/login');
                })*/
        }else
            console.log("다른에러임.");

        console.log(error.config);
        console.log(error.response.data);
    }

)

/*function requestRefreshToken(accessToken){
    let form ={
        expiredToken : accessToken
    }
    console.log("펑셩ㄴ! " +accessToken);
    return axios.request({
        url:'/api/member/new_token',
        method:'POST',
        data:form,

    })
}*/


export default instance;