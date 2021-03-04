import Vue from "vue";
import Vuex from "vuex";
import commonStore from "@/store/commonStore";
import memberStore from "@/store/memberStore"
import reviewStore from "@/store/reviewStore";
import albumStore from "@/store/albumStore";
import areaStore from "@/store/areaStore";
import commentStore from "@/store/commentStore";
import adminStore from "@/store/adminStore"
import createPersistedState from "vuex-persistedstate"
Vue.use(Vuex);

export const store = new Vuex.Store({
    // ...
    modules:{
        commonStore,
        memberStore,
        reviewStore,
        albumStore,
        areaStore,
        commentStore,
        adminStore
    },
    plugins:[
        createPersistedState({
            paths: ["memberStore","reviewStore","albumStore","commentStore","areaStore"]

        })
    ]

});
export default store;