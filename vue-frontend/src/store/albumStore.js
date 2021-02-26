import album_api from "@/apis/album_api";

const albumStore={
    state:{
        fileInfo:''
    },
    mutations:{
        SET_FILE_INFO(state, payload){
            state.fileInfo=payload;
        }
    },
    actions:{
        async REQUEST_GET_FILES(context,payload){
            const response = await album_api.getFiles(payload);
            if(response){
                console.log(response.data);
                context.commit('SET_FILE_INFO',response.data);
            }

        },
        async REQUEST_UPLOAD_FILES(context,payload){
            const addResponse = await album_api.uploadFiles(payload);
            if(addResponse){
                context.commit('SET_SNACK_BAR',{
                    msg:'사진첩에 사진이 등록되었습니다.',color:'success'
                });
                const getResponse = await album_api.getFiles(payload.get('areaId'));
                context.commit('SET_FILE_INFO',getResponse.data);
            }

        },
        async REQUEST_UPDATE_ALBUM(context,payload){
            const updateResponse = await album_api.deleteFiles(payload);
            if(updateResponse){
                context.commit('SET_SNACK_BAR',{
                    msg:'선택된 사진이 삭제되었습니다.',color:'success'
                });
                const getResponse = await album_api.getFiles(payload.areaId);
                context.commit('SET_FILE_INFO',getResponse.data);
            }
            try{
                await album_api.deleteFiles(payload);

            }catch (e) {
                console.log("앨범 수정 실패");
                context.commit('SET_SNACK_BAR',{
                    msg:'앨범 수정을 실패했습니다.',color:'error'
                });
            }
        }

    }
}
export default albumStore;