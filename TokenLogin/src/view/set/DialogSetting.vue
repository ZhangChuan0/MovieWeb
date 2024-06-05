<script setup>
import { ref } from 'vue'
import { ElMessageBox } from 'element-plus'
import {queryById, saveSetting} from "@/api/set.js";
const dialogVisible = ref(false)
const title= ref("")
import {useRouter}  from "vue-router";
const init = ()=>({
  name:""
})
const router = useRouter()



const form = ref()

const initAndShow= async (row,data)=>{


  form.value = init()
  if (data){
    Object.assign(form.value,data)
  }
  dialogVisible.value = true
  if (row){
    title.value = "更新"
    Object.assign(form.value, {id:row})
    const {data} = await queryById(form.value)

    form.value.name =data.data[0].name
  }else {
    title.value = "新增"
  }


}



const submit=async ()=>{

    const res = await saveSetting(form.value)
    console.log("new ",res)
  dialogVisible.value = false
  await  router.push({name:"set"})

}
defineExpose({
  initAndShow,

})

</script>

<template>
  <el-dialog
      v-model="dialogVisible"
      :title="title"
      width="500"
  >
    <el-form>
      <el-form-item label="名称">
      <el-input v-model="form.name" placeholder="请输入名称" />
      </el-form-item>
    </el-form>
    <template #footer>
      <div class="dialog-footer">
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="submit()">
          提交
        </el-button>
      </div>
    </template>
  </el-dialog>

</template>

<style scoped lang="scss">

</style>