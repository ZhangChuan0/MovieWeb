import request from "@/utils/request.js";
//
export const getLanguage = ()=>{
    return request({
        method:"GET",
        url:"/setting/getLanguage",
    })

}

//得到影厅

export const getRoom = ()=>{
    return request({
        method:"GET",
        url:"/setting/getRoom",

    })

}

//职员表
export const getPosition = ()=>{
    return request({
        method:"GET",
        url:"/setting/getPosition",
    })
}

//====================保存or更新
export const saveSetting = (data)=>{
    return request({
        method:"POST",
        url:"/setting/saveSetting",
        data:data
    })

}


//查找单个
export const queryById = (data)=>{
    return request({
        method:"POST",
        url:"/setting/queryById",
        data:data
    })

}

//删除
export const deleteById = (data)=>{
    return request({
        method:"POST",
        url:"/setting/deleteById",
        data:data
    })

}