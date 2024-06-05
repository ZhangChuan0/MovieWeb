<script setup>
import { ref } from 'vue'
import {useRouter} from "vue-router";
import {form} from "@/composables/useMember.js";
import {ElMessage} from "element-plus";

import {postSalesByUser, validateUser} from "@/api/sales.js";
import {cart, isPay, selectedSeats} from "@/view/sales/payPart.js";
import Sales from "@/view/sales/Sales.vue";
import {buyMovie} from "@/api/movie.js";
import Seat from "@/view/sales/Seat.vue";


const router = useRouter()
const dialogFormVisible = ref(false)
const formLabelWidth = '140px'


const isPass = ref(false)

const init = ({
  name: '张杰',
  password: '12345678',
})
// 余额
const balance = ref("")
//购物类型
const shoppingType = ref(false)


// 购物信息
const shoppingList = ref([])

const movie = ref()

const initAndShow= (item,movieInfo)=>{
  dialogFormVisible.value = true;
  form.value = init
isPass.value = false
  if (!movieInfo){

    shoppingList.value = item
    console.log("food")
    shoppingType.value = true;
  }else{
  // 影票

    shoppingType.value = false;
    movie.value = movieInfo
  }



}

//子组件元素
const sales = ref()


//座位的元素
const seatFlush = ref();
// 支付
const payFor =async () =>{


    // 食物
if (shoppingType.value){
    const {data} = await postSalesByUser(shoppingList.value,form.value)

    if (data.code === "000000"){
      ElMessage.success("支付成功")
      cart.value = []
      isPay.value = false //收银台下降
      await sales.value.fresh()
      dialogFormVisible.value = false;
      await router.push({name:"eat"})
    }else {
      ElMessage.error("支付失败")
    }


}else {

if (movie.value.price === 0){
  ElMessage.error("票价类型还没选择")
  return new Promise(()=>{})
}


  const  res = await buyMovie(movie.value,form.value)
  if (res.data.code === "000000"){
    // 刷新子界面--失败
    await  seatFlush.value.divition({id:movie.value.movie})
    dialogFormVisible.value = false;

    // await router.push({name:"movie"}) //跳转

    isPay.value = false //收银台下降
    ElMessage.success("支付成功")
  }else {
    ElMessage.error("支付失败")
  }


}

}

const onSure=async ()=>{

//   验证账户和密码是否正确，并且得到余额信息
  const {data} = await validateUser(form.value)
  console.log(data)
  if (data.code === "000000"){
    balance.value = data.data
    isPass.value = true
    ElMessage.success("校验成功")
  }else {
    ElMessage.error("校验失败")
  }

}



defineExpose({
  initAndShow
})
</script>

<template>
<Sales ref="sales" v-show="false"/>
  <Seat ref="seatFlush" v-show="false"/>
  <el-dialog v-model="dialogFormVisible"
             title="校验"
             width="500">
    <el-form :model="form">
      <el-form-item label="会员名" :label-width="formLabelWidth">
        <el-input v-model="form.name" autocomplete="off" />
      </el-form-item>
      <el-form-item label="密码" :label-width="formLabelWidth">
        <el-input v-model="form.password" autocomplete="on" />
      </el-form-item>
    </el-form>

    <el-card v-show="isPass">
      <h2>余额</h2>
      <span style="font-size: 20px;color: #36cdfc ">￥{{balance}}</span>
      <el-button @click="payFor">支付</el-button>
    </el-card>

    <template #footer>
      <div class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取消</el-button>
        <el-button type="primary" @click="onSure()">
          确定
        </el-button>
      </div>
    </template>

  </el-dialog>
</template>

<style scoped lang="scss">

</style>