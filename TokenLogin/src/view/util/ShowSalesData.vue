<script setup>
import {provide, ref} from 'vue'
import { ChatLineRound, Male } from '@element-plus/icons-vue'
import LeftTop from "@/view/util/packages/LeftTop.vue";
import LeftCenter from "@/view/util/packages/LeftCenter.vue";
import RightTop from "@/view/util/packages/RightTop.vue";
import RightBottom from "@/view/util/packages/RightBottom.vue";
import {queryResult,queryCourses} from "@/composables/useCourses.js"
import VChart, {THEME_KEY} from "vue-echarts";
import {use} from 'echarts/core';
import { TitleComponent, LegendComponent } from 'echarts/components';
import { RadarChart } from 'echarts/charts';
import { CanvasRenderer } from 'echarts/renderers';
import {getMovie, dataOfMovie, handleName, result, membersCount, activeName} from "@/composables/useShowSalesData.js";
import {updateBoxOffice} from "@/api/sales.js";

use([TitleComponent, LegendComponent, RadarChart, CanvasRenderer]);

getMovie()

// 获取电影
queryCourses({page:1})


updateBoxOffice()

const handleClick = (event) => {
  const courseName = event.target.innerText; // 获取被点击选项卡的文本内容，即课程名称

  handleName(courseName)
}

provide(THEME_KEY, "light");


const legendData = dataOfMovie.value.map(movie => movie.name);


const option =ref ({
  title: {
    text: '电影分析'
  },
  legend: {
    data: legendData
  },
  radar: {
    // shape: 'circle',
    indicator: [
      { name: '时长', max: 300,min:0 },
      { name: '票房', max: 10000,min:0 },
      { name: '价格', max: 100 ,min:0},
      { name: '座位', max: 77 ,min:0},
      {name:  "周边", max:500,min:0},

    ]
  },
  series: [
    {
      name: 'Budget vs spending',
      type: 'radar',
      data: dataOfMovie.value.map(movie => ({
        value: [
          movie.value[0], // 时长
          movie.value[1], // 票房
          movie.value[2], // 价格
          movie.value[3],  // 座位
          movie.value[4]  // 销售
        ],
        name: movie.name
      }))
    }
  ]
})

</script>

<template>
  <div class="data-dashboard">
    <div class="header">
      <h1>运营数据展示板</h1>
    </div>
    <div class="main-content">
      <div class="left-panel">
<!--        左上-->
        <div class="chart-area">
          <LeftTop/>
        </div>
<!--        左中-->
        <div class="pie-chart-area">
          <LeftCenter/>
        </div>

      </div>
<!--      中间-->
      <div class="center-panel">
        <div class="main-metrics">
          <el-tabs
              type="card"
              v-model="activeName"
              @click="handleClick($event)
">
            <el-tab-pane v-for="movie in queryResult.records" :label="movie.courseName" :key="movie.id" :name="movie.courseName"/>

          </el-tabs>
        </div>
        <div class="conversion-funnel">
          <el-row>
            <el-col :span="6">
              <el-statistic title="总票房" :value="result.boxOffice" />
            </el-col>
            <el-col :span="6">
              <el-statistic :value="result.seatCount">
                <template #title>
                  <div style="display: inline-flex; align-items: center">
                    占座
                    <el-icon style="margin-left: 4px" :size="22">
                      <Male />
                    </el-icon>
                  </div>
                </template>
                <template #suffix>/77</template>
              </el-statistic>
            </el-col>
            <el-col :span="6">
              <el-statistic title="附带周边" :value="562">
                <template #suffix>
                  <el-icon style="vertical-align: -0.125em">
                    <ChatLineRound />
                  </el-icon>
                </template>
              </el-statistic>
            </el-col>
          </el-row>
          <v-chart class="chart" :option="option" />
        </div>
        <div class="product-metrics">

          <el-table :data="queryResult.records" style="width: 100%">
            <el-table-column type="index" label="序号" width="auto" />
            <el-table-column prop="courseName" label="电影名" width="180" />
            <el-table-column prop="teacherName" label="导演" />
            <el-table-column prop="teacherDescription" label="主演" />
            <el-table-column prop="teacherPosition" label="语言" />
            <el-table-column prop="activityStock" label="当前票房" />
          </el-table>

        </div>

      </div>
      <div class="right-panel">
<!--        右上-->
        <div class="gender-stats">
          <RightTop />
        </div>

<!--        右下-->
        <div class="regional-stats">
          <RightBottom/>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped lang="scss">
.data-dashboard {
  display: flex;
  flex-direction: column;
  height: 100vh;
  font-family: Arial, sans-serif;
}

.header {
  background-color: #3f51b5;
  color: white;
  padding: 16px;
  text-align: center;
}

.main-content {
  display: flex;
  flex: 1;
  padding: 16px;
}

.left-panel {
  flex: 1;
  display: flex;
  flex-direction: column;
  gap: 16px;
}

.center-panel {
  flex: 2;
  display: flex;
  flex-direction: column;
  gap: 16px;
  margin: 0 16px;
}

.right-panel {
  flex: 1;
  display: flex;
  flex-direction: column;
  gap: 16px;
}

.chart-area,
.pie-chart-area,
.main-metrics,
.conversion-funnel,
.product-metrics,
.gender-stats,
.age-stats,
.regional-stats {
  background-color: white;
  border: 1px solid #ccc;
  border-radius: 4px;
  padding: 16px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}
.chart {
  height: 400px;
}



</style>