<script setup>
import {use} from 'echarts/core';
import {
  ToolboxComponent,
  TooltipComponent,
  GridComponent,
  LegendComponent
} from 'echarts/components';
import { BarChart, LineChart } from 'echarts/charts';
import { UniversalTransition } from 'echarts/features';
import { CanvasRenderer } from 'echarts/renderers';
import VChart, { THEME_KEY } from "vue-echarts";
import {getOrderAllInfo} from "@/api/sales.js";

use([
  ToolboxComponent,
  TooltipComponent,
  GridComponent,
  LegendComponent,
  BarChart,
  LineChart,
  CanvasRenderer,
  UniversalTransition
]);

provide(THEME_KEY, "light")

const dataList = ref([])

const fetchData = async () => {
  const { data } = await getOrderAllInfo();
  const salesData = {}; // 用于存储每一天的销售数据

  // 遍历订单数据
  data.records.forEach((order) => {
    // 解析 createTime 字段，提取日期的年、月、日部分
    const createDate = order.createTime.split(" ")[0];

    // 初始化当前日期的销售数据
    if (!salesData[createDate]) {
      salesData[createDate] = {
        total: 0, // 总销售额
        movie: 0, // 电影销售额
        nonMovie: 0, // 非电影销售额
      };
    }

    const totalAmount = parseInt(order.totalAmount);
    salesData[createDate].total += totalAmount; // 累加总销售额

    if (order.item_type === "movie") {
      salesData[createDate].movie += totalAmount; // 累加电影销售额
    } else {
      salesData[createDate].nonMovie += totalAmount; // 累加非电影销售额
    }
  });

  // 将销售数据转换为符合格式的列表
  const dateList = Object.keys(salesData); // 日期列表
  const totalSalesList = dateList.map((date) => salesData[date].total); // 总销售额列表
  const movieSalesList = dateList.map((date) => salesData[date].movie); // 电影销售额列表
  const nonMovieSalesList = dateList.map((date) => salesData[date].nonMovie); // 非电影销售额列表

  dataList.value = [dateList,totalSalesList, movieSalesList, nonMovieSalesList];

  console.log("日期data:", dateList);
  console.log("总销售data:", totalSalesList);
  console.log("电影data:", movieSalesList);
  console.log("商品data:", nonMovieSalesList);

};

fetchData();


watch(dataList, (newline) => {
  option.value.xAxis.data = newline[0];
  console.log("time",newline[0])
  option.value.series[0].data = newline[2];
  option.value.series[1].data = newline[3];
  option.value.series[2].data = newline[1];
})






const option = ref({
  tooltip: {
    trigger: 'axis',
    axisPointer: {
      type: 'cross',
      crossStyle: {
        color: '#999'
      }
    }
  },
  toolbox: {
    feature: {
      dataView: { show: true, readOnly: false },
      magicType: { show: true, type: ['line', 'bar'] },
      restore: { show: true },

    }
  },
  legend: {
    data: ['电影', '卖品', '总收入']
  },
  xAxis: [
    {
      type: 'category',
      data: dataList.value[1],
      axisPointer: {
        type: 'shadow'
      }
    }
  ],
  yAxis: [
    {
      type: 'value',
      name: '电影',
      min: 0,
      max: 2000,
      interval: 500,
      axisLabel: {
        formatter: '{value} ￥'
      }
    },
    {
      type: 'value',
      name: '总收入',
      min: 0,
      max: 4000,
      interval: 1000,
      axisLabel: {
        formatter: '{value} ￥'
      }
    }
  ],
  series: [
    {
      name: '电影',
      type: 'bar',
      tooltip: {
        valueFormatter: function (value) {
          return value + ' ￥';
        }
      },
      data:dataList.value[2]
    },
    {
      name: '卖品',
      type: 'bar',
      tooltip: {
        valueFormatter: function (value) {
          return value + ' ￥';
        }
      },
      data:dataList.value[3]
    },
    {
      name: '总收入',
      type: 'line',
      yAxisIndex: 1,
      tooltip: {
        valueFormatter: function (value) {
          return value + ' ￥';
        }
      },
      data:dataList.value[1]
    }
  ]
})

</script>
<template>
  <h1>折线图历史销售数据</h1>
  <v-chart class="chart" :option="option" />
</template>
<style scoped>
.chart {
  height: 300px;
}
</style>