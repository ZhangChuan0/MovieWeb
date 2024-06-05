<script setup>
import {useRouter} from "vue-router";
const router = useRouter()

</script>

<template>
  <div style="display: flex">
    <div class="el-button">
      <el-button type="primary" @click="router.push({name:'set-edit',params:{until:'position'}})">岗位</el-button>
    </div>

    <div class="el-button">
      <el-button type="primary" @click="router.push({name:'set-edit',params:{until:'language'}})">语言</el-button>
    </div>

    <div class="el-button">
      <el-button type="primary" @click="router.push({name:'set-edit',params:{until:'room'}})">影厅</el-button>
    </div>
  </div>
</template>

<style scoped lang="scss">
.el-button{
  margin-top: 20px;
  padding: 60px;
  width: 30%;
  height: 80%;
  margin-left: 30px;
}
</style>