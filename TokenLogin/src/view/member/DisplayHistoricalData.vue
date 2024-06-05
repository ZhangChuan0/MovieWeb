<script setup>
import {memberList, searchForm, searchMember} from "@/composables/useMember.js";
import { reactive,ref } from 'vue'
const props = defineProps({
  form:{
    type:Object,
    default:{}
  }

})

searchForm.value = props.form // 默认携带用户参

searchMember({item:1})

const formInline = ref()

const onSubmit = () => {

  searchMember({search:formInline.value})

}

const handleCurrentChange = (item)=>{

  searchMember({search:formInline,item})
  console.log("页面"+memberList.value.pages)
}

</script>

<template>
  <el-card style="width: auto">
    <template #header>
      <div class="card-header">
        <el-form :inline="true"  class="demo-form-inline">
          <el-form-item label="商品">
            <el-input v-model="formInline" placeholder="请输入" clearable />
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="onSubmit">查询</el-button>
          </el-form-item>
        </el-form>
      </div>
    </template>

    <el-table :data="memberList.records" border style="width: 100%">

      <el-table-column type="index" label="序号" width="auto"/>
      <el-table-column prop="createTime" label="创建时间" width="auto"/>
      <el-table-column prop="item_name" label="商品" width="auto" />
      <el-table-column prop="totalAmount" label="总额" width="auto"/>
      <el-table-column prop="item_total" label="数量" width="50px" />
      <el-table-column prop="remark" label="位置" width="auto" />

      <el-table-column label="票形">
        <!-- 使用具名插槽 -->
        <template #default="{ row }">
          <!-- 判断票型是否为空 -->
          <span v-if="row.price_type">{{ row.price_type }}</span>
          <span v-else>非影票</span>
        </template>
      </el-table-column>

      <el-table-column label="支付方式" >
        <template #default="{ row }">
          <span v-if="row.payMethod === 'member'">会员卡</span>
          <span v-else-if="row.payMethod === 'alipay'">支付宝</span>
          <span v-else-if="row.payMethod === 'cash'">现金</span>
          <span v-else-if="row.payMethod === 'wechat'">微信</span>
          <span v-else>未知</span> <!-- 添加一个默认条件 -->
        </template>
      </el-table-column>


      <el-table-column prop="status" label="状态" />
    </el-table>

    <template #footer>

      <el-pagination
                    background
                    v-model:current-page="memberList.hitCount"
                     layout="prev, pager, next"
                    :page-size="7"
                     :total="memberList.pages"
                    @current-change="(item)=>handleCurrentChange(item)"
      />

    </template>

  </el-card>
</template>

<style scoped lang="scss">


.demo-form-inline .el-input {
  --el-input-width: 220px;
}

.demo-form-inline .el-select {
  --el-select-width: 220px;
}


</style>