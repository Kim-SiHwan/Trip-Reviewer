import axios from "axios";
import store from "@/store/store";
import router from "@/routes/index"
const instance = axios.create();

instance.interceptors.request.use(
    async config=>{
        config.headers={'Authorization' : localStorage.getItem("access_token")}
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
        if(error.response.status===401){
            console.log(error.response);
            console.log("토큰이만료되었음.");
            await requestRefreshToken(localStorage.getItem('access_token'))
                .then(res=>{
                    console.log(res.data);
                    //현재 리프레시 만료되었을 때 한번에 처리를 못함. 아래 캐치에서 500에러뜰 때 진행되니까
                    //msg를 검사하던, 컨트롤러에서 토큰이 만료되었을 때 에러를 던지던 깔끔하게 처리를 합시다.
                    localStorage.setItem('access_token','Bearer '+res.data.refreshToken)
                    store.commit('SET_SNACK_BAR',{
                        msg:"토큰이 재발급 되었습니다! 다시 진행해주세요.",color:'info'
                    })
                }).catch(()=>{
                    store.commit('SET_SNACK_BAR',{
                        msg:"토큰이 만료되었으니 로그인을 다시 시도해주세요.",color:'info'
                    })
                    store.commit('LOGOUT');
                    router.push('/login');
                })
        }else
            console.log("다른에러임.");

        console.log(error.config);
        console.log(error.response.data);
    }

)

function requestRefreshToken(accessToken){
    let form ={
        jwt : accessToken
    }
    console.log("펑셩ㄴ! " +accessToken);
    return axios.request({
        url:'/api/new_token',
        method:'POST',
        data:form,

    })
}


export default instance;