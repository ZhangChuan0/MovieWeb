<script setup>
import {use} from 'echarts/core';
import {
  TitleComponent,
  ToolboxComponent,
  TooltipComponent,
  LegendComponent
} from 'echarts/components';
import { FunnelChart } from 'echarts/charts';
import { CanvasRenderer } from 'echarts/renderers';
import VChart, {THEME_KEY} from "vue-echarts";
import {provide} from "vue";
import {getOrderAllInfo} from "@/api/sales.js";

use([
  TitleComponent,
  ToolboxComponent,
  TooltipComponent,
  LegendComponent,
  FunnelChart,
  CanvasRenderer
]);

provide(THEME_KEY, "light");


const dataList = ref([])
const fetchData = async () => {
  const { data } = await getOrderAllInfo();

  // 创建一个对象用于统计每个电影的总销售额
  const movieSales = {};

  // 遍历订单数据
  data.records.forEach((order) => {
    // 只统计商品类型为 movie 的项
    if (order.item_type === 'movie') {
      const movieName = order.item_name;
      const totalAmount = parseInt(order.totalAmount);

      // 如果该电影名已经存在于 movieSales 中，则累加销售额，否则将其添加到 movieSales 中
      if (movieSales[movieName]) {
        movieSales[movieName] += totalAmount;
      } else {
        movieSales[movieName] = totalAmount;
      }
    }
  });

  // 将 movieSales 转换为所需的格式
  // 返回处理好的数据
  dataList.value =  Object.keys(movieSales).map((movieName) => ({
    name: movieName,
    value: movieSales[movieName]
  }));

};

fetchData()

watch(dataList, (newOrderData) => {
  // 更新 legend 的 data
  option.value.legend.data = newOrderData.map((item) => item.name);

  // 更新 series 的 data
  option.value.series[0].data = newOrderData;
});





const option =ref({
  tooltip: {
    trigger: 'item',
    formatter: '{a} <br/>{b} : {c}%'
  },
  legend: {
    data: dataList.value.map((item) => item.name)
  },
  series: [
    {
      name:"电影",
      type: 'funnel',
      left: '10%',
      top: 60,
      bottom: 60,
      width: '80%',
      min: 0,
      max: 1000,
      minSize: '0%',
      maxSize: '100%',
      sort: 'descending',
      gap: 2,
      label: {
        show: true,
        position: 'inside'
      },
      labelLine: {
        length: 10,
        lineStyle: {
          width: 1,
          type: 'solid'
        }
      },
      itemStyle: {
        borderColor: '#fff',
        borderWidth: 1
      },
      emphasis: {
        label: {
          fontSize: 20
        }
      },
      data: dataList.value
    }
  ]
})

</script>

<template>
  <h1>电影销售排行列表</h1>
  <v-chart class="chart" :option="option" />
</template>
<style scoped>
.chart {
  height: 70vh;
}
</style>