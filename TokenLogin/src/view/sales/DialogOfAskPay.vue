<script setup>
import { ref } from 'vue'
import {form} from "@/composables/useMember.js";
import {resetPassword} from "@/api/member.js";
import {ElMessage} from "element-plus";
import {postSales} from "@/api/sales.js";
import {cart, isCash, isPay, selectedList, selectedSeats} from "@/view/sales/payPart.js";
import {useRouter} from "vue-router";
import Sales from "@/view/sales/Sales.vue";
import {buyTicket} from "@/api/movie.js";
const router = useRouter()
const dialogVisible = ref(false)

const pass  =ref([])

// 电影座位包
const dataOfSeat = ref()

// 弹窗所属于的状态 true-电影
const dialogStatus = ref(true)


// 模拟加载时间
const loading = ref(true);
const initAndShow= (item,data)=>{
  loading.value = true;
  dialogVisible.value = true;
  if (data){
    dialogStatus.value = true
    dataOfSeat.value = data
  }else {
    dialogStatus.value = false
    pass.value = item
  }

  delayTime()

}

defineExpose({
  initAndShow
})

const content = ref("")

content.value ="确认购买"

const sales = ref()


//  确定按钮
const onSubmitOperation =async ()=>{

  if (dialogStatus.value){

       // 影片

    if (dataOfSeat.value.price === 0){
      ElMessage.error("票价类型还没选择")
      return new Promise(()=>{})
    }
       const  res = await buyTicket(dataOfSeat.value)
    if (res.data.code === "000000"){
      ElMessage.success("购买成功")
      selectedSeats.value = []
      isPay.value = false
      dialogVisible.value = false;
    }else {
      ElMessage.error("购买失败")
    }


  }else {


  //  买玩具食物等

  const {data} = await postSales(selectedList.value)

  if (data.code === "000000"){
    ElMessage.success("支付成功")
    isPay.value = false //收银台下降
    cart.value = []
    await sales.value.fresh()
    dialogVisible.value = false;
  }else {
    ElMessage.error("支付失败")
  }

  }

}


const svg = `
        <path class="path" d="
          M 30 15
          L 28 17
          M 25.61 25.61
          A 15 15, 0, 0, 1, 15 30
          A 15 15, 0, 1, 1, 27.99 7.5
          L 15 15
        " style="stroke-width: 4px; fill: rgba(0, 0, 0, 0)"/>
      `

const delayTime = ()=>{
  const randomTime = () => {
    return Math.floor(Math.random() * 3000) + 1000; // 生成随机时间，范围在1000到3999毫秒之间
  };

  setTimeout(() => {
    loading.value = false;
  }, randomTime());
}






</script>

<template>
  <el-dialog
      v-model="dialogVisible"
      title="提示"
      width="500"
  >

    <div
        v-show="!isCash"
        v-loading="loading"
        element-loading-text="加载中..."
        :element-loading-spinner="svg"
        element-loading-background="rgba(122, 122, 122, 0.8)"

    >

     <img src="@/assets/qrcode.png" style="margin-left:180px;transform:scale(1.4);width: 100px;height: 100px" alt="unkown">
    </div>

    <template #footer>
      <div class="dialog-footer">
        <el-button @click="()=>{dialogVisible = false
           }">取消</el-button>
        <el-button type="primary" @click="onSubmitOperation()">
          确定
        </el-button>
      </div>
    </template>
  </el-dialog>
  <Sales ref="sales" v-show="false"/>
</template>

<style scoped lang="scss">

</style>