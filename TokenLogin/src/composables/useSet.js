import {ref} from "vue"
import {getLanguage, getPosition, getRoom} from "@/api/set.js";

// 语言的结果
export const resultLanguage = ref([])


//语言的方法
export const getLanguageList = async () => {
    const {data} = await getLanguage()
    if (data.code === "000000"){
        resultLanguage.value = data.records
    }

}

//影厅的结果
export const resultRoom = ref([])

//影厅的方法
export const getRoomList = async ()=>{
    const {data} = await getRoom()
    if (data.code === "000000"){

        resultRoom.value = data.records
    }

}


//职员表的结果
export const resultPosition = ref([])

//职员表的方法
export const getPositionList = async ()=>{
    const {data} = await getPosition()
    if (data.code === "000000"){
        resultPosition.value = data.records
    }

}
