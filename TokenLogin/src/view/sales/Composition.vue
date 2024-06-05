<script setup>
import PayButtom from "@/view/sales/PayButtom.vue";
import Body from "@/view/sales/Body.vue";
</script>

<template>
    <el-container class="main-box">
      <Body/>
      <PayButtom/>
    </el-container>


</template>

<style scoped lang="scss">

.main-box{

  flex-direction: column;  //内样式垂直显示
  height: 90vh;
}
</style>