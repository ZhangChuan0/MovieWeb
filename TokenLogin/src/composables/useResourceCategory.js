// 接口函数
import {getAll, saveOrEdit,deleteCategory} from "@/api/resource-category.js";
import {ElMessage, ElMessageBox} from "element-plus";
import {reactive, ref} from "vue";


// 保存资源类别信息 用数组来保存
export const allResourceCategory = ref([])


// 是否显示弹窗
export const dialogFormVisible = ref(false)


// 创建和编辑状态
export const isCreate = ref(true)
export const  msgText = ref('')



// 弹窗表单传递的数据
export const form = reactive({
    name: "",
    order:"0",
    id:null
})

//获取资源类别信息
export const getAllResourceCategory =async ()=>{
    const {data} = await getAll()
//     判断
    if (data.code === "000000"){
        allResourceCategory.value = data.data
    }else {
        ElMessage.error("获取资源失败");
        throw new Error("资源异常")
    }

}


// 提交事件
export const onSubmit = async () =>{
    // 不管处理结果成功与否都关闭窗口
    const {data} = await saveOrEdit(form).finally(()=>(dialogFormVisible.value = false))

    if (data){
        ElMessage.success(`${msgText.value}资源类型成功`)
    }else {
        ElMessage.error(`${msgText.value}资源类型失败`)
        throw  new Error("所需的参数为空")
    }
    // 再次获取数据
    await getAllResourceCategory()
}

// 接受删除的方法
export  const handleDelete = async (id)=>{
    const {data} = await deleteCategory(id)
       await ElMessageBox.confirm("确定删除吗？","删除提醒",{
           confirmButtonText:"确定", //成功后顺序执行
           cancelButtonText:"取消", // 失败被捕获
           type:"warning"
       }).catch(()=>{
           ElMessage.info("取消删除")
           return new Promise(()=>{})
       })
    if (data){
        ElMessage.success("删除成功")
    }else {
        ElMessage.error("删除失败")
        throw new Error("删除失败了")
    }
    await getAllResourceCategory()
}

