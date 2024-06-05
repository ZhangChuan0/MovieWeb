<script setup>
import { reactive, ref } from 'vue'
import {useRouter} from "vue-router";
import {checkMember,isSaved,form} from "@/composables/useMember.js";
import {ElMessage} from "element-plus";
import SimulatedDialog from "@/view/member/SimulatedDialog.vue";


const router = useRouter()
const dialogFormVisible = ref(false)
const formLabelWidth = '140px'


const init = ({
  name: '张杰',
  phone: '1008611',
})


const num = ref(0)
const initAndShow= (item)=>{
  dialogFormVisible.value = true;
  form.value = init
  num.value = item
}
//外部弹窗
const simulatedDialog = ref(null)


onMounted(() => {
  simulatedDialog.value = SimulatedDialog // 使用组件的引用来获取其实例
})


const onSure=async (item)=>{

  await checkMember(form.value)

  if(isSaved.value && form.value.name && form.value.phone){

    if (num.value === 4){
       await simulatedDialog.value.initAndShow()
      return false
    }
    await router.push({name: 'members-operation', params: {id: item}})

    dialogFormVisible.value = false
  }else {
    ElMessage.error("请输入正确的信息")
  }


}



defineExpose({
  initAndShow
})
</script>

<template>
  <el-dialog v-model="dialogFormVisible"
             title="校验"
             width="500">
    <el-form :model="form">
      <el-form-item label="会员名" :label-width="formLabelWidth">
        <el-input v-model="form.name" autocomplete="off" />
      </el-form-item>
      <el-form-item label="电话号码" :label-width="formLabelWidth">
        <el-input v-model="form.phone" autocomplete="on" />
      </el-form-item>
    </el-form>


    <template #footer>
      <div class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取消</el-button>
        <el-button type="primary" @click="onSure(num)">
          确定
        </el-button>
      </div>
    </template>

  </el-dialog>

  <SimulatedDialog  :type="num.toString()" ref="simulatedDialog"/>
</template>

<style scoped lang="scss">

</style>