import area_api from "@/apis/area_api";

const areaStore={
    state:{
        selectedArea:'',
        selectedPath:'',
        areas:[]

    },
    mutations:{
        SET_SELECTED_AREA(state,payload){
            state.selectedArea=payload;
        },
        SET_SELECTED_PATH(state,payload){
            state.selectedPath=payload;
        },
        SET_AREAS(state,payload){
            state.areas = payload;
        }
    },
    actions:{
        async REQUEST_GET_AREA(context,payload){
            const response = await area_api.getArea(payload);
            if(response){
                context.commit('SET_SELECTED_AREA',response.data);
            }
        },
        async REQUEST_GET_PATH(context,payload){
            try{
                context.commit('SET_SELECTED_PATH',payload);
            }catch (e) {
                console.log("도형 불러오기 실패")

                context.commit('SET_SNACK_BAR',{
                    msg:'도형 불러오기를 실패했습니다.',color:'error'
                });
            }
        },

        async REQUEST_CHANGE_AREA_INFO(context,payload){
            const changeResponse = await area_api.changeAreaInfo(payload);
            if(changeResponse){
                const getAllAreasResponse = await area_api.getAreas();
                const getAreaResponse = await area_api.getArea(payload.areaId);
                context.commit('SET_AREAS',getAllAreasResponse.data);
                context.commit('SET_SELECTED_AREA',getAreaResponse.data);
            }
        },

        async REQUEST_INIT_AREA_INFO(context,payload){
            const initResponse = area_api.initAreaInfo(payload.areaId);
            if(initResponse){
                const getAllAreasResponse = await area_api.getAreas();
                const getAreaResponse = await area_api.getArea(payload.areaId);
                context.commit('SET_AREAS',getAllAreasResponse.data);
                context.commit('SET_SELECTED_AREA',getAreaResponse.data);
            }

        }
/*
        async REQUEST_CHANGE_COLOR(context,payload){
            try {
                const response = await area_api.changeColor(payload);
                context.commit('SET_AREAS',response.data);
                context.commit('SET_SELECTED_AREA',response.data[payload.areaIdx-1]);
            }catch (e) {

                context.commit('SET_SNACK_BAR',{
                    msg:'색상 변경을 실패했습니다.',color:'error'
                });
            }
        },
        async REQUEST_CHANGE_TITLE(context,payload){
            try {
                // const response = await area_api.changeTitle(payload);
                // store.commit('setArea',response.data);
                console.log(payload);
                const response = await area_api.changeTitle(payload);
                context.commit('SET_AREAS',response.data);
                context.commit('SET_SELECTED_AREA',response.data[payload.areaIdx-1]);
            }catch (e) {

                context.commit('SET_SNACK_BAR',{
                    msg:'제목 변경을 실패했습니다.',color:'error'
                });
            }
        },*/
    }
}
export default areaStore