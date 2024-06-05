<script setup>
import {memberList, searchForm, searchMember} from "@/composables/useMember.js";
import { ref } from 'vue'
import {returnTicket} from "@/api/member.js";
import {useRouter} from "vue-router";
import {ElMessage} from "element-plus";

const router = useRouter()
const props = defineProps({
  form:{
    type:Object,
    default:{}
  }

})


const movieList = ref([])

searchMember({item:1})


watchEffect(()=>{

  movieList.value = memberList.value.records.filter(item=>item.item_type === "movie")
  console.log("movie is ",movieList.value)
})






searchForm.value = props.form // 默认携带用户参



const formInline = ref()

const onSubmit = () => {

  searchMember({search:formInline.value})

}

const handleCurrentChange = (item)=>{

  searchMember({search:formInline,item})

}

const popMoney =async (id)=>{

  const {data} = await returnTicket(id)
  if (data.code){
    ElMessage.success("退票成功！")
    await router.push({name:'members'})
  }

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

    <el-table :data="movieList" border style="width: 100%">
      <el-table-column type="index" label="序号" width="auto"/>
      <el-table-column prop="createTime" label="创建时间" width="auto"/>
      <el-table-column prop="item_name" label="商品" width="auto"/>
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
        </template>
      </el-table-column>


      <el-table-column prop="status" label="状态" />
      <el-table-column label="操作" v-show="">
        <template #default="{ row }">

          <el-button v-if="row.item_type === 'movie' && row.status !== '已退款'" type="danger" @click="popMoney(row.id)">退票</el-button>
        </template>

      </el-table-column>

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