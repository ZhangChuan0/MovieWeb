import request from "@/utils/request.js";


//搜索和得到一些信息,所有的职位表信息
export const getStaffInfo = (data)=>{
    return request({
        method:"POST",
        url:"/manager/saveOrEdit",
        data:data
    })


}


// 改变用户的登录管理员权限
export const changeLogin = (data)=>{
    return request({
        method:"POST",
        url:"/manager/permission/changeLogin",
        data:data

    })
}


// 保存或更新manager信息

export const saveOrEditManager = (data)=>{
    return request({
        method:"POST",
        url:"/manager/saveOrUpdateStaff",
        data: {
            ...data
        }
    })
}

export const getManagerById = (managerId)=>{
    return request({
        method:"GET",
        url:"/manager/getManagerById",
        params: {
            managerId
        }
    })


}

//  删除员工
export const deleteStaff = (id)=>{
    return request({
        method:"DELETE",
        url:"/manager/deleteStaff",
        params:{
            id
        }

    })
}







