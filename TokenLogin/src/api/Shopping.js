// 获取所有数据的接口
import request from "@/utils/request.js";

// 条件查询和获取所有数据
export const getAllData  = (condition)=>{
    return request({
        method:"POST",
        url:"/boss/shopping/getAllData",
        data:condition
    })

}


//     获取电影名
export const getMovieName=()=>{
    return request({
        method:'GET',
            url:"/boss/getMovieName"
    })

}

//保存or更新
export const saveOrUpdate = (product)=>{
    return request({
        method:"POST",
        url:"/boss/shopping/saveOrUpdate",
        data:product

    })

}

//根据单id查到product信息
export const getProductById = (id)=>{
    return request({
        method:"GET",
        url:"/boss/shopping/getProductById",
        params:{id}

    })
}

// 删除
export const deleteProduct = (id)=>{
    return request({
        method:"DELETE",
        url:"/boss/shopping/deleteProduct",
        params:{id}
    })
}