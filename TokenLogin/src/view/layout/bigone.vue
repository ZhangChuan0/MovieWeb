<script setup>
import {User as IconUser, Setting, MessageBox, House, View} from "@element-plus/icons-vue"

</script>

<template>
图标：
  <el-icon size="size">
    <MessageBox/>
    <IconUser/>
    <Setting/>
  <House /><View />
  </el-icon>
<!--  <RouterView></RouterView>-->

</template>

<style scoped>
.el-icon{
  width: 100vh;
}
</style>