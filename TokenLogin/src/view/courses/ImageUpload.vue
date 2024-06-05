<script setup>
import {Clock, Edit, Picture, UploadFilled, Document, Plus} from '@element-plus/icons-vue'
import {ElMessage, ElNotification as notify} from 'element-plus'
import {watchEffect,ref,watch} from "vue";

// 声明属性消息接收
const props = defineProps({

  modelValue:{
    type:String,
    default:''
  }

})

// 声明事件消息--发送im
const emit = defineEmits()
// 保存图片给前端显示的临时地址
const imageUrl = ref("")

watchEffect(()=>{  //自动监听属性值变化
  imageUrl.value = props.modelValue
  // console.log("imageUrl.value = "+imageUrl.value)
})


// 提交照片之前本地检查
const beforeAvatarUpload = (rawFile) => {
  isShowPercentage.value = true
  if (rawFile.type !== 'image/jpeg') {
    ElMessage.error('Avatar picture must be JPG format!')
    return false
  } else if (rawFile.size / 1024 / 1024 > 2) {
    ElMessage.error('Avatar picture size can not exceed 2MB!')
    return false
  }
  return true
}
// 上传图片成功后
const handleAvatarSuccess= (
    response,
    uploadFile
) => {
  imageUrl.value = URL.createObjectURL(uploadFile.raw)
  if (response.status === "000000"){
    notify({
      title: '成功',
      message: '上传成功',
      type: 'success',
    })
    // course.value.courseListImg = response.name  //保存真实上传到存在服务器的地址
    emit('update:modelValue', response.name)  //发送给父组件
  }

}
// 上传进度条
const percentage = ref(0)


// 是否显示进度条
const isShowPercentage = ref(false)


// 上传图片的进度改变
const handleProgress = (evt, file, fileList) => {
  percentage.value = Math.floor(evt.percent)
}

// 清除按钮
const clearImg = ()=>{
  emit('update:modelValue', "")
}


</script>

<template>

    <el-progress v-show="isShowPercentage" type="circle" :percentage="percentage" :width="250"/>

    <el-upload
        v-show="!isShowPercentage"
        class="avatar-uploader"
        action="http://127.0.0.1:8080/boss/course/upload"
        method="POST"
        :show-file-list="false"
        :on-success="handleAvatarSuccess"
        :before-upload="beforeAvatarUpload"
        :on-progress="handleProgress"
        drag
    >
      <img v-if="imageUrl" :src="imageUrl" class="avatar" :style="{ width: '100%' }" alt="图片显示失败" @load="isShowPercentage = false"/><!--图片加载好之后就隐藏进度条-->
      <el-icon v-else class="avatar-uploader-icon"><Plus /></el-icon>
    </el-upload>
  <el-button v-if="imageUrl" type="danger" @click="clearImg">清除</el-button>


</template>


<style scoped lang="scss">
.el-card{
  margin: 5px;
}

.avatar-uploader .avatar {
  width: 178px;
  height: 178px;
  display: block;
}

</style>

<style>
.avatar-uploader .el-upload {
  border: 1px dashed var(--el-border-color);
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
  transition: var(--el-transition-duration-fast);
}

.avatar-uploader .el-upload:hover {
  border-color: var(--el-color-primary);
}

.el-icon.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  text-align: center;
}
</style>