
<script setup>
import {
  cart,
  isPay,
  payMountOfUse, seats,
  selectedList,
  selectedSeats,
  ticketType,
  info, isCash
} from "@/view/sales/payPart.js";
import PayDialog from "@/view/sales/PayDialog.vue";
import DialogOfAskPay from "@/view/sales/DialogOfAskPay.vue";
import {buyTicket} from "@/api/movie.js";



const payDialog = ref();
const handlePaymentClick =async (type) => {

  selectedList.value.forEach(item => {
    item.payMethod = type;
  });

  // 购物车的内容决定是否响应
  if (cart.value.length !== 0) {

    if (type === 'member') {
      await payDialog.value.initAndShow(selectedList.value);
      return false; //不参加后续判断
    } //会员支付的弹窗

    dialogOfAskPay.value.initAndShow(selectedList.value)//打开普通支付的弹窗
    if (type === "cash"){
      isCash.value = true;
    }else {
      isCash.value = false;
    }
    console.log(type)

  //   电影票
  }else if (selectedSeats.value.length!==0){

    let data = {
      type:ticketType.value.type,
      price:ticketType.value.price,
      ordered:seats.value,
      buy:selectedSeats.value,
      movie:info.value.id,
      payMethod:type,
      userId:0
    }
    // 会员
    if (type === 'member') {
      await payDialog.value.initAndShow(selectedList.value,data);
      return false; //不参加后续判断
    }

//打开普通支付的弹窗
    await dialogOfAskPay.value.initAndShow(selectedList.value,data)

  }

};

const dialogOfAskPay = ref()
</script>

<template>
  <DialogOfAskPay ref="dialogOfAskPay"/>
  <el-footer class="foot" v-show="isPay">
    <div class="footer-container">

      <div class="payment-methods">
        <h3 style="margin-right: 20px">收银台</h3>
        <div class="payment-item" @click="handlePaymentClick('alipay')">
          <img class="pay-icon" src="@/assets/pay/alipay.svg" alt="支付宝"><span>支付宝</span>
        </div>

        <div class="payment-item" @click="handlePaymentClick('member')">
          <img class="pay-icon" src="@/assets/pay/member.svg" alt="会员 "><span>会员</span>
        </div>

        <div class="payment-item" @click="handlePaymentClick('wechat')">
          <img class="pay-icon" src="@/assets/pay/wechatpay.svg" alt=" 微信"><span>微信</span>
        </div>

        <div class="payment-item" @click="handlePaymentClick('cash')">
          <img class="pay-icon" src="@/assets/pay/pay.svg" alt=" 现金"><span>现金</span>
        </div>
      </div>

<!--      展示金额-->
      <div class="total-price">
        <h3>订单总价:</h3>
        <span class="price" v-show="selectedList.length !=0 ">¥ {{payMountOfUse}}</span>
        <span class="price" v-show="selectedSeats.length !=0 ">¥ {{ selectedSeats.length * ticketType.price}}</span>
      </div>
    </div>

  </el-footer>
  <PayDialog  ref="payDialog"/>
</template>



<style scoped lang="scss">

.foot {
  background-color: #b0daff;
  height: 12vh;
  display: flex;
  justify-content: center;
  align-items: center;
}

.footer-container {
  display: flex;
  justify-content: space-between;
  align-items: center;
  width: 80%;
  padding-top: 10px;
}

.payment-methods {
  border: 1px solid #bce2ff;
  display: flex;
  justify-content: space-between;
  border-radius: 5px;
  padding: 20px;

}

.payment-item {
  margin-left: 20px;
  display: flex;
  justify-content: center;
  align-items: center;
  cursor: pointer;
  padding: 15px ;
  border-radius: 5px;
  background-color: #d5eafd;
  .pay-icon{
    width: 25px;
    height: 25px;
  }

}
.payment-item:hover {
  transform: scale(1.2); /* 悬停时的缩放 */
  background-color: #eff8ff;
}


/* 订单总价标题样式 */

.total-price {
  margin-top: 20px;
  font-size: 16px;
  color: #333;

}

/* 订单总价金额样式 */
.price {
  font-size: 20px;
  font-weight: bold;
  color: #ff4949;
}


</style>