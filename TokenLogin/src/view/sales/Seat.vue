<script setup>
import { ref, computed } from 'vue'
import {isPay, seats, selectedSeats,info} from "@/view/sales/payPart.js";
import { getSeat} from "@/api/movie.js";
import { operation2} from "@/composables/useMovie.js";
import occupiedChair from "@/assets/seat/occup.svg"
import emptyChair from "@/assets/seat/empty.svg"
import sellOut from "@/assets/seat/sell.svg"

const seat_fake = ref([])

// 选中位置的图片
// const occupiedChair = '../assets/seat/occup.svg'
// const emptyChair = "../assets/seat/empty.svg"
// const sellOut = "../assets/seat/sell.svg"


// 不可选位置


// 点击座位事件
const toggleSeat = (seatId) => {
  console.log(seatId)
  const index = selectedSeats.value.indexOf(seatId);
  if (index !== -1) {
    selectedSeats.value.splice(index, 1); // 已经选中，从选中数组中移除
  } else {
    selectedSeats.value.push(seatId); // 未选中，添加到选中数组中
  }
}


// 清空所选
const clearSelectedSeats = () => {

  selectedSeats.value = []
  isPay.value = false
  console.log("当前座位",selectedSeats.value)
}



// 是否选中某个座位
const isSelected = computed(() => {
  const selectedSeatSet = new Set(selectedSeats.value);
  return (seatId) => selectedSeatSet.has(seatId);
});



// 加载不同的图片资源
const getImageUrl = (seat) => {
  if (isSelected.value(seat.id)) {
    return occupiedChair;
  } else if (seat.status === '-1') {
    return sellOut;
  } else  {
    return emptyChair;
  }
};




// 提交事件
const onSubmit = async ()=>{
  isPay.value = true

  seats.value = seat_fake.value.flat() //扁平化 方便后端处理

  console.log("seed",selectedSeats.value)

}




// 初始化座位 分配电影信息

const divition = async (item)=>{

  selectedSeats.value = []
  const {data} = await getSeat(item.id)
  info.value.id = item.id
  seat_fake.value =  operation2(data.records)
  info.value.name = item.courseName

}

defineExpose({

  divition

})

</script>
<template>
  <div style="display: flex" class="seat-out-box">
    <div class="seat-container">
      <h2 style="color: black;text-align: center">荧幕</h2>
      <h3 style="color: #ff4949">电影名：<u>{{ info.name }}</u></h3>
      <div v-for="(row, rowIndex) in seat_fake" :key="rowIndex" class="seat-row">
        <div
            v-for="(seat, colIndex) in row"
            :key="seat.id"
            class="seat"
            :class="{ selected: isSelected(seat.id), disabled: seat.status === '-1' }"
            @click="seat.status !== '-1' && toggleSeat(seat.id)"
        >
          <img style="width: 30px" :src="getImageUrl(seat)" alt="Seat" />
        </div>
      </div>
      <div class="actions">
        <el-button @click="clearSelectedSeats">清空所选</el-button>
        <el-button @click="onSubmit" type="primary">
          <img class="pay-icon" src="@/assets/pay/getM.svg" alt="支付宝" />
          确定
        </el-button>
      </div>
    </div>
  </div>
</template>

<style scoped lang="scss">
/* Container styles */
.seat-out-box {

  backdrop-filter: blur(4px) brightness(90%);
  background-color: rgba(248, 242, 242, 0.4);

  padding: 20px;
  border-radius: 10px;
  opacity: 0.9;
}

.seat-container {
  backdrop-filter: blur(4px) brightness(90%);
  background-color: rgb(197, 225, 253,0.3);
  padding: 20px;
  border-radius: 10px;
}

/* Seat styles */
.seat-row {
  display: flex;
  justify-content: center;
}

.seat {
  margin: 5px;
  padding: 10px;
  border-radius: 5px;
  cursor: pointer;
}

.seat img {
  width: 30px;
}

.selected {
  background-color: #4caf50; /* Green */
}

.disabled {
  background-color: #f44336; /* Red */
  cursor: not-allowed;
  //pointer-events: none;
}

/* Button styles */
.actions {
  margin-top: 20px;
  display: flex;
  justify-content: center;
}

.el-button {
  margin: 0 10px;
  padding: 10px 20px;
  border-radius: 5px;
  font-size: 16px;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

.el-button:hover {
  background-color: #a5d6f4;
  color: white;
  transform: scale(1.2);
}

.el-button-primary {
  background-color: #007bff; /* Blue */
  border: none;
  color: white;
}

.el-button-primary:hover {
  background-color: #0056b3; /* Darker blue */
}

.pay-icon {
  width: 20px;
  vertical-align: middle;
  margin-right: 5px;
}
</style>