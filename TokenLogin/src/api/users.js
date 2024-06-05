import request from "../utils/request.js";
import {ref} from "vue";


//返回类型
/*
const loginResult = {
    success:Boolean,
    state:Number,
    message:String,
    content:String
}
*/

export const login = (loginInfo)=> {
    return request({
        method:"POST",
        url:"/signfor",
        data:loginInfo
})
}

/*
* 请求类型为application/x-www.from-urlcoded时，需要
*数据拼接成：属性1=值1&属性2=值2
*
* 请求类型为application/json时
* 可以直接传
*
* */


// 拿到用户的信息
export const getInfo= (param) => {
    return request({
        method:"POST",
        url:"/front/user/getinfo",
        data:param
    })

}



//用户退出
export const logout = (token)=>{
    console.log("当前的token是：",token)

    return request({
        method:"POST",
        url:"/front/user/logout",
        data:token
    })
}
//拿到员工的
export const getUserInfo = (token)=>{}




