<script setup>
import request from "../utils/request.js";
import {ref} from "vue";

let info = ref()

  request({
    method: "GET",
    url: "/login/1"
  }).then(res => {

    info.value = ref(res.data);
    console.log(info)
  })

</script>

<template>

  后端传过来的数据:<span v-text="info"></span>

  <el-main>
    <el-scrollbar>
      <RouterView />
    </el-scrollbar>
  </el-main>
</template>

<style scoped>
  .el-scrollbar{

    width: 100vh;
    height: 20vh;
    background-color: #a8a1a8;

  }
</style>