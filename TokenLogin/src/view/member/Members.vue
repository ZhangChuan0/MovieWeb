<script setup>
import { ref } from 'vue'
import DialogAsk from "@/view/member/DialogAsk.vue";
import {useRouter} from "vue-router";
const router=useRouter()
// 定义菜单项数据
const menuItems = ref([
  { label: '开户', icon: 'el-icon-user-solid' },
  { label: '卡查询', icon: 'el-icon-bank-card' },
  { label: '修改资历资料', icon: 'el-icon-edit' },
  { label: '修改密码', icon: 'el-icon-lock' },
  { label: '重置密码', icon: 'el-icon-refresh' },
  { label: '退票', icon: 'el-icon-warning-outline' },
  { label: '充值', icon: 'el-icon-coin' },
  { label: '查询历史消费', icon: 'el-icon-search' },
])

const dialogAsk = ref() //弹窗
const handleMenuItemClick=(item)=>{
  if (item !== 0){
    dialogAsk.value.initAndShow(item)
  }else {
    router.push({name:'members-operation',params:{id:0}})
  }


}


</script>

<template>
  <div class="menu-container">
    <el-row :gutter="20">
      <el-col :span="6" v-for="(item, index) in menuItems" :key="index">
        <el-card class="menu-item" shadow="hover" @click="handleMenuItemClick(index)">
          <i :class="item.icon" class="item-icon"></i>
          <span class="item-label">{{ item.label }}</span>
        </el-card>
      </el-col>
    </el-row>
  </div>
  <DialogAsk ref="dialogAsk"/>
</template>

<style scoped lang="scss">
.menu-container {
  padding: 20px;
}

.menu-item {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  height: 120px;
  cursor: pointer;

  .item-icon {
    font-size: 32px;
    margin-bottom: 10px;
  }

  .item-label {
    font-size: 14px;
    text-align: center;
  }
}
</style>