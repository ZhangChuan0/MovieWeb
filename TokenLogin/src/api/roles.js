import request from '@/utils/request'


// 按条件获取获取角色列表



export const getRolePages = (condition) => {
    return request({
        url: '/boss/role/getRolePages',
        method: 'POST',
        data:condition
    })
}

// 保存或修改role
export const saveOrUpdateRole = (data) => {
    return request({
        url: '/boss/role/saveOrUpdate',
        method: 'POST',
        data
    })
}

//删除role
export const deleteRole = (id) => {
    return request({
        url: `/boss/role/remove/${id}`,
        method: 'DELETE',
    })
}


//获取角色的菜单

export const getRoleMenus = (roleId) => {
    return request({
        url: '/boss/role/getRoleMenus/',
        method:"GET",
        params:{
            roleId:roleId
        }
    })
}
// 给role分配菜单信息

export const allocateRoleMenus = (roleId,menuList) => {
    return request({
        url: '/boss/role/allocateRoleMenus',
        method: 'POST',
        data:{
            roleId,
            menuList
        }
    })

}

// 得到Role拥有的资源列表

export const getRoleResource = (roleId)=>{
    return request({
        method:"GET",
        url: '/boss/role/getRoleMenus/',
        params:{
            roleId
        }
    })

}

//分配角色资源

export const allocateRoleResource = (roleId,resourceIdList)=>{
    return request({
        method:"POST",
        url: '/boss/role/allocateRoleResource',
        data:{
            roleId,
            resourceIdList
        }
    })

}
