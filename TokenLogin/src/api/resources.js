import request from "@/utils/request.js"



// 传递给后端的参数
let condition = {
    id:Number,
    name:String,
    startCreateTime:String,
    url:String,
    category:String,
    endCreateTime:String,
    current:Number,
    size:Number
}


//按条件分页查找资源
export const getResourcePage = (condition)=>{
    return request({
        method:"POST",
        url:"/boss/resource/getResourcePages",
        data:condition
    })


}

// 保存和修改资源
export const saveOrUpdate = (resource)=>{
    return request({
        method:"POST",
        url:"/boss/resource/saveOrUpdate",
        data:resource

    })

}

// 删除指定一行
export const deleteById = (id)=>{
    return request({
        method:"DELETE",
        url:`/boss/resource/${id}`
    })

}
