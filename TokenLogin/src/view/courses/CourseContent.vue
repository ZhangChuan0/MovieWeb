<script setup>
import {ref,watch,onMounted,defineProps} from "vue";
import {course,courseSAL,loadSAL} from "@/composables/useCourseContent.js";
import {useRouter} from "vue-router";
import {ElNotification as notify} from 'element-plus'
import {Clock, Edit, Picture, UploadFilled, Document, Plus} from '@element-plus/icons-vue'
import SectionDialogCreateOrEdit from "@/view/courses/SectionDialogCreateOrEdit.vue";
import UploadVideoImageDialog from "@/view/courses/UploadVideoImageDialog.vue";
const router = useRouter()
//得到路由地址
const props = defineProps({
  courseId:{
    type:String,
    default:''
  }
})

loadSAL(props.courseId)


// 点击树状图的反应
const handleNodeClick = (data,node) => {
  console.log(node.level)

}



const defaultProps = {
  children: 'records',
  label: 'name'  //可以用一个和函数来解决后端传过来的名字不同
}
const callback = ()=>{
  loadSAL(props.courseId)
}

const dialogFather = (dialog)=>{
  dialog() //接收父组件发送的方法
}

// 判断拖拽是否符合要求


const drop =(draggingNode,dropNode,type)=>{
  return type !== 'inner' && draggingNode.parent === dropNode.parent  //禁止内部叠加，重复叠加等等

}

const uploadDlg = ref()
const sectionDialogCreateOrEdit = ref()
</script>
<template>
  <el-page-header @back="router.go(-1),notify({title:'提示',type:'success',message:'返回成功'})">
    <template #content>
      <span class="text-large font-600 mr-3"> {{ course.name }} </span>
    </template>

    <template #extra>
      <div class="flex items-center">

        <el-button type="primary" :icon="Plus" class="ml-2" @click="sectionDialogCreateOrEdit.showAndInit(course)">增加章节</el-button>
      </div>
    </template>

  </el-page-header>
  <el-card style="width: auto">
    <el-tree
        style="width: auto"
        :data="courseSAL"
        :props="defaultProps"
        @node-click="handleNodeClick"
        draggable
        :allow-drop="drop"
    >
      <template #default="{ node, data }">
        <span class="custom-tree-node">
          <span >{{ node.label }}</span>

          <span v-if="node.level === 1">
            <el-button type="primary">添加</el-button>
          </span>

           <span v-else-if="node.level === 2">
            <el-button @click="sectionDialogCreateOrEdit.showAndInit(course,node.data)">编辑</el-button>
            <el-button size="large" type="success" @click="uploadDlg.showAndInit(course,node.data)">上传视频</el-button>
          </span>
        </span>
      </template>

    </el-tree>
<SectionDialogCreateOrEdit  ref="sectionDialogCreateOrEdit" @send="dialogFather"  :callback="callback"/>
    <UploadVideoImageDialog  ref="uploadDlg"/>
  </el-card>
</template>
<style >
.el-page-header{
 margin-bottom: 18px;
}

.el-tree-node{
  padding: 15px 0;
  border-bottom: 1px solid black;
  border-left: #8c939d 1px solid;
  font-size: 15px;
}
.custom-tree-node{
  flex: 1;
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding-right:8px;
}
</style>