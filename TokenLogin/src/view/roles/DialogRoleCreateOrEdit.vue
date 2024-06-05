<script setup>
import { reactive, ref } from 'vue'
import {queryRoles,queriedResult} from "@/composables/useRoles.js";
import {saveOrUpdateRole} from "@/api/roles.js";
import {ElMessage} from "element-plus";

const dialogFormVisible = ref(false)
const formLabelWidth = '140px'
// 表单数据

const initData = ()=>({
  name: '',
  description: '',
})

const form = reactive(initData())
const msg = ref("")
const initAndShow = (row) =>{
  dialogFormVisible.value = true
  Object.assign(form,initData())
  if (row?.id){
    msg.value = "更新"
    console.log("表单数据",form)
    Object.assign(form,row)
  }else {
    msg.value = "新建"
  }

}


// 导出初始化组件给父类使用

defineExpose({
  initAndShow,
})


// 更新和新建
const submit = async ()=>{

  console.log("已经写好的表单数据",form)
  const {data} = await saveOrUpdateRole(form).finally(()=>(dialogFormVisible.value = false))
  if (data){
    await queryRoles()
  }else {
    ElMessage.error(msg.value+'失败')
    throw new Error("新建失败")
  }
}

// 表单验证
const rules = reactive({
  name: [
    { required: true, message: '请输入姓名', trigger: 'blur' },
    { min: 2, max: 5, message: '长度在 2 到 5 个字符', trigger: 'blur' }
  ],
  description: [
    { required: true, message: '请选择地区', trigger: 'change' }
  ],
})

</script>

<template>
  <el-dialog v-model="dialogFormVisible" :title="msg+'角色'" width="500">
    <el-form :model="form" :rules="rules">
      <el-form-item label="姓名" :label-width="formLabelWidth" prop="name">
        <el-input v-model="form.name" autocomplete="off" />
      </el-form-item>
      <el-form-item label="描述" :label-width="formLabelWidth" prop="description">
        <el-select v-model="form.description" placeholder="选择地区">
          <el-option label="不限" value="" />
          <el-option v-for="role in queriedResult.records" :key="role.id" :label="role.description" :value="role.description" />

        </el-select>
      </el-form-item>
    </el-form>
    <template #footer>
      <div class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取消</el-button>
        <el-button type="primary" @click="submit()">
          提交
        </el-button>
      </div>
    </template>
  </el-dialog>
</template>

<style scoped lang="scss">

</style>