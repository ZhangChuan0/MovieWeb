<script setup>
import {use} from 'echarts/core';
import { TreemapChart } from 'echarts/charts';
import { CanvasRenderer } from 'echarts/renderers';
import {provide, ref} from "vue";
import VChart, {THEME_KEY} from "vue-echarts";
import {activeName, chooseMember, membersCount} from "@/composables/useShowSalesData.js";

use([TreemapChart, CanvasRenderer]);

provide(THEME_KEY, "light");

// 初始化 option
const option = ref({

  series: [
    {
      type: 'treemap',
      data: [
        {
          name: '会员',
          value: membersCount.value.member, // 使用 membersCount 的值
        },
        {
          name: '非会员',
          value: membersCount.value.custom, // 使用 membersCount 的值
        }
      ]
    }
  ]
})

const chart  = ref()


// 监听 activeName 的变化
watch(activeName, (newValue) => {
  chooseMember(newValue).then(() => {
    // 更新 option 中的 value 属性
    option.value.series[0].data[0].value = membersCount.value.member;
    option.value.series[0].data[1].value = membersCount.value.custom;

    // 通过 ECharts 实例的 setOption 方法更新图表选项
    // 获取 VChart 组件的实例并调用 getEchartsInstance 方法获取 ECharts 实例
    const chartInstance = chart.value.getEchartsInstance();
    chartInstance.setOption(option.value);
  });
});



</script>
<template>
  <h1>会与和非会员</h1>
  <v-chart class="chart" :option="option" ref="chart" />
</template>
<style scoped>
.chart {
  height: 300px;
}
</style>