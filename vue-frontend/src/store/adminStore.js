import admin_api from "@/apis/admin_api";

const adminStore={
    state:{
        memberList:[],
        tagList:[]
    },
    mutations:{
        SET_MEMBER_LIST(state,payload){
            state.memberList = payload;
        },
        SET_TAG_LIST(state,payload){
            state.tagList = payload;
        }

    },
    actions:{
        async REQUEST_GET_ALL_MEMBERS(context){
            const response = await admin_api.getMembers();
            if(response){
                context.commit('SET_MEMBER_LIST',response.data);
            }
        },
        async REQUEST_GET_ALL_TAGS(context){
            const response = await admin_api.getTags();
            if(response){
                context.commit('SET_TAG_LIST',response.data);
            }
        },
        async REQUEST_DELETE_TAG(context,payload){
            const response = await admin_api.deleteTag(payload);
            if(response){
                context.commit('SET_SNACK_BAR',{
                    msg:'태그 삭제 완료',color:'success'
                })
            }
        }

    }

}

export default adminStore;