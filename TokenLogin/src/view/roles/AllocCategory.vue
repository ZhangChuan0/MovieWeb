<script setup>
import { ref,defineProps } from 'vue'

// 接收父组件的数据
const props = defineProps({
  category:''
})

//声明触发的事件---发往父组件
const emit = defineEmits()


const checkAll = ref(props.category.selected)

const checkedIds = ref([])  //选中项
const allItemIsd = []  //所有项

const isIndeterminate = ref(
    // 初始化半选状态
    checkedIds.value.length > 0 && checkedIds.value.length < allItemIsd.length
)
// 遍历每一项的结果

props.category.records?.forEach((resource)=>{
  allItemIsd.push(resource.index)
  resource.selected && checkedIds.value.push(resource.index)
})


// 在setup时，就发送数据 消息方法，消息本体
emit("postCheckIdsRef",checkedIds.value)



const handleCheckAllChange = (val) => {
  checkedIds.value = val ? allItemIsd : []

  isIndeterminate.value = false  // 当全选时，取消半选状态
}
const handleCheckedCitiesChange = (value) => {
  const checkedCount = value.length
  checkedIds.value = value
  checkAll.value = checkedCount === allItemIsd.length
  isIndeterminate.value = checkedCount > 0 && checkedCount < allItemIsd.length
}



</script>

<template>

  <el-card class="box-card">
    <template #header>
      <div class="card-header">

        <el-checkbox v-model="checkAll" :indeterminate="isIndeterminate" @change="handleCheckAllChange">
        {{category.name}}
        </el-checkbox>

      </div>

    </template>

    <el-checkbox-group v-model="checkedIds" @change="handleCheckedCitiesChange">
      <el-row>
        <el-col :span="8" v-for=" resource in category.records" :key="resource.index">
<!--          每一行只有三个一换行，每个占8个格子 3 X 8 = 24 -->
          <el-checkbox  :label="resource.name" :value="resource.index"/>
        </el-col>
      </el-row>


    </el-checkbox-group>

  </el-card>
</template>

<style scoped lang="scss">
.el-card{
  margin-bottom: 20px;


}
.card-header{
  display: flex;
  justify-content: space-between;
  align-content: center;

}
.text{
  font-size: 15px;
}
.box-card{
  width: auto;
}
</style>