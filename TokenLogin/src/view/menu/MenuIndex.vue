<script setup>
import {useRouter} from "vue-router";
import {useMenus} from "@/composables/useMenus.js";
// 保存后台信息

const {getAllMenus,allMenus,handleDelete}  = useMenus()


const router = useRouter()


getAllMenus();


</script>

<template>
<h1>菜单列表</h1>
  <el-card class="box-card">
    <template #header>
      <div class="card-header">
        <el-button type="primary" @click="router.push({name:'menus-create'})">添加菜单</el-button>
      </div>
    </template>
    <el-table :data="allMenus" border style="width: 100%">
      <el-table-column type="index" label="序号" align="center"/>
      <el-table-column prop="name" label="菜单名称" width="180" align="center"/>
      <el-table-column prop="size" label="菜单层级" align="center"/>
      <el-table-column prop="icon" label="菜单图标" align="center"/>
      <el-table-column prop="order" label="排序" align="center"/>
      <el-table-column label="操作" align="center" v-slot="scope">
        <el-button type="primary" @click="router.push({name:'menus-edit',params:{id:scope.row.id}})">编辑</el-button>
        <el-button type="danger" @click="handleDelete(scope.row.id)">删除</el-button>
      </el-table-column>
    </el-table>
  </el-card>



</template>

<style scoped lang="scss">

.card-header{
  display: flex;
  justify-content: space-between;
  align-items:center ;
}

.item{
  margin-bottom:18px ;
}
.box-card{
  width: auto;
}


</style>