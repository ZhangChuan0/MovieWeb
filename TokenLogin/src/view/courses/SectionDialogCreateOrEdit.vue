<script setup>
import {defineProps, reactive, ref,defineEmits} from 'vue'
import {saveCourseTheme,getSectionById} from "@/api/courses.js";
const dialogFormVisible = ref(false)
const formLabelWidth = '140px'



const initData = ()=>({
  id:undefined,
  courseName : "",
  sectionName:"",
  description:"",
  orderNum:0,
  status:0,
  code:""
})

const form = ref(initData())


const title = ref("")
const showAndInit = async (course,courseId)=>{
  form.value = initData() //呼出弹窗后马上重置

  if (courseId?.index) {
    title.value = "更新"
    form.value.sectionName = courseId.name
    form.value.description = courseId.code
    form.value.code = courseId.index

  }else {
    form.value.id = course.id
    title.value = "新建"
  }
  form.value.courseName = course.name


  dialogFormVisible.value = true

  console.log("前台初始化",form.value)
}
const props = defineProps({
  callback:Function
})

const emit = defineEmits()
const dialog = ()=>{
  console.log("do it")
}

emit("send",dialog)

// 提交  修改
const onSaveCourseTheme = async ()=>{
  let res = null
  if (title.value === "新建") {
      res = await saveCourseTheme(form.value).finally(() => {
      dialogFormVisible.value = false
    })
  }else if (title.value === "更新") {
      res = await getSectionById(form.value.code,form.value.sectionName).finally(()=>{
      dialogFormVisible.value = false
    })

  }

  await props.callback()
  console.log("get back for service ",res)
}

defineExpose({
  showAndInit,
})

</script>

<template>
<!--点击对话框之外或者esc dialog不消失-->
  <el-dialog v-model="dialogFormVisible" :title="title+'章节'" width="500" :close-on-click-modal="false" :close-on-press-escape="false">

    <el-form :model="form" label-position="left">

      <el-form-item label="课程名称" :label-width="formLabelWidth">
        <el-input v-model="form.courseName" autocomplete="off" disabled/>
      </el-form-item>

      <el-form-item label="章节名称" :label-width="formLabelWidth">
        <el-input v-model="form.sectionName" autocomplete="off" />
      </el-form-item>

      <el-form-item label="章节描述" :label-width="formLabelWidth">
        <el-input v-model="form.description" autocomplete="off" />
      </el-form-item>

      <el-form-item label="章节排序" :label-width="formLabelWidth"  >
        <el-input-number v-model="form.orderNum" autocomplete="off" controls-position="right" :min="1" :max="10" :step="1"/>
      </el-form-item>

    </el-form>

    <template #footer>
      <div class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取消</el-button>
        <el-button type="primary"  @click="onSaveCourseTheme">
          确定
        </el-button>
      </div>
    </template>
  </el-dialog>

</template>

<style scoped lang="scss">

</style>