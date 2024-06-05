<script setup>

import router from "@/router/router.js";
const backHome = ()=>{
  router.push("/index")
}

</script>

<template>
  <div class="box">
    <el-row>
      <el-col :sm="200" :lg="60">
        <el-result
            icon="error"
            title="404 NOT FOUND"
            sub-title="点击回到主页">
          <template #extra>
            <el-button type="primary" @click="backHome">Back</el-button>
          </template>
        </el-result>
      </el-col>
    </el-row>
  </div>
</template>

<style scoped lang="scss">

</style>