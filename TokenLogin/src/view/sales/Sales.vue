<script setup>
import { ref } from 'vue'
import {getFood,getTools,foodList,toolsList} from "@/composables/useSales.js";
import {isPay, payMountOfUse, cart, totalPriceOfcar} from "@/view/sales/payPart.js";
import PayDialog from "@/view/sales/PayDialog.vue";


getFood()
getTools()

onMounted(()=>{
  getFood()
  getTools()
})

// 定义当前选中的类别
const activeCategory = ref('food')



// 添加商品到购物车
const addToCart = (product,category) => {
  // 检查购物车中是否已经存在该商品
  const existingProductIndex = cart.value.findIndex(item => item.product.name === product.name)
  if (existingProductIndex !== -1) {
    // 商品已存在于购物车中，检查是否超过库存
    const existingProduct = cart.value[existingProductIndex]
    if (existingProduct.product.quantity + 1 <= (product.stock || parseInt(product.password))) {
      // 将商品数量加1
      existingProduct.product.quantity++
    } else {
      // 超过库存，给出提示或其他操作
      console.log("购物车中的商品数量超过库存")
    }
  } else {
    // 商品不存在于购物车中，直接添加到购物车
    if (1 <=(parseInt(product.password) || product.stock)) {
      // 将商品数量设为1
      product.quantity = 1
      cart.value.push({product,category})
    } else {
      // 超过库存，给出提示或其他操作
      console.log("新添加的商品库存不足")
    }
  }
}



// 计算总价
const totalPrice = computed(() => {
  return cart.value.reduce((total, item) => total + parseInt(item.product.phone || item.product.price) * item.product.quantity, 0);
});

watchEffect(() => {
  payMountOfUse.value = totalPrice
  totalPriceOfcar.value = totalPrice
})



// 清空
const clearCart = () => {
  cart.value = []
  isPay.value = false
}

const removeFromCart = (index) => {
  cart.value.splice(index, 1)
}



//不可点击判断
const isDisabled = (product) => {
  const existingProduct = cart.value.find(item => item.product.name === product.name);
  if (!existingProduct) return false; // 如果购物车中没有该商品，则不禁用
  const selectedQuantity = cart.value.reduce((total, item) => {
    if (item.product.name === product.name) {
      return total + item.product.quantity;
    } else {
      return total;
    }
  }, 0);
  return selectedQuantity >= (parseInt(product.password) || product.stock);
};



// 结算按钮
const checkout = ()=>{

  isPay.value = true
}



// 刷新父页面
const fresh = () => {

  getFood()
  getTools()

}

defineExpose({
  fresh
})



</script>

<template>
<!--新的页面内布局-->
  <el-main>
  <div class="sale-container">

    <div class="category-tabs">

        <el-tabs v-model="activeCategory">

          <el-tab-pane label="食物" name="food">

            <div class="product-list">
              <!-- 食物商品列表 -->
              <el-row :gutter="20">
                <el-col :span="6" v-for="product in foodList" :key="product.name">
                  <el-card class="product-item"
                           shadow="hover"
                           @click="addToCart(product,'food')"
                           :class="{ 'disabled': isDisabled(product)}"
                  >
                    <img :src="product.portrait" class="product-image"  alt="null"/>
                    <div class="product-name">{{ product.name }}</div>
                    <div class="product-price">¥{{ product.phone }}</div>
                    <div class="product-stock">库存{{product.password}}</div>
                  </el-card>

                </el-col>
              </el-row>
            </div>
          </el-tab-pane>

          <el-tab-pane label="周边" name="toys">
            <div class="product-list">
              <!-- 玩具商品列表 -->
              <el-row :gutter="20">
                <el-col :span="6" v-for="product in toolsList" :key="product.name">
                  <el-card class="product-item" shadow="hover"
                           @click="addToCart(product,'tool')"
                           :class="{ 'disabled': isDisabled(product)}"
                  >
                    <img :src="product.iamge_url" class="product-image"  alt="null"/>
                    <div class="product-name">{{ product.name }}</div>
                    <div class="product-price">¥{{ product.price }}</div>
                    <div class="product-stock">库存{{product.stock}}</div>
                  </el-card>
                </el-col>
              </el-row>
            </div>
          </el-tab-pane>

        </el-tabs>


    </div>

    <div class="cart">
      <el-card class="cart-card">
        <div class="cart-header">购物车</div>
        <div class="cart-items">
          <div v-for="item in cart" :key="item.name" class="cart-item">
            <span>{{ item.product.name }}</span>
            <span>¥{{ item.product.price || item.product.phone }}</span>
            <span>X {{item.product.quantity}}</span>
            <el-button type="danger" size="small" @click="removeFromCart(index)">删除</el-button>
          </div>
        </div>
        <div class="cart-total">总计: ¥{{ totalPrice }}</div>
        <div class="cart-actions">
          <el-button type="primary"  @click="checkout">
            <img class="pay-icon" src="@/assets/pay/getM.svg" alt=" 现金">
            结算
          </el-button>

          <el-button @click="clearCart">取消</el-button>
        </div>
      </el-card>
    </div>
  </div>
  </el-main>
</template>

<style scoped lang="scss">
.pay-icon{
  width: 25px;
  height: 25px;
}
.category-tabs{
  width: 70%;
}
.sale-container {
  display: flex;
  justify-content: space-between;
  padding: 10px;
}

.product-list  {
  width: 90%;
}

.product-item {
  display: flex;
  justify-content: space-evenly;
  align-items: center;
  padding: 10px;
  cursor: pointer;
&.disabled{
  cursor: not-allowed;
  opacity: 0.8;
  background-color: #d4eeff;
}
  .product-image {
    width: 100px; // 设置图片宽度
    height: 100px; // 设置图片高度
    object-fit: cover; // 保持图片的长宽比
    margin-bottom: 10px; // 添加底部外边距
  }

  .product-name {
    font-size: 14px;
  }

  .product-price {
    font-size: 16px;
    font-weight: bold;
    color: #409eff;
  }
  .product-stock{
    font-size: 15px;
  }
}

.cart {
  width: 28%;
}

.cart-card {
  padding: 10px;
}

.cart-header {
  font-size: 16px;
  font-weight: bold;
  margin-bottom: 10px;
}

.cart-items {
  max-height: 300px;
  overflow-y: auto;

}

.cart-item {
  display: flex;
  justify-content: space-between;
  margin-bottom: 5px;
}

.cart-total {
  margin-top: 10px;
  text-align: right;
  font-size: 16px;
  font-weight: bold;
}

.cart-actions {
  margin-top: 10px;
  text-align: right;
}
</style>