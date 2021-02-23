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
          console.log(state.token);
          console.log(state.authenticated);
          console.log(state.username);
          if(state.token && state.authenticated && state.username)
              return true;
          return false;
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
        async REQUEST_JOIN(context,member){
            try{
                const response = await member_api.requestJoin(member);
                console.log(response.data);
                console.log(context);
                return response;
            }catch (e) {
                console.log("실패했습니다.")
                console.log(e.response.data.message);
            }
        },
        async REQUEST_LOGIN(context,payload){
            try{
                const response = await member_api.requestLogin(payload);
                context.commit('LOGIN',response.data);

                const areaResponse = await area_api.getAreas();
                context.commit('SET_AREAS',areaResponse.data);
                context.commit('SET_SNACK_BAR',{
                    msg:response.data.username+'님 반갑습니다.',color:'success'
                });

            }catch (e) {
                context.commit('SET_SNACK_BAR',{
                    msg:'아이디 혹은 비밀번호를 확인해주세요.',color:'error'
                });
            }
        },
        async REQUEST_LOGOUT(context,payload){
            await member_api.requestLogout(payload);
            context.commit('LOGOUT');
            context.commit('SET_SNACK_BAR',{
                msg:'로그아웃 되었습니다.',color:'success'
            });

        },
    }

}

export default memberStore