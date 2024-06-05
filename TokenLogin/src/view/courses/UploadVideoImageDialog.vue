<script setup>
import {ref} from "vue"
import {uploadVideo} from "@/api/uploadVideo.js";
import {UploadFilled, VideoPlay} from "@element-plus/icons-vue";
const dialogVisible = ref(false)


const lessonId = ref(0)
// 初始化对话框
const showAndInit = (course,courseId)=>{
  dialogVisible.value =true
  lessonId.value = courseId.index
}

defineExpose({
  showAndInit
})

const realUrl = ref()
const successFunc = (response,uploadFile)=>{
  console.log("after video upload ",response)
  realUrl.value= response

}

// 选择视频临时保存
const videoFile  = ref()
const uploadVideTip=ref()
// 上传到前端
const handleBeforeUploadVideo = (file)=>{
  videoFile.value = file
  uploadVideTip.value = file.name
  // return false;
}


// 视频上传进度
const percentage = ref(0)
const handleProgress = (evt, file, fileList) => {
  console.log("上传进度",evt)
  percentage.value = Math.floor(evt.percent)

}

// 上传
const handleUpload = async ()=>{
//   添加文件 && 上传
  if (videoFile.value !== undefined && videoFile.value !== null) {
    const res = await uploadVideo(videoFile.value)
    console.log("back from service",res.data)

  }

}
</script>

<template>
  <el-dialog v-model="dialogVisible" title="上传视频" width="40%" >
    <div class="upload-body">
    <el-upload
        class="upload-demo"
        action="http://127.0.0.1:8080/boss/video/upload"
        multiple
        :on-success="successFunc"
        :before-upload="handleBeforeUploadVideo"
        :on-progress="handleProgress"
        accept="video/*"
    >
      <el-button type="primary">选择视频</el-button>
      <template #tip>
        <div class="el-upload__tip">
        </div>
      </template>
    </el-upload>

      <div v-if="realUrl">
        <video :src="realUrl" controls style="width: 30%"></video>
      </div>

      <div class="dia-btm">
        <el-button @click="handleUpload" type="primary"><el-icon><UploadFilled /> </el-icon>上传</el-button>
        <el-button @click="() =>{videoFile = undefined ;percentage = 0;realUrl=''} ">清除</el-button>
      </div>


    <h4>视频上传进度：{{percentage}}</h4>
    </div>
  </el-dialog>


</template>

<style scoped lang="scss">
.el-dialog{
  .upload-demo{
    height: 90px;
  }
h4{
  margin: 20px 10px;
}

  .upload-body{
    padding-left: 30px;


  }


  .dia-btm{
    display: flex;
    justify-content: space-around;

  }
}


</style>