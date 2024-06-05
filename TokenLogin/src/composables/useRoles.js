import {getRolePages,deleteRole} from "@/api/roles.js";
import {ElMessage, ElMessageBox} from "element-plus";

//查询条件

export const queryCondition = reactive({
    name:"",
    id:"",
    description:""
})


//查询结果

export const queriedResult = ref({
    records:[],
    pages:0,
    current:0
})


//查询方法
export const queryRoles = async (param = {}) => {
    Object.assign(queryCondition, param)  //合并查询条件
   const {data} = await getRolePages(queryCondition)
    if (data.code !== "000000"){
       ElMessage.error("获取role资源失败")
        throw new Error("获取role资源失败")
    }else {
        queriedResult.value = data.data
    }

}

//删除表中数据
export const deleteRoleLine = async (id) => {
    await ElMessageBox.confirm("是否删除？","删除提示",{
        confirmButtonText:"确认",
        cancelButtonText:"取消",
        type:"warning"
    }).catch(()=>{
        ElMessage.info("取消删除")
        return new Promise(()=>{})
    })
    const {data} = await deleteRole(id)
    if (data){
        ElMessage.success("删除成功")
    }else {
        ElMessage.error("删除失败")
    }

    await queryRoles()

}

