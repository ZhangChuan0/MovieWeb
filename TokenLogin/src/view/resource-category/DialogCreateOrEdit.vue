<script setup>
import { ref,defineExpose,toRefs } from 'vue'
import {
  onSubmit,
  form,
  isCreate,
  msgText,
  dialogFormVisible,
  allResourceCategory
} from "@/composables/useResourceCategory.js"

const fmResourceCategory = ref()

const initAndShow = (id) => {
  fmResourceCategory.value?.resetFields()  //重置表单的值回到初始
  console.log("最新",fmResourceCategory.value)
  dialogFormVisible.value = true

  if (id){
    isCreate.value = false
    msgText.value  = "更新"
    const resource = allResourceCategory.value.find((item)=>item.id === id)  //找到符合的元素
    Object.assign(form, resource) //没有的属性就添加上，有的就赋值
  }else{
    isCreate.value = true
    msgText.value= "创建"
    form.id = null  //重置id的值,因为要根据这个在后端来判断属于更改还是新建
  }
}

// 向外导出
defineExpose({
  initAndShow,
})


</script>

<template>
  <el-dialog v-model="dialogFormVisible" :title="msgText+'资源类别'" width="500">
    <el-form :model="form" ref="fmResourceCategory">
      <el-form-item label="类别名称"   prop="name">  <!--表单重置必须要接上参数prop-->
        <el-input v-model="form.name" autocomplete="off" />
      </el-form-item>
      <el-form-item label="排序"  prop="order">
        <el-input v-model="form.order" autocomplete="off" />
      </el-form-item>
    </el-form>
    <template #footer>
      <div class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取消</el-button>
        <el-button type="primary" @click="onSubmit">
          提交
        </el-button>
      </div>
    </template>
  </el-dialog>
</template>

<style scoped lang="scss">

.el-button--text{
  margin-right: 10px;
}

.el-input{
  width: 300px;

}

.dialog-footer button:first-child{
  margin-right: 10px;
}

.el-form-item{

  width: 300px;
}
</style>