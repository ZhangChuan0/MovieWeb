import request from "@/utils/request.js"

//获取所有菜单项目
export const getAll = () => {
    return request({
        method: "GET",
        // url:"/data/book"
        url: "data/boss/menu/getALL"
    })

}
// 保存或更新菜单--重复创建

export const saveOrUpdate = (menuInfo)=>{
    return request({
        method:"POST",
        url:"/data/boss/menu/saveOrUpdate",
        data:menuInfo
    })
}


// 删除菜单

export const deleteMenu = (id) => {
    return request({
        method:"DELETE",
        url:`/data/boss/menu/${id}`,

    })



}


// 通过指定ID获取信息--重复提交而不是修改

export const getEditMenuInfo =(id)=>{
    return request({
        method:"GET",
        url:"/data/boss/menu/getEditMenuInfo",
        // query形式  即通过？ 传递
        params:{
            id:id
        }


    })

}
