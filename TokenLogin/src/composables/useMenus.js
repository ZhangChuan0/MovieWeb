
import {getAll,saveOrUpdate,deleteMenu,getEditMenuInfo} from "@/api/menus.js";
import {ElMessage, ElMessageBox} from "element-plus";
    // 保存后台信息
import router from "@/router/router.js"

export function useMenus(){

    const allMenus = ref([])

    // 获取所有菜单
    const getAllMenus = async ()=> {

        const {data} = await getAll() //单独解析

        console.log(data)

        if (data.code === "000000") {

            allMenus.value = data.data
        } else {
            ElMessage.error("获取菜单内容失败")
            throw new Error("将菜单的错误抛出，而不是处理")
        }

    }
    // 获取顶级菜单
    const topMenus = computed(()=>allMenus.value.filter((menu)=>{menu.size === 0}))



    // 提交的表单数据--提交对象
    const form = ref({
        name:"",
        size:0,
        order:"",
        icon:"",
        shown:true

    })


    // 提交表单
    const onSubmit = async ()=>{

       const info = await saveOrUpdate(form.value)
        if (info.data){
            ElMessage.success(`${msgText.value}菜单成功`)
            await router.push({name: 'menus'})
        }else {
            ElMessage.error("添加失败")
            throw  new Error("处理判断问题")
        }

    }

    // 删除
    const handleDelete = async (id) =>{
        // 二次确认框
        await ElMessageBox.confirm("确定删除数据吗","删除提示",{
            confirmButtonText:"确认",  //成功
            cancelButtonText:"取消"    //失败
        }).catch(()=>{
            ElMessage.info("删除操作被取消")
            return new Promise(()=>{})
        })

    //     接口处理
       const res = await deleteMenu(id)

        if (res){
            ElMessage.success("删除成功")
            getAllMenus()
        }else {
            ElMessage.error("删除失败")

        }

    }


    // 根据Id获取form信息

    const getMenuInfoById = async (id)=>{
        // 根据Id确定状态
        if (!Number(id)){
            isCreate.value = true
            return
        }else {
            isCreate.value = false
        }

            const res = await getEditMenuInfo(id)
            if (res.data != null){
                form.value = res.data
            }else {
                ElMessage.error("获取失败")
            }

    }

    // 新增菜单时或更新菜单时状态
    const isCreate = ref(true)
    const msgText = computed(()=>(isCreate.value ? "创建":"更新"))


    // 返回
    return {getAllMenus,allMenus,topMenus,form,onSubmit,handleDelete,getMenuInfoById,msgText}
}