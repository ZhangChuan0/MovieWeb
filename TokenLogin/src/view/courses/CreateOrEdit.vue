<script setup>
import {Clock, Edit, Picture, UploadFilled, Document, Plus} from '@element-plus/icons-vue'
import {ElMessage, ElNotification as notify} from 'element-plus'
import ImageUpload from "@/view/courses/ImageUpload.vue";
import {ref,watch,onMounted,defineProps} from "vue";
import TextEditor from "@/view/courses/TextEditor.vue";
import {saveUpdateCourse, getCourseById01, getCourse_largeById} from "@/api/courses.js";
import {useRouter} from "vue-router";
import {getLanguageList, getRoomList, resultLanguage,resultRoom} from "@/composables/useSet.js";

const router = useRouter()
//得到路由地址
const props = defineProps({
  courseId:{
    type:String,
    default:''
  }
})
// 页面加载时判断
onMounted(async () => {

  if (props.courseId) {



    const res = await getCourse_largeById(props.courseId)
    console.log("back data:",course.value)
    if (res.data.code === "000000"){
      course.value = res.data.data

      // 处理时间
        timeRange.value = [
          new Date(course.value.activityBeginTime),
          new Date(course.value.activityEndTime)
        ]
      course.value.activityCourse = course.value.activityCourse !== "false";

    }


  }


})

// 步骤条当前位置
const currentStep = ref(0)
// 表单数据
const course = ref({
  // 电影信息
  courseName:"wu", //电影名称
  brief:"ENABLE", //3D效果
  previewFirstField:'11',//电影类型1
  previewSecondField:'11',//电影类型2
  sortNum:"A", //分配影厅
//   电影封面信息
  courseListImg:'https://cube.elemecdn.com/9/c2/f0ee8a3c7c9638a54940382568c9dpng.png',  //封面
  courseImgUrl:'https://cube.elemecdn.com/9/c2/f0ee8a3c7c9638a54940382568c9dpng.png',   //海报
  // 影票信息
  discounts:49, //普通票价格
  price:0,  //团购价
  sales:0,  //上映年份
  discountTag:"ENABLE", //是否上架
//   秒杀活动
  activityCourse:"ENABLE", //限制时间上映
  //   课程详情
  status:1,//上架与否
  courseDescriptionMarkDown:"反馈",//概述
      activityBeginTime:"2012-01-01", //开始时间
      activityEndTime:"2012-02-02", //结束时间
      activityAmount:0,// 限时团购价
      activityStock:0,//总票房
      teacherName: "11", //导演
      teacherPosition: "中文",//语言
      teacherDescription: "11",//主演
})

// 秒杀活动的时间范围  以及监听上变化，同时自动化赋值
const timeRange = ref([])
watch(timeRange,(newValue)=>{
  if (Array.isArray(newValue)){
    course.value.activityBeginTime = newValue[0].toLocaleDateString()
   course.value.activityEndTime= newValue[1].toLocaleDateString()
  }else {
    course.value.activityBeginTime = ""
    course.value.activityEndTime= ""
  }


})

const onSubmitLargeFrom = async ()=>{

  const info = props.courseId? "更新影片" : "创建影片"
  if(props.courseId){
    Object.assign(course.value,{id:props.courseId})
  }

  console.log(course.value);
  const {data} = await saveUpdateCourse(course.value)
  if (data.code === "000000"){
    ElMessage.success(info+"成功")
    await router.push({name: "courses"})
  }


// 弃用
/*

  const res = await saveUpdateCourse(course.value)
  console.log("form back Service",res.data)
  if (res.data.code === '000000'){
    ElMessage.success(info + '成功')
    await router.push({name: 'courses'})
  }else {
    ElMessage.error(info + '失败')
  }

*/

}

// 时间改变
const handleTiemChange = (event)=>{

}


// 语言

getLanguageList()

// 影厅
getRoomList()
</script>

<template>
  <el-page-header @back="()=>{$router.go(-1),notify({title:'提示',type:'success',message:'返回成功'})}">
    <template #content>
      <div class="flex items-center">
    <!--通过有没有值来判断应该放什么在这儿-->
        <span class="text-large font-600 mr-3"> {{ props.courseId ? '编辑':'新建'}} </span>

      </div>
    </template>
<!--右边的按钮部分-->
    <template #extra>
      <div class="flex items-center">
        <el-button type="primary" class="ml-2" @click="onSubmitLargeFrom">保存</el-button>
      </div>

    </template>

    <el-card style="width: auto">
<!--      步骤条条-->
      <template #header>
          <el-steps
              class="mb-4"
              style="width: auto"
              :space="100"
              :active="currentStep"
              simple
          >
            <el-step title="基本信息" :icon="Edit"   @click="currentStep = 0"/>
            <el-step title="电影封面信息" :icon="UploadFilled"   @click="currentStep = 1"/>
            <el-step title="价格设置" :icon="Picture"  @click="currentStep = 2"/>
            <el-step title="限时上映" :icon="Clock"    @click="currentStep = 3"/>
            <el-step title="详情" :icon="Document"    @click="currentStep = 4"/>
          </el-steps>
      </template>
<!--巨大表单-->
      <el-form label-width="120" size="large" :model="course">
        <div v-show="currentStep === 0">

          <el-form-item label="电影名称"  prop="courseName" :rules="[{ required: true, message: '请输入电影名称', trigger: 'blur' }]">
            <el-input v-model="course.courseName" maxlength="50" show-word-limit/>
          </el-form-item>

          <el-form-item label="导演"  prop="teacherName"  :rules="[{ required: true, message: '请输入导演名称', trigger: 'blur' }]">
            <el-input v-model="course.teacherName"/>
          </el-form-item>

          <el-form-item label="3D效果">
            <el-switch
                v-model="course.brief"
                class="ml-2"
                active-value="ENABLE"
                inactive-value="DISABLE"
                style="--el-switch-on-color: #13ce66; --el-switch-off-color: #ff4949"
            />
          </el-form-item>

          <el-form-item label="主演">
            <el-input v-model="course.teacherDescription"/>
          </el-form-item>

          <el-form-item label="语言">

            <el-select
                v-model="course.teacherPosition"
                clearable
                placeholder="选择语言"
                style="width: 240px"
            >
             <el-option v-for="item in resultLanguage" :key="item.id" :label="item.name" :value="item.name"/>
            </el-select>

          </el-form-item>

          <el-form-item label="风格">
            <el-input  v-model="course.previewFirstField"  style="flex:1;margin-right: 20px"/>
            <el-input v-model="course.previewSecondField"  style="flex:1;"/>

          </el-form-item>



          <el-form-item label="上映年份">
            <el-input-number v-model="course.sales"/>
          </el-form-item>

        </div>
<!--       展示图片 -->
        <div v-show="currentStep === 1">
          <el-form-item label="封面">
<!--            上传图片的组件-->
            <ImageUpload v-model="course.courseListImg"/>
          </el-form-item>

          <el-form-item label="海报">

            <ImageUpload v-model="course.courseImgUrl"/>

          </el-form-item>

          <el-form-item label="总时长">
            <el-input-number v-model="course.price"/>
          </el-form-item>

        </div>

        <div v-show="currentStep === 2">
          <el-form-item label="普票价格">
            <el-input-number v-model="course.discounts"/>
          </el-form-item>

          <!--只能输入数字-->
          <el-form-item label="分配影厅">
            <el-select
                v-model="course.sortNum"
                clearable
                placeholder="影厅"
                style="width: 240px"
            >
              <el-option v-for="item in resultRoom " :key="item.id" :label="item.name" :value="item.name"/>
            </el-select>
          </el-form-item>


          <el-form-item label="是否上架">
            <el-switch
                v-model="course.discountTag"
                class="ml-2"
                inactive-value="DISABLE"
                active-value="ENABLE"
                style="--el-switch-on-color: #13ce66; --el-switch-off-color: #ff4949"
            />
          </el-form-item>

          <el-form-item label="总票房">
            <el-input-number v-model="course.activityStock" controls-position="right" :min="0"/>
          </el-form-item>

        </div>
<!--        秒杀活动-->
        <div v-show="currentStep === 3">



          <el-form-item label="限时上映">
            <el-switch
                v-model="course.activityCourse"
                class="ml-2"
                style="--el-switch-on-color: #13ce66; --el-switch-off-color: #ff4949"
                active-value="ENABLE"
                inactive-value="DISABLE"
            />
          </el-form-item>

          <template v-if="course.activityCourse">
          <el-form-item label="上映时间">
            <el-date-picker
                v-model="timeRange"
                type="datetimerange"
                start-placeholder="开始时间"
                range-separator="至"
                end-placeholder="结束时间"
                format="YYYY-MM-DD"
                date-format="YYYY/MM/DD ddd"
                @change="handleTiemChange($event)"
            />

          </el-form-item>
          <el-form-item label="限时团购价">
            <el-input-number v-model="course.activityAmount" controls-position="right"  :min="1" :step="0.5"/>
          </el-form-item>



          </template>
        </div>
        <div v-show="currentStep === 4">

          <el-form-item label="影片概述">
            <TextEditor v-model="course.courseDescriptionMarkDown"/>
          </el-form-item>
          <el-form-item label="是否上架">

            <el-switch
                v-model="course.status"
                class="ml-2"
                style="--el-switch-on-color: #13ce66; --el-switch-off-color: #ff4949"
                :active-value="1"
                :inactive-value="0"
            />
          </el-form-item>
        </div>

    <div class="form-bottom-btn">
<!--      按钮控制步骤条显示-->
      <el-button v-show="currentStep !== 0 "  @click="currentStep--">上一步</el-button>
      <el-button  v-show="currentStep !== 4 "  @click="currentStep++">下一步</el-button>
      <el-button  v-show="currentStep === 4 " type="primary" @click="onSubmitLargeFrom">提交</el-button>
    </div>

      </el-form>
    </el-card>

  </el-page-header>

</template>

<style scoped lang="scss">

.avatar-uploader .avatar {
  width: 178px;
  height: 178px;
  display: block;
}

.el-card{
  margin: 5px;
}
.el-step{
  cursor: pointer;  //鼠标接近变成可点击状态
}

.form-bottom-btn{
  display: flex;
  justify-content: center;
}
.el-form{
  padding: 0 100px 0 0;

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