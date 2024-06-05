import {ref} from "vue"
import {getAllData, getMovieName} from "@/api/Shopping.js";


//查询
export const conditionShopping = ref({})

//方法
export const shoppingMethod  = async (param)=>{

    Object.assign(conditionShopping.value,param)  //查询
    const {data} = await getAllData(conditionShopping.value)
    if (data.code === "000000"){
        Resultshopping.value = data.data.records

    }
}

//结果
export const Resultshopping = ref([]) //正确的装载
// 电影
export const Resultmovie = ref([]) //正确的装载


//电影名方法
export const getNameList= async ()=>{
    const res = await getMovieName()
     Resultmovie.value = res.data.records
}

