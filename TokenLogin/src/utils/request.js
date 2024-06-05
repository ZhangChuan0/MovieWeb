// 安装axios 准备接口

import axios from "axios";
const request = axios.create({
    baseURL:import.meta.env.VITE_API_URL,  //改为全部变量
})

// 利用请求拦截
/*
request.interceptors.request.use((config)=>{
    if (!config.headers){
        config.headers = config.headers ?? {};
    }
})
*/

export default request