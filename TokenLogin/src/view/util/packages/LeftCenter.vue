<script setup>
import { use } from "echarts/core";
import { CanvasRenderer } from "echarts/renderers";
import { PieChart } from "echarts/charts";
import {
  TitleComponent,
  TooltipComponent,
  LegendComponent
} from "echarts/components";
import VChart, { THEME_KEY } from "vue-echarts";
import { ref, provide } from "vue";
import {getOrderAllInfo} from "@/api/sales.js";

use([
  CanvasRenderer,
  PieChart,
  TitleComponent,
  TooltipComponent,
  LegendComponent
]);

provide(THEME_KEY, "light");

const orderData = ref([])

// 获取订单数据并处理
const fetchOrderData = async () => {
  const { data } = await getOrderAllInfo(); // 假设 getOrderAllInfo 返回订单数据

  // 创建一个对象用于统计每个商品的数量
  const itemMap = {};

  // 遍历订单数据
  data.records.forEach((order) => {
    // 只统计商品类型不为 movie 的项
    if (order.item_type !== 'movie') {
      const itemName = order.item_name;
      const itemTotal = parseInt(order.item_total);

      // 如果该商品名已经存在于 itemMap 中，则累加数量，否则将其添加到 itemMap 中
      if (itemMap[itemName]) {
        itemMap[itemName] += itemTotal;
      } else {
        itemMap[itemName] = itemTotal;
      }
    }
  });

  // 将 itemMap 转换为所需的格式
  // 更新 orderData 的值
  orderData.value = Object.keys(itemMap).map((itemName) => ({
    name: itemName,
    value: itemMap[itemName],
  }));



};


fetchOrderData()

watch(orderData, (newOrderData) => {
  // 更新 legend 的 data
  option.value.legend.data = newOrderData.map((item) => item.name);

  // 更新 series 的 data
  option.value.series[0].data = newOrderData;
});

const option = ref({
  title: {
    text: "商品销售",
    left: "center"
  },
  tooltip: {
    trigger: "item",
    formatter: "{a} <br/>{b} : {c} ({d}%)"
  },
  legend: {
    orient: "vertical",
    left: "right",
    data: orderData.value.map((item) => item.name)
  },
  series: [
    {
      name: "商品名",
      type: "pie",
      radius: "55%",
      center: ["50%", "60%"],
      data: orderData.value,
      emphasis: {
        itemStyle: {
          shadowBlur: 10,
          shadowOffsetX: 0,
          shadowColor: "rgba(0, 0, 0, 0.5)"
        }
      }
    }
  ]
});


</script>
<template>
  <v-chart class="chart" :option="option" />
</template>
<style scoped>
.chart {
  height: 100vh;
}
</style>