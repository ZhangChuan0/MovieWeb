import {defineStore} from "pinia";
import {ref,computed} from "vue";
import {ElMessage} from "element-plus";
export const useToken = defineStore("mytoken",()=>{
    // ref相当于state
    const tokenJson = ref("")
    // computed 相当于
    const token = computed(()=>{
        try {
            // return JSON.stringify(tokenJson.value || "{ }")  //将原始的信息转换成token信息 防止报错，空字符串报错

            return tokenJson.value || "{}"
        }catch (err){
            ElMessage.error("JSON转换失败")
            console.log("Json",err)
        }

    })

    // function相当于是action

    const saveToken = (data)=>{
        tokenJson.value = data
        console.log("token信息",tokenJson.value)
    }

    return{
        tokenJson,
        token,
        saveToken
    }

},{

    persist:false,

})


