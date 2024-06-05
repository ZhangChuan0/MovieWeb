<script setup>
import {queriedResult,queryResources,} from "@/composables/useResources.js";
import {ref,reactive} from "vue";
import {saveOrUpdate} from "@/api/resources.js";

const dialogVisible = ref(false);
// 表单数据
const form =reactive({
  name:"",
  url:"",
  description:""

})



const  isCreate = ref(true)
const msg = ref("")
const resetFom = ref({
  id:"",
  name:"",
  url:"",
  description:""
})
queryResources()
 const initAndShow =   (row)=>{
  dialogVisible.value = true
  if (row?.id){
    isCreate.value = false
    msg.value = "更新"
    console.log(row)
    Object.assign(form,row)
  }else {
    console.log("编辑过后的菜单",form)
    isCreate.value = true
    msg.value = "创建"
  }

}

defineExpose({
  initAndShow,
})

const submit =async ()=>{

  const {data}  = await saveOrUpdate(form).finally(()=>{dialogVisible.value =false})
  if (data) {

    queryResources()
  }

  resetFom.value?.resetFields() //打开就清空内容
  form.id = ""
  form.description=""
  form.createdTime = ""
}
</script>

<template>
  <el-dialog
      v-model="dialogVisible"
      :title="msg+'资源'"
      width="500"
      >
    <el-form :model="form" label-width="auto" style="max-width: 600px" ref="resetFom">
      <el-form-item label="资源名称" prop="name" :rules="[{ required: true, message: '请输入资源名称', trigger: 'blur'  }]">
        <el-input v-model="form.name" />
      </el-form-item>

      <el-form-item label="资源路径" prop="url" :rules="[{ required: true, message: '请输入资源路径', trigger: 'blur' }]">
        <el-input v-model="form.url" />
      </el-form-item>
      <el-form-item label="选择描述" prop="description">
        <el-select v-model="form.description" placeholder="请选择" :rules="[{ required: true, message: '请选择描述', trigger: 'change'}]">
          <el-option v-for="resouce in queriedResult.records" :key="resouce.id" :label="resouce.description" :value="resouce.description"/>

        </el-select>

      </el-form-item>
    </el-form>
    <template #footer>
      <div class="dialog-footer">
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="submit">
          提交
        </el-button>
      </div>
    </template>
  </el-dialog>
</template>

<style scoped lang="scss">

</style>