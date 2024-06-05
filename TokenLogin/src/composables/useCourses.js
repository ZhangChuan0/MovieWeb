import {getQueryCourses} from "@/api/courses.js";
import {ref} from "vue";
import {ElMessage} from "element-plus";

//查询条件
export const queryCondition= ref({
    status:"",
    name:""
})


//查询结果
export const queryResult= ref({})

//查询方法
export const queryCourses= async (param={})=>{
    Object.assign(queryCondition.value,param)
    const {data} = await getQueryCourses(queryCondition.value)
    console.log("新查询",queryCondition.value)
    if (data.code === "000000"){
        queryResult.value = data.data
    }

}