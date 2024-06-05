<script setup>
import {zhCn} from "element-plus/es/locale/index";
</script>

<template>

<!-- 国际化 -->
  <el-config-provider :locale="zhCn">
    <RouterView/>
  </el-config-provider>
</template>
<style scoped>

</style>
