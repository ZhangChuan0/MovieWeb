import {shoppingMethod,Resultshopping} from "@/composables/useShopping.js"
import {queriedResult,queryUsers} from "@/composables/useUser.js";
import { ref } from 'vue'
import {getOrderAllInfo} from "@/api/sales.js"

// 食物
export const foodList = ref([])
export const getFood=async ()=>{
    await queryUsers()
    foodList.value = queriedResult.value.records.filter(item => item.status === 'ENABLE' & parseInt(item.password) > 5)

}

// 周边
export const toolsList = ref([])
export const getTools=async ()=>{
    await shoppingMethod()
    toolsList.value = Resultshopping.value.filter(item => item.status === 'ENABLE'  & item.stock > 5)

}


// 得到订单的所有信息，解析出其中的电影列表
export const  orderList = ref([])

export const orderMethod = async ()=>{
    const {data} = await getOrderAllInfo();
    orderList.value = data.records.filter(item=>item.item_type === "movie")
    console.log("info",orderList.value)

}



