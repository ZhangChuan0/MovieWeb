<script setup>
import {useMenus} from "@/composables/useMenus.js";
import {useRoute}  from "vue-router";

const route = useRoute()  //跳转到当前页时所有的路由信息
// 保存后台信息
const {getAllMenus,topMenus,form,onSubmit,getMenuInfoById,msgText}  = useMenus()

// 将页面的选中的编辑按钮的值传递，完成功能
getMenuInfoById(route.params.id)
</script>

<template>
  <el-form :model="form" label-width="auto" size="large">

    <el-form-item label="菜单名称">
      <el-input v-model="form.name" />
    </el-form-item>

    <el-form-item label="菜单层级">
     <el-input v-model="form.size" />
    </el-form-item>

    <el-form-item label="菜单图标">
      <el-input v-model="form.icon" />
    </el-form-item>

    <el-form-item label="排序">
      <el-input v-model="form.order" />
    </el-form-item>

    <el-form-item label="显示">
      <el-radio-group v-model="form.shown">
        <el-radio :value="true">是</el-radio>
        <el-radio :value="false">否</el-radio>
      </el-radio-group>

    </el-form-item>

    <el-form-item>
      <el-button type="primary" @click="onSubmit">立即{{ msgText }}</el-button>
    </el-form-item>
  </el-form>
</template>

<style scoped lang="scss">

.el-form{
  background-color: #cee3f3;
  padding: 20px;
  width: auto;
  border-radius:10px ;
  .el-form-item{
    border-radius: 10px;
  }
}

</style>