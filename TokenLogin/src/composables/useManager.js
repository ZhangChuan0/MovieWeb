import {getStaffInfo} from "@/api/manager.js";
import {ref} from "vue";
//查询结果
export const queryResult = ref({
    records:[],
    data:[]
})
//查询条件
export const queryCondition = ref({
    name:"",
    phone:"",
    gender:""
})
//查询功能
export const managerQuery = async (param) => {
    Object.assign(queryCondition.value,param)
    const {data} = await getStaffInfo(queryCondition.value)
  if (data.code === "000000"){

      queryResult.value.records = data.records
      queryResult.value.data = data.data
  }

}
