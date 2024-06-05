<script setup>
import {queriedResult,queryCondition,queryUsers} from "@/composables/useUser.js";
import userIcon from "@/assets/user/portrait.svg" //默认头像
import {enableUser,forbidUser} from "@/api/custom.js";
import {ElMessage} from "element-plus";
import {ref,watch} from "vue";
import DialogAllocRoles from "@/view/users/DialogAllocRoles.vue";
import {useRouter} from "vue-router";
const router = useRouter()
queryUsers()

// 显示分配小菜单
const dialogAllocRoles = ref();

const onAlloc = (row) => {
  dialogAllocRoles.value.initAndShow(row.id);
}


// 切换用户状态
const handleChange = async (act,userId)=>{

  // 复杂的写法

  let actions = {
    ENABLE:{msg:"上架",fn:enableUser},
    DISABLE:{msg:"下架",fn:forbidUser}
  }

  const {data} =await actions[act].fn(userId)
  if (data){
    ElMessage.success(`${actions[act].msg}商品成功`)
  }else {
    ElMessage.success(`${actions[act].msg}商品失败`)
  }

}

//表单


// 时间范围
const  timeRange = ref("")


// 当时间范围改变时 修改相应组件

watch(timeRange,(newTime)=> {
  if (Array.isArray(newTime)){
    console.log(newTime)
        queryCondition.value.createTime = newTime[0].toLocaleDateString()
        queryCondition.value.updateTime = newTime[1].toLocaleDateString()

  }else {
    queryCondition.value.createTime = ""
    queryCondition.value.updateTime = ""

  }
})




const handleSizeChange = (val) => {
queryUsers({
  pageSize:val,
  currentPage:1
})
}
const handleCurrentChange = (val) => {
  queryUsers({
    currentPage:val
  })
}


</script>

<template>
  <el-card >
    <template #header>
      <el-form :inline="true" :model="queryCondition" class="demo-form-inline">

        <el-form-item label="编号查询" clearable >
          <el-input v-model="queryCondition.phone" placeholder="编号查询" clearable />
        </el-form-item>

        <el-form-item label="保质期查询">
          <el-date-picker
              v-model="timeRange"
              type="datetimerange"
              start-placeholder="开始时间"
              end-placeholder="结束时间"
              format="YYYY-MM-DD HH:mm:ss"
              date-format="YYYY/MM/DD ddd"
              time-format="HH:mm:ss"
          />
        </el-form-item>

        <el-form-item>
          <el-button type="primary" @click="queryUsers({currentPage:1})">查询</el-button>
        </el-form-item>

        <el-form-item>
          <el-button @click="router.push({name:'users-createOrEdit'})">增加</el-button>
        </el-form-item>


      </el-form>
    </template>

    <el-table :data="queriedResult.records" stripe style="width: auto">
      <el-table-column type="index" label="序号" width="auto"  align="center"/>
      <el-table-column prop="id" label="食品编号" width="auto"  align="center"/>
      <el-table-column prop="name" label="名称" width="100px" align="center" />

      <el-table-column prop="portrait" label="包装" width="auto" align="center" v-slot="{row}">
        <el-avatar :size="50" :src-set="row.portrait || userIcon"></el-avatar>
      </el-table-column>

      <el-table-column prop="password" label="库存" width="auto "/>

      <el-table-column prop="phone" label="价格" width=" 50px"/>
      <el-table-column prop="createTime" label="生产日期" width="auto" align="center"/>
      <el-table-column label="状态" width="auto" align="center" v-slot="{row}">

        <el-switch
            v-model="row.status"
            active-text="上架"
            active-value="ENABLE"
            inactive-value="DISABLE"
            inactive-text="下架"
            @change="handleChange($event,row.id)"
            style="--el-switch-on-color: #13ce66; --el-switch-off-color: #ff4949"
        />

      </el-table-column>

      <el-table-column  label="操作" width="auto" v-slot="{row}">
        <el-button type="primary" @click="onAlloc(row)" >分配类型</el-button>
        <el-button type="info" @click="router.push({name:'users-edit',params:{userId:row.id}})">编辑</el-button>
      </el-table-column>

    </el-table>

    <template #footer>

      <el-pagination
          v-model:current-page="queriedResult.hitCount"
          v-model:page-size="queriedResult.current"
          :page-sizes="[5, 10, 15, 30]"
          :background="true"
          layout="total, sizes, prev, pager, next, jumper"
          :total="queriedResult.pages"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
      />

    </template>
    <DialogAllocRoles  ref="dialogAllocRoles"/>
  </el-card>

</template>

<style scoped lang="scss">
.el-card{
  width: auto;
}
.el-pagination{
  display: flex;
  justify-content: center;
  margin-top: 14px;
}
</style>