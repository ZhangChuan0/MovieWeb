import request from "@/utils/request.js";

// 资源类别
export const getAll = () => {
    return request({
        method:"GET",
        url:"/boss/resource/category/getAll",
    })

}


//保存或更新资源类别

export const saveOrEdit = (ResourceCategoryInfo)=>{
    return request({
        method:"POST",
        url:"/boss/resource/category/saveOrEdit",
        data:ResourceCategoryInfo
    })

}

//删除资源类别

export const deleteCategory = (id)=>{
    return request({
        method:"DELETE",
        url:`/boss/resource/category/${id}`
    })


}