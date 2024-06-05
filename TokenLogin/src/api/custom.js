import request from "@/utils/request.js";

let qd = ({
    currentPage:Number,
    pageSize:Number,
    phone:Number,
    userID:Number,
    startCreateTime:String,
    endCreateTime:String,
})



// 每一个用户项目
let user = ({
    createTime:String,
    id:Number,
    name:String,
    password:String,
    phone:String,
    portrait:String,
    regIp:String, //注册IP
    status:"ENABLE" || "DISABLE",
    updateTime:String,

})


// 获取用户列表信息
export const getUserPages = (queryCondition) => {

    return request({
        url: '/boss/user/getUserPages',
        method: 'post',
        data: queryCondition
    })
}

// 启用用户
export const enableUser = (userId) =>{
    return request({
        method:"GET",
        url:"/boss/user/enableUser",
        params:{
            userId
        }
    })

}

// 禁用用户
export const forbidUser = (userId) =>{
    return request({
        method:"POSt",
        url:"/boss/user/forbidUser",
        data:{
            userId
        }
    })

}

// 查询指定用户Id的所有角色信息

export const getRoleWithUserPermission = (userId) =>{
    return request({
        method:"GET",
        url:"/boss/role/getRoleWithUserPermission",
        params:{
            userId
        }
    })
}

// 给指定用户分配角色
export const allocateUserRoles = (userId,roleIdList=[]) =>{
    return request({
        method:"POST",
        url:"/boss/user/allocateUserRoles",
        data:{
            userId,
            roleIdList
        }
    })
}

// 保存或修改商品
export const saveOrUpdateUser = (user) =>{
    return request({
        method:"POST",
        url:"/boss/user/saveOrEditUser",
        data:user
    })
}

//得到id对应的user信息
export const getUserById = (userId) =>{
    return request({
        method:"GET",
        url:"/boss/user/getUserById",
        params:{userId}
    })

}

//得到id对应的user信息
export const removeUserById = (userId) =>{
    return request({
        method:"DELETE",
        url:"/boss/user/removeUser",
        params:{userId}
    })

}

