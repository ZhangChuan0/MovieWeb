<script setup>

import {queryRoles,queriedResult,queryCondition,deleteRoleLine} from "@/composables/useRoles.js";
import DialogRoleCreateOrEdit from "@/view/roles/DialogRoleCreateOrEdit.vue";
import {useRouter} from "vue-router"
const router = useRouter()
queryRoles()

const dialogRoleCreateOrEdit = ref()
// 搜索
</script>

<template>
  <el-card >
    <template #header>
      <el-button type="primary" @click="dialogRoleCreateOrEdit.initAndShow()">新增角色</el-button>
        <el-form :inline="true" :model="queryCondition" class="demo-form-inline">

          <el-form-item label="请输入姓名：">
            <el-input v-model="queryCondition.name" placeholder="输入名称" clearable />
          </el-form-item>

          <el-form-item>
            <el-button type="primary" @click="queryRoles(queriedResult.name)">搜索</el-button>
            <el-button type="info" @click="queryCondition.name = ''">重置</el-button>
          </el-form-item>
        </el-form>
    </template>
  <el-scrollbar height="500px">
    <el-table :data="queriedResult.records" border style="width: 100%">
      <el-table-column type="index" label="序号" width="auto" align="center"/>
      <el-table-column prop="name" label="姓名" width="auto" align="center"/>
      <el-table-column prop="description" label="描述" width="auto" align="center"/>
      <el-table-column prop="createTime" label="创建时间" width="auto" align="center"/>
      <el-table-column label="操作" width="auto" align="center" v-slot="{row}">
        <el-button type="primary" link @click="router.push({name:'alloc-menus',params:{roleId:row.id}})">分配菜单</el-button>
        <el-button type="primary" link @click="router.push({name:'alloc-resource',params:{roleId:row.id}})">分配资源</el-button>
        <el-button type="primary" @click="dialogRoleCreateOrEdit.initAndShow(row)">编辑</el-button>
        <el-button type="danger" @click="deleteRoleLine(row.id)">删除</el-button>
      </el-table-column>
    </el-table>
  </el-scrollbar>

    <template #footer>
      <el-pagination
          v-model:current-page="queriedResult.current"
          v-model:page-size="queriedResult.hitcount"
          :page-sizes="[10,20,30,40]"
          layout="prev, pager, next,total,jumper,sizes"
          :total="queriedResult.pages"
          @size-change="(size)=>{
            queryRoles({size})

          }"
          @current-change="(page)=>{
            queryRoles({page})
            }"
      />
    </template>
  </el-card>
  <DialogRoleCreateOrEdit ref="dialogRoleCreateOrEdit"/>
</template>

<style scoped lang="scss">
.demo-form-inline{
  display: flex;
  justify-content: space-around;
  float: right;
}

.el-table{
  margin-top: 20px;
}
</style>