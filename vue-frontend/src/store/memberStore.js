import member_api from "@/apis/member_api";
import auth from "@/util/auth"
import area_api from "@/apis/area_api";
const memberStore={
    state:{
        token:localStorage.getItem('access_token'),
        authenticated:false,
        username:'',
    },
    getters:{
      isAuthenticated(state){
          return !!(state.token && state.authenticated && state.username);

      }
    },
    mutations:{
        LOGIN(state,payload){
            console.log(payload);
            auth.setStorage(payload);
            state.token=localStorage.getItem('access_token');
            state.username=localStorage.getItem('username')
            state.authenticated=true;
            console.log("SET!")
            console.log(state.username);
        },
        LOGOUT(state){
            auth.initStorage();
            state.token="";
            state.username="";
            state.authenticated=false;
        },

    },
    actions:{
        async REQUEST_JOIN(context,payload){
            const joinResponse = await member_api.requestJoin(payload);
            if(joinResponse){
                context.commit('SET_SNACK_BAR',{
                    msg:payload.username+'으로 정상 가입되었습니다.',color:'success'
                });
            }
        },
        async REQUEST_LOGIN(context,payload){
            const loginResponse = await member_api.requestLogin(payload);
            if(loginResponse){
                context.commit('LOGIN',loginResponse.data);
                context.commit('SET_SNACK_BAR',{
                    msg:loginResponse.data.username+'님 반갑습니다.',color:'success'
                });
                const areaResponse = await area_api.getAreas();
                context.commit('SET_AREAS',areaResponse.data);

            }

        },
        async REQUEST_LOGOUT(context){
            const logoutResponse = await member_api.requestLogout();
            if(logoutResponse) {
                context.commit('LOGOUT');
                context.commit('SET_SNACK_BAR', {
                    msg: '로그아웃 되었습니다.', color: 'success'
                });
            }

        },
    }

}

export default memberStore