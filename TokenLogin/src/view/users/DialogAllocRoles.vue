<script setup>
import {ref} from 'vue'
import {getRoleWithUserPermission,allocateUserRoles} from "@/api/custom.js";
import {queryUsers} from "@/composables/useUser.js";
import {ElMessage} from "element-plus";

const form = ref({
  userId:"",
  roleList:[]
})
const formLabelWidth = '140px'
// 显示弹窗
const dialogFormVisible = ref(false)


// 保存所有的role信息

const allRole   = ref([])
// 封装显示的方法
const initAndShow = async (userId) => {
  dialogFormVisible.value = true

  // 查询结果
  const {data} = await getRoleWithUserPermission(userId)
  if (data.code === "000000"){
    allRole.value = data.data.records

    form.value.userId = userId
    form.value.roleList = data.data.records.filter((r) =>r.hasPermission === "True").map((r) =>r.index)
    // 过滤出来已经选中的元素



  }
}


const onSubmit = async ()=>{
  console.log("前端收集到的参数",form.value.userId,form.value.roleList)
  const res = await allocateUserRoles(form.value.userId,form.value.roleList).finally(()=>{dialogFormVisible.value=false})

  if (res.data){
    ElMessage.success("折扣分配成功")
  }

}


defineExpose({
  initAndShow
})

</script>

<template>
  <el-dialog v-model="dialogFormVisible" title="分配折扣" width="500">
    <el-form :model="form">

      <el-form-item label="折扣力度" :label-width="formLabelWidth" >
        <el-select v-model="form.roleList" placeholder="选择优惠力度" multiple>
          <el-option v-for="role in allRole " :key="role.index" :label="role.name" :value="role.index"/>
        </el-select>
      </el-form-item>

    </el-form>
    <template #footer>
      <div class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取消</el-button>
        <el-button type="primary" @click="onSubmit">
          确认
        </el-button>
      </div>
    </template>
  </el-dialog>
</template>

<style scoped lang="scss">

</style>