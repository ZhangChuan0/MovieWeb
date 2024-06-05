//获取用户列表
import {getUserPages} from "@/api/custom.js";
import {ref} from "vue";
import {shoppingMethod,Resultshopping} from "@/composables/useShopping.js"
// 条件
export const queryCondition = ref({
    updateTime:"",
    createTime:""
})

// 动作
export const queryUsers = async (param={}) => {

    Object.assign(queryCondition.value, param) //查询条件，合并参数
    const {data} = await getUserPages(queryCondition.value);

    if (data.code === "000000"){
        queriedResult.value = data.data
    }

}

// 结果
export const queriedResult = ref({})


// 缺货通知
export const lockList = ref(0)


// 获取食品

// 周边
export const getToolList =async ()=>{
    await shoppingMethod()
}







