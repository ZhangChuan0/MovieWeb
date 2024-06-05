import {getResourcePage} from "@/api/resources.js";
import {ref,reactive} from "vue";
import {ElMessage} from "element-plus";
//查询条件
export const queryCondition = reactive({
    id:0,
    name:"",
    url:"",
    category:"",
    current:1,
    size:5
})
//查询结果
export const queriedResult = ref({})


//查询方法
export const queryResources = async (params={})=>{
    Object.assign(queryCondition,params) //合并查询条件 将params的值赋给queryCondition
    console.log(queryCondition)
    const {data} = await getResourcePage(queryCondition)
    if (data.code === "000000"){
        queriedResult.value = data.data
    }else {
        ElMessage.error("获取资源列表失败")
        throw new Error("获取资源列表失败")
    }



}