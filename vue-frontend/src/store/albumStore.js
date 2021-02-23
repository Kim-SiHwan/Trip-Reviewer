import file_api from "@/apis/file_api";

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
            try{
                const response = await file_api.getFiles(payload);
                console.log(response.data);
                context.commit('SET_FILE_INFO',response.data);

            }catch (e) {
                console.log("사진첩 불러오기 실패")
                context.commit('SET_SNACK_BAR',{
                    msg:'사진첩 불러오기를 실패했습니다.',color:'error'
                });
            }
        },
        async REQUEST_UPLOAD_FILES(context,payload){
            try{
                await file_api.uploadFiles(payload);
            }catch (e) {
                console.log("업로드실패");
                context.commit('SET_SNACK_BAR',{
                    msg:'사진 업로드를 실패했습니다.',color:'error'
                });
            }
        },
        async REQUEST_UPDATE_ALBUM(context,payload){
            try{
                await file_api.deleteFiles(payload);

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