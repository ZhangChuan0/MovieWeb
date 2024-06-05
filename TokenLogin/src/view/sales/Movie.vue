<script setup>
import {getMovie, hall, movieList} from "@/composables/useMovie.js";
import Seat from "@/view/sales/Seat.vue";
import {resultRoom,getRoomList} from "@/composables/useSet.js";
import {ticketType} from "@/view/sales/payPart.js";


// 初始化页面获取电影列表 房间号码
getMovie()
getRoomList()




const moviePrice = ref(0)
//计算属性，响应式数据
const data = reactive([
  {
    name: "学生",
    get price() {
      return parseInt(moviePrice.value * 0.85);
    },
    selected: false
  },
  {
    name: "标准",
    get price() {
      return parseInt(moviePrice.value);
    },
    selected: false
  },
  {
    name: "会员",
    get price() {
      return parseInt(moviePrice.value * 0.75);
    },
    selected: false
  }
]);

// 房间选择// 选定影厅
const chooseRoom = ()=>{

  getMovie()

}
const seat = ref()

const img_url = ref("")
// 选定电影
const choseOneMovie =(item)=>{
  moviePrice.value = item.discounts
  seat.value.divition(item)
  img_url.value = item.courseListImg

}

//计算图片样式
const seatStyle = computed(() => ({
  marginLeft: '10px',
  width: '100vw',
  height: 'auto',
  // backgroundColor: '#b49393',
  backgroundImage: `url(${img_url.value})`,
  backgroundSize: 'cover', // Ensure the background covers the entire element
  backgroundRepeat: 'no-repeat', // Prevent the background from repeating
}));


// 状态 由什么支付
const typeOfPrice = (selectedItem) => {
  // 将所有的选中状态重置为 false
  data.forEach(item => {
    item.selected = false;
  });
  // 设置当前选中项的 selected 属性为 true
  selectedItem.selected = true;
  // 处理选中项的其他逻辑

  // 改变类型和价格价格参数
  ticketType.value.type = selectedItem.name
  ticketType.value.price = selectedItem.price
};




</script>

<template>
  <el-main >
    <div class="box-movie">
  <div class="aside">
<!--    影厅选择 -->
    <div class="date-pick">
      <h1>影厅选择</h1>
      <el-select
          placeholder="选择影厅"
          v-model="hall"
          @change="chooseRoom"
      >
        <el-option label="不选" value=""/>
        <el-option v-for="room in resultRoom" :Key="room.id" :label="room.name" :value="room.name"/>
      </el-select>

    </div>



<!--    电影列表-->


    <div class="movie-list">
      <el-scrollbar height="600px">
        <div v-for="item in movieList" :key="item.id" class="movie-item" @click="choseOneMovie(item)">
          <img alt="null" class="movie-img" :src="item.courseListImg"/>
          <div class="movie-info">
            <h3>{{item.courseName}}</h3>
            <h4>{{ item.teacherName }}</h4>
            <span>{{item.teacherPosition}} / </span>
            <span v-if="item.brief === 'ENABLE'">3D</span>
            <span v-else>2D</span>
            <div class="movie-duration">时长：{{item.price}}</div>
          </div>
        </div>
      </el-scrollbar>
    </div>



    <div class="fare">
<!--票价-->
      <el-row :gutter="10">
        <el-col :span="8" v-for="item in data" >
          <el-card class="button-group-fare" :class="{ 'selected': item.selected }" @click="typeOfPrice(item)">
            <span class="fare-name">{{item.name}}</span>
            <span class="fare-price">¥{{item.price}}</span>
          </el-card>
        </el-col>
      </el-row>


    </div>


  </div>
<!--座位-->
    <div class="seat" :style="seatStyle">
    <Seat ref="seat"/>
    </div>
    </div>
  </el-main>
</template>

<style scoped lang="scss">
.box-movie{
  display: flex;
  padding: 5px;
  height: 110%;
  box-shadow: 20px 20px 20px 20px #a6d7f6;

  .aside{
    background-color: #c5e1fd;
    width: 300px;
    height: auto;

    .date-pick{

      height: 10vh;
    }

    .movie-list {
      height: 70vh; /* 增加高度 */
      overflow: hidden;
      padding: 10px;
      background-color: #e6f7ff; /* 设置浅蓝色背景 */
      border-radius: 8px; /* 设置圆角 */
    }

    .movie-item {
      display: flex;
      align-items: center;
      background-color: #ffffff;
      border-radius: 8px;
      box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
      margin-bottom: 10px;
      padding: 10px;
      transition: transform 0.3s ease, box-shadow 0.3s ease;
      cursor: pointer;
      border: 1px solid #91d5ff; /* 添加浅蓝色边框 */
    }

    .movie-item:hover {
      transform: translateY(-5px);
      box-shadow: 0 4px 16px rgba(0, 0, 0, 0.2);
    }

    .movie-img {
      width: 100px;
      height: auto;
      border-radius: 8px;
      margin-right: 15px;
    }

    .movie-info {
      flex: 1;
    }

    .movie-info h3 {
      font-size: 1.2em;
      margin: 0;
      color: #1890ff; /* 设置深蓝色字体 */
    }

    .movie-info h4 {
      font-size: 1em;
      margin: 5px 0;
      color: #40a9ff; /* 设置较浅的蓝色字体 */
    }

    .movie-info span {
      font-size: 0.9em;
      color: #69c0ff; /* 设置更浅的蓝色字体 */
    }

    .movie-duration {
      margin-top: 5px;
      font-size: 0.9em;
      color: #40a9ff; /* 设置较浅的蓝色字体 */
    }


    .fare{
      height: 15vh;
    }

  }
  .seat{
    display: flex;
    align-content: center;
    justify-content: center;
    margin-left: 10px;
    width: 100%;
    height: auto;
    background-color: rgba(213, 233, 255, 0.94);

  }
}
.button-group-fare{
  justify-content: center;
  align-items: center;
  padding: 3px;
  cursor: pointer;
  &.selected{

    transform: scale(1.1);
    transition: transform 0.3s ease; /* 添加过渡效果 */
  }
}
.fare-name{
  font-size: 12px;
}
.fare-price{
  font-size: 16px;
  font-weight: bold;
  color: #36cdfc;
}

.scrollbar-demo-item {
  display: flex;
  height: 100px;
  margin: 10px;
  text-align: center;
  border-radius: 4px;
  background: var(--el-color-primary-light-9);
  color: var(--el-color-primary);
}



</style>