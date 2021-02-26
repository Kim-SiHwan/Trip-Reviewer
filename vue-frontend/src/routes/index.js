import Vue from 'vue';
import Router from 'vue-router';
import Map from "@/components/map/Map";
import Login from "@/components/member/Login";
import Join from "@/components/member/Join";
import Album from "@/components/map/Album";
import UploadReview from "@/components/review/UploadReview";
import ReviewList from "@/components/review/ReviewList";
import ReviewDetail from "@/components/review/ReviewDetail";
import MyPage from "@/components/member/MyPage";
import Main from "@/components/common/Main";
import ErrorPage from "@/components/common/ErrorPage";
import AreaDetail from "@/components/map/AreaDetail";

Vue.use(Router); //vue 라우터 사용

export default new Router({ //라우터 연결
    routes:[
        {
            path:'/',
            component: Main
        },
        {
            path:'/main'
            ,component:Map

        }
        ,{
            path:'/detail',
            component:AreaDetail,
        },
        {
            path:'/login',
            component: Login
        },
        {
            path:'/join',
            component:Join
        },
        {
            path:'/my',
            component: MyPage
        },
        {
            path:'/album',
            component: Album
        },
        {
            path:'/uploadReview',
            component: UploadReview
        },
        {
            path:'/reviewList',
            component: ReviewList
        },
        {
            path:'/reviewDetail',
            component: ReviewDetail
        },
        {
            path:'/error',
            component: ErrorPage
        }

    ]
})