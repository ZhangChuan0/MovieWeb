<script setup>
import {queryResult,queryCondition,managerQuery} from "@/composables/useManager.js";
import userIcon from "@/assets/user/portrait.svg";
import {changeLogin} from "@/api/manager.js";
import { reactive } from 'vue'
import {ElMessage} from "element-plus";



managerQuery()


const formInline = reactive({
  name: '',
  position: [],
})

const onQuery = async () => {
  // console.log(formInline)
  if (formInline.name !== '' || formInline.position.length !== 0) {
  await managerQuery(formInline)
  }


}

// 切换用户状态
const handleChange = async (act,userId)=>{

const data = ({
  act:act,
  userId:userId
})

const {data:code} = await changeLogin(data)

  if (code.code === "000000"){
    ElMessage.success("更新权限成功");
  }else {
    ElMessage.error("更新权限失败");
  }

}

</script>

<template>
  <el-card style="width: auto">

    <template #header>
      <el-form :inline="true" :model="formInline" class="demo-form-inline">

        <el-form-item label="姓名" >
          <el-input v-model="formInline.name" placeholder="请输入姓名" clearable />
        </el-form-item>

        <el-form-item label="职位">
          <el-select
              v-model="formInline.position"
              placeholder="选择职位"
              clearable
              multiple
              style="width: 150px"
          >
            <el-option label="全选" value="" />
            <el-option v-for="position in queryResult.data" :key="position.id" :label="position.name" :value="position.name"/>
          </el-select>

        </el-form-item>

        <el-form-item>
          <el-button type="primary" @click="onQuery">查询</el-button>
        </el-form-item>

        <div class="btm">
          <el-button @click="()=>{$router.push({name:'manager-create'})}">添加员工</el-button>
        </div>
      </el-form>

    </template>

    <el-table :data="queryResult.records" stripe style="width: 100%" size="large">
      <el-table-column  type="index" label="序号" width="80px"  align="center"/>
      <el-table-column prop="id" label="员工ID" width="auto"  align="center"/>
      <el-table-column prop="name" label="姓名" width="80px" />


        <el-table-column prop="portrait" label="头像" width="auto" align="center" v-slot="{row}">
          <el-avatar :size="50" :src-set="row.portrait || userIcon"></el-avatar>
        </el-table-column>

      <el-table-column   label="登录" width="auto" align="center" v-slot="{row}">
        <el-switch
            v-model="row.role"
            class="mb-2"
            style="--el-switch-on-color: #13ce66; --el-switch-off-color: #ff4949"
            active-text="启用"
            active-value="admin"
            inactive-value="staff"
            inactive-text="禁止"
            @change="handleChange($event,row.id)"
        />

      </el-table-column>
      <el-table-column prop="phone" label="电话" width="auto"  align="center"/>
      <el-table-column prop="gender" label="性别" width="60px"  align="center"/>
      <el-table-column prop="position" label="职位" width="auto"  align="center"/>
      <el-table-column label="操作"  width="auto" align="center" v-slot="{row}">

        <el-button type="info" @click="$router.push({name:'manager-edit',params:{managerId:row.id}})">编辑</el-button>

      </el-table-column>

    </el-table>
  </el-card>
</template>

<style scoped lang="scss">

.el-form{
  margin-top: 20px;
  display: flex;
  .btm{
    margin-left: 400px;
  }

}

</style>