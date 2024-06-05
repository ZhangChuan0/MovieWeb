<script setup>

import { ref } from 'vue'
import {ElMessage, ElMessageBox} from 'element-plus'
import {deleteStaff} from "@/api/manager.js"
import {useRouter} from "vue-router";

const dialogVisible = ref(false)
const router = useRouter()

const props = defineProps({
  managerId:{
    type:String,
    default:''
  }
})


console.log("",props.managerId)
const handleClose = async ()=>{
  await ElMessageBox.confirm('真的确认删除吗?', {

    cancelButtonText: '取消',
    confirmButtonText: '确认',
    type: 'warning'
  }).catch(()=>{
   ElMessage.info("已取消")
  })

  const {data} = await deleteStaff(props.managerId)
  if (data.code === "000000"){
    ElMessage.success("删除成功")

  }

  await router.push({name:"manager"})

  dialogVisible.value = false
}


const initAndShow = () =>{
  dialogVisible.value = true

}

defineExpose({
  initAndShow
})


</script>

<template>
  <el-dialog
      v-model="dialogVisible"
      title="二次确认"
      width="500"
  >
    <span>你正在删除 员工信息<br></span>
    <span>打印员工的个人信息生成辞退报告</span><br>
    <span>员工编号为：{{props.managerId}}</span>
    <template #footer>
      <div class="dialog-footer">
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="danger" @click="handleClose">
          确定
        </el-button>
      </div>
    </template>
  </el-dialog>

</template>

<style scoped lang="scss">

</style>