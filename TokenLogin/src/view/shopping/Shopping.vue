<script setup>
import {shoppingMethod,Resultshopping} from "@/composables/useShopping.js"
import { reactive } from 'vue'
import DialogCreateOrEditShopping from "@/view/shopping/DialogCreateOrEditShopping.vue";
shoppingMethod()



// 表单
const formInline = reactive({
  name: '',
})

const onSubmit = () => {
  console.log('submit!')
}


// 开启弹窗
const dialogCreateOrEditShopping = ref()
</script>

<template>
  <el-card style="width: auto">
    <template #header>
      <el-form :inline="true" :model="formInline" class="demo-form-inline">
        <el-form-item label="名称">
          <el-input v-model="formInline.name" placeholder="商品名" clearable />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="shoppingMethod(formInline)">查询</el-button>
        </el-form-item>

        <el-form-item style="margin-left: 700px">
          <el-button type="primary" @click="dialogCreateOrEditShopping.initAndShow()">新增</el-button>
        </el-form-item>
      </el-form>

    </template>
<!--    显示区域-->
    <el-table :data="Resultshopping" border style="width: 100%">
      <el-table-column type="index" label="序号" width="180" />

      <el-table-column label="图片" width="auto" v-slot="{row}">
        <el-avatar :size="50" :src-set="row.iamge_url" />
      </el-table-column>

      <el-table-column prop="name" label="名称" width="auto" />
      <el-table-column prop="stock" label="库存" width="auto" />
      <el-table-column prop="price" label="价格"  width="auto"/>
      <el-table-column prop="status" label="上架销售"  width="auto" v-slot="{row}">
        <el-switch
            v-model="row.status"
            class="ml-2"
            style="--el-switch-on-color: #13ce66; --el-switch-off-color: #ff4949"
            active-text="是"
            inactive-text="否"
            active-value="ENABLE"
            inactive-value="DISABLE"
        />
      </el-table-column>
      <el-table-column prop="description" label="相关电影"  width="auto"/>

      <el-table-column label="操作"  width="auto" v-slot="{row}">
        <el-button  @click="dialogCreateOrEditShopping.initAndShow(row.id)">编辑</el-button>
      </el-table-column>

    </el-table>

  </el-card>
  <DialogCreateOrEditShopping ref="dialogCreateOrEditShopping"/>
</template>

<style scoped lang="scss">

</style>