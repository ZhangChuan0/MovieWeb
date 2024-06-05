import {createRouter,createWebHistory} from "vue-router";
import Home from "../view/layout/Home.vue"
import ShowData from "../view/showData.vue";
import {useToken} from "@/stores/mytoken.js";
import {useRoute} from "vue-router";
const route = useRoute()
const router = createRouter({
    history:createWebHistory("/"),
    routes:[
        {
            path:"/home",
            name:"Home",
            // 添加登录验证,父路由有了子路由也就有了
            meta:{requiresAuth:true},
            component:Home,
            children:[
                {
                    path:"user",
                    name:"user",
                    component: ()=>import("@/view/layout/UserOne.vue")
                }
            ]
        },
        {
            path:"/login",
            name:"login",
            component: ()=>import("@/view/login/LoginView.vue")
        },
        {
            path:"/",
            name:"information",
            component:ShowData,
        },
    //     设置路由懒加载，有且当路由到此地址时，才导入文件
        {
            path:"/about",
            name:"about",
            component:()=> import("../view/About.vue"),
        },
    //     404错误路由
        {
            path:"/:not(.*)*",  //not是自定义名称通配符
            name:"ErrorPage",
            component:()=>import("@/view/ErrorPage.vue")
        },
    //     index
        {
            path:"/index",
            name:"index",
            meta:{requiresAuth:true,title:"默认页"},
            component:()=>import("@/view/layout/AppLayout.vue"),
            children:[
                // 内嵌菜单页面
                {
                    path: "menus",
                    name: "menus",
                    meta:{title:"菜单"},
                    component: ()=>import("@/view/menu/MenuIndex.vue")
                },
                {
                    path:"create",
                    name:"menus-create",
                    meta:{title:"创建菜单"},
                    component:()=>import("@/view/menu/CreateOrEdit.vue")
                },
                {
                    path: "/:id/edit",
                    name:"menus-edit",
                    meta:{title:"编辑菜单"},
                    component:()=>import("@/view/menu/CreateOrEdit.vue")
                },
                {
                    path: "resource-category",
                    name:"resource-category",
                    meta:{title:"资源分类"},
                    component:()=>import("@/view/resource-category/ResourceCategoryindex.vue")
                },{
                    path:"resource",
                    name:"resource",
                    meta:{title:"资源列表"},
                    component:()=>import("@/view/resources/ResourcesIndex.vue")
                },
                {
                    path: "roles",
                    name: "roles",
                    meta:{title:"角色列表"},
                    component: ()=>import("@/view/roles/RolesIndex.vue")
                },
                {
                    path: "/roles/:roleId/alloc-menus",
                    name: "alloc-menus",
                    meta:{title:"分配菜单"},
                    component: ()=>import("@/view/roles/AllocMenus.vue"),
                    props:true  //将路由中的使用参数的方式获取

                },
                {
                    path:"/roles/:roleId/alloc-resource",
                    name:"alloc-resource",
                    meta:{title:"分配资源"},
                    component:()=>import("@/view/roles/AllocResource.vue"),
                    props:true
                },
                //     users大界面
                {
                    path:"/users",
                    name:'users',
                    meta:{title:"商品列表"},
                    component:()=>import("@/view/users/UsersIndex.vue")
                },
                {
                    path:"/users/createOrEdit",
                    name:"users-createOrEdit",
                    meta:{title:"商品增加"},
                    component:()=>import("@/view/users/CreateOrEditUser.vue")
                },{
                    path: "/users/:userId/edit",
                    name:"users-edit",
                    meta:{title:"商品编辑"},
                    component:()=>import("@/view/users/CreateOrEditUser.vue"),
                    props:true
                },
                // 课程列表展示
                {
                    path: "/courses",
                    name: "courses",
                    meta:{title:"电影"},
                    component:()=>import("@/view/courses/CoursesIndex.vue")
                },
            //     创建课程页面
                {
                    path: "/course/create",
                    name: "course-create",
                    meta:{title:"创建电影"},
                    component:()=>import("@/view/courses/CreateOrEdit.vue")
                }
            //     修改课程页面
                ,{
                    path:"/course/:courseId/edit",
                    name:"course-edit",
                    meta:{title:"更新电影"},
                    component:()=>import("@/view/courses/CreateOrEdit.vue"),
                    props:true

                }

                // 内容管理界面
                ,{
                    path: "course/:courseId/content",
                    name:"content",
                    meta:{title:"内容页"},
                    component:()=>import("@/view/courses/CourseContent.vue"),
                    props:true
                },
                {
                    path:"/manager",
                    name:"manager",
                    meta:{title:"员工"},
                    component:()=>import("@/view/manager/ManagerIndex.vue")
                },
                {
                    path:"/manager/create",
                    name:"manager-create",
                    component:()=>import("@/view/manager/CreateOrEditManager.vue"),
                    meta:{title:"创建新员工"}
                },{
                    path:"/manager/:managerId/edit",
                    name:"manager-edit",
                    meta:{title: "更新员工"},
                    component:()=>import("@/view/manager/CreateOrEditManager.vue"),
                    props:true
                },
                {
                    path:"/shopping",
                    name:"shopping",
                    component:()=>import("@/view/shopping/Shopping.vue"),
                    meta:{title:"商品"}
                },
                {
                    path: "/setting",
                    name:"set",
                    meta:{title: "设置"},
                    component:()=>import("@/view/set/SetPage.vue")
                },
                {
                    path:"/setting/:until/set",
                    name:"set-edit",
                    meta:{title:"设置"},
                    component:()=>import("@/view/set/EditOrCreateSetting.vue"),
                    props:true
                },{
                    path: "/members",
                    name:"members",
                    meta:{title:"项目"},
                    component:()=>import("@/view/member/Members.vue")
                },{
                    path:"/members/:id/operation",
                    name: "members-operation",
                    meta:{title:"子项"},
                    component:()=>import("@/view/member/CreateOrUpdate.vue"),
                    props:true
                },
                {
                    path: "/sales",
                    name: "sales",
                    meta: {title: "销售"},
                    component: () => import("@/view/sales/Composition.vue"),
                    children:[{
                        path: "eat",
                        name: "eat",
                        meta: {title: "食物"},
                        component: () => import("@/view/sales/Sales.vue")
                    },
                    {
                     path:"movie",
                     name:"movie",
                     meta:{title:"票务"},
                     component:()=>import("@/view/sales/Movie.vue")
                    }
                    ]
                },
                {
                    path: "/sales/data",
                    name:"sales-data",
                    meta:{title:"数据"},
                    component:()=>import("@/view/util/ShowSalesData.vue")
                },{
                    path:"/query",
                    name: "query",
                    meta:{title:"查询"},
                    component:()=>import("@/view/query/Query.vue")
                }

            ]
        }

    ]
})


// 回调函数

router.beforeEach((to, from, next)=>{
    const store = useToken()
    if (to.matched.some((r)=>r.meta.requiresAuth)){
        if (!store.token.access_token){  //判断有没有token
                next({name: "login",query:{redirect:to.fullPath}})
        }
    }
    next() //默认按照输入的跳转，也可以自定义
})

export default router