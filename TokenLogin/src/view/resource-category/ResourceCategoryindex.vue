<script setup>
import {getAllResourceCategory,allResourceCategory,handleDelete} from "@/composables/useResourceCategory.js"
import DialogCreateOrEdit from "@/view/resource-category/DialogCreateOrEdit.vue";

// 一旦到这个页面就触发获取资源的方法
getAllResourceCategory()
console.log(allResourceCategory.value)

// 子组件转递数据到父组件
const dlgCreateOrEdit = ref()

</script>

<template>

  <el-card >
    <template #header>
      <div class="card-header">
        <h3>资源类别列表</h3>
        <el-button type="primary" @click="dlgCreateOrEdit?.initAndShow()">创建类别</el-button>
<!--        不使用路由的形式创建表单，而采用弹窗-->
      </div>
    </template>
    <el-table :data="allResourceCategory" border>
<!--      当type为index时，就为自动增加的序号-->
      <el-table-column type="index" label="序号" width="auto" align="center"/>
      <el-table-column prop="order" label="排序" width="auto" align="center"/>
      <el-table-column prop="name" label="姓名" width="180" align="center"/>
      <el-table-column prop="address" label="地址" align="center"/>
<!--      我存入的时间不需要格式化，所以不用函数-->
      <el-table-column prop="createDate" label="创建时间" align="center"/>
      <el-table-column label="操作" width="180" align="center" v-slot="{row}">  <!--  通过作用域插槽，获取每一行的数据    -->
        <el-button type="primary" @click="dlgCreateOrEdit?.initAndShow(row.id)">编辑</el-button>
        <el-button type="danger" @click="handleDelete(row.id)">删除</el-button>
      </el-table-column>
    </el-table>
<!--    子组件-->
    <DialogCreateOrEdit ref="dlgCreateOrEdit"/>
  </el-card>

</template>

<style scoped lang="scss">

.card-header{
  display: flex;
  justify-content: space-between;
  align-content: center;
}

.text{
  font-size: 14px;
}

.item{
  margin-bottom: 18px;
}
.box-card{
  width: auto;
}

</style>