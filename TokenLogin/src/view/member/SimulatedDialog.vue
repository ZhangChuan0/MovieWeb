<script setup>
import { ref } from 'vue'
import {form} from "@/composables/useMember.js";
import {resetPassword} from "@/api/member.js";
import {ElMessage} from "element-plus";

const dialogVisible = ref(false)

const props = defineProps({
  type: {
    type: String,
    default: ""
  }
})

const initAndShow= ()=>{
  dialogVisible.value = true;

}

defineExpose({
  initAndShow
})

const content = ref("")

content.value = props.type === "6" ? "充值成功，请点击确认返回" : "点击确认按钮，重置密码"

//  确定按钮
const onSubmitOperation =async ()=>{

  console.log(form.value)

  if ( props.type === "4"){
    const {data} = await resetPassword(form.value)
   if (data.code === "000000"){
     ElMessage.success("重置成功")
   }else {
     ElMessage.error("failed")
   }

  }

  if (props.type === "6"){
    ElMessage.success("充值成功")
  }



  dialogVisible.value = false;
}

</script>

<template>
  <el-dialog
      v-model="dialogVisible"
      title="提示"
      width="500"
  >
    <span>{{content}}</span>
    <template #footer>
      <div class="dialog-footer">
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="onSubmitOperation()">
          确定
        </el-button>
      </div>
    </template>
  </el-dialog>

</template>

<style scoped lang="scss">

</style>