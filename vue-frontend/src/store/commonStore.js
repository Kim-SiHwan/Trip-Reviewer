
const commonStore={
    state:{
        snackBar:{
            open:false,
            text:'',
            timeout:2500,
            color:'error'
        },
    },
    mutations:{
        SET_SNACK_BAR(state,payload){
            state.snackBar.open=true;
            state.snackBar.text=payload.msg
            state.snackBar.color=payload.color;

        },
    }
}
export default commonStore;