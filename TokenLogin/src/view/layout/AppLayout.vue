<script setup>
import AppAside from "@/view/layout/AppAside.vue";
import AppHeader from "@/view/layout/AppHeader.vue";
import AppMain from "@/view/layout/AppMain.vue";
</script>

<template>
  <div class="common-layout">
    <el-container>

<!--      侧边栏  -->
      <AppAside />
      <el-container class="head-and-main">
<!--        顶部-->
        <AppHeader></AppHeader>

        <AppMain></AppMain>
      </el-container>
    </el-container>
  </div>
</template>

<style scoped lang="scss">

.head-and-main{
  flex-direction: column;  //内样式垂直显示
  height: 100vh;
}

</style>