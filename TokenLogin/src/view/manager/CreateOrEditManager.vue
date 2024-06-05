<script setup>
import {useRouter} from "vue-router";
import {Clock, Document, Edit, Picture, UploadFilled} from "@element-plus/icons-vue";
import ImageUpload from "@/view/courses/ImageUpload.vue";
import {queryResult,managerQuery} from "@/composables/useManager.js";
import TextEditor from "@/view/courses/TextEditor.vue";
import {saveOrEditManager,getManagerById} from "@/api/manager.js";
import {ElMessage} from "element-plus";
import {onMounted} from "vue";
import RemoveManagerDialog from "@/view/manager/RemoveManagerDialog.vue";
import {userInfo} from "@/composables/useLogin.js";

managerQuery();

const router = useRouter()

const props = defineProps({
  managerId:{
    type:String,
    default:''
  }
}
)

//查询固定员工



onMounted( async ()=>  {
  if (props.managerId){
    const {data} = await getManagerById(props.managerId)
    if (data.code === "000000"){
      form.value = data.data
      console.log("data of manager",form.value.data_of_birth)
    }

  }

})


// 页头的处理
const title = props.managerId ? '编辑员工' : '创建员工'


// 步骤条当前位置
const currentStep = ref(0)


// 表单数据
const form = ref({
  // 员工基本信息
  name: "",
  gender: '男',
  portrait: 'https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png',
  data_of_birth:"",
  // 职位信息
  position:'',
  salary_info:3150,
  phone:'',
  password:'',
//   附加
  note:"写点什么吧…………"
})

// 临时密码
const TemporaryPW = ref('')

// 提交表单
const onSubmitManagerInfo = async() => {

  // console.log(form.value.data_of_birth)
  if (props.managerId){
    Object.assign(form.value,{id:props.managerId})
  }

  if (form.value.name !== '' && form.value.position !== '' && form.value.phone !== '' && form.value.password !== ''){

    const {data} = await saveOrEditManager(form.value)
    if (data.code === "000000"){
      ElMessage.success(data.data)
      if(userInfo.value.role === "admin"){
        await router.push('/manager')
      }else {
        await router.push({name:"index"})
      }


    }
  }

}

// 格式化时间
const handleDate = (item)=>{
  form.value.data_of_birth = item.toLocaleDateString()

}
// 移除用户

const removeManager = async ()=>{
  console.log("Remove ")

}


//删除弹窗

const removeManagerDialog =ref()
</script>

<template>
  <el-page-header @back="router.go(-1)">
    <template #content>
      <span class="text-large font-600 mr-3">{{title}}</span>
    </template>


  </el-page-header>
<!--  表单-->
  <el-card style="width: auto" >
    <template #header>
      <el-steps
          class="mb-4"
          style="width: auto"
          :space="100"
          :active="currentStep"
          simple
      >
        <el-step title="个人信息" :icon="Edit"   @click="currentStep = 0"/>
        <el-step title="职位信息" :icon="UploadFilled"   @click="currentStep = 1"/>
        <el-step title="备注" :icon="Picture"  @click="currentStep = 2"/>
      </el-steps>

    </template>


    <el-form :model="form" style="width: auto" >

      <div v-show="currentStep === 0" style="margin: 20px;display: flex;justify-content: space-around">
        <div style="padding: 50px">
<!--          写一个规则此处必填-->
      <el-form-item class="form-item-basic-info" label="姓名" :rules="[{ required: true, message: '请输入姓名', trigger: 'blur' }]">
        <el-input v-model="form.name"  clearable/>
      </el-form-item>

      <el-form-item class="form-item-basic-info" label="性别" >
        <el-select v-model="form.gender" placeholder="选择性别">
          <el-option label="男" value="男" />
          <el-option label="女" value="女" />
        </el-select>
      </el-form-item>
      </div>


        <div>
        <el-form-item label="头像">
          <ImageUpload v-model="form.portrait"/>
        </el-form-item >
      <el-form-item label="生日">
        <el-col :span="11">
          <el-date-picker
              v-model="form.data_of_birth"
              type="date"
              placeholder="选择时间"
              date-format="YYYY/MM/DD ddd"
              style="width: 100%"
              @change="handleDate"
          />
        </el-col>
      </el-form-item>
      </div>

      </div>

      <div v-show=" userInfo.role === 'admin'">
      <div style="display: flex;justify-content: space-around" v-show="currentStep === 1">

      <div>
      <el-form-item label="岗位定级" :rules="[{ required: true, message: '请选择岗位', trigger: 'change' }]">
        <el-select v-model="form.position" placeholder="选择岗位" style="width: 240px"  >
          <el-option v-for="position in queryResult.data" :key="position.id" :label="position.name" :value="position.name"/>
        </el-select>
      </el-form-item>
        <el-form-item label="工资" >
          <el-input-number v-model="form.salary_info" :min="3150" :max="10000" :step="50" style="width: 240px"/>
        </el-form-item>
      </div>

        <div>
      <el-form-item label="电话" style="width: 500px" :rules="[{ required: true, trigger: 'blur'}]">
        <el-input v-model="form.phone"/>
      </el-form-item>
        <el-form-item label="自定义密码" style="width: 500px" :rules="[{ required: true, message: '请输入密码', trigger: 'blur' }]">
          <el-input v-model="form.password" placeholder="输入密码"/>
          <el-input v-model="TemporaryPW" placeholder="再次输入"/>
        </el-form-item>
      </div>

      </div>
      </div>


      <div v-show="currentStep === 2">

        <el-form-item  label="爱好"  style="width: 90%;padding: 30px">
          <TextEditor v-model="form.note"/>
        </el-form-item>
      </div>

    </el-form>


        <div v-show="currentStep === 0">

        </div>


    <div class="form-bottom-btn">
      <!--      按钮控制步骤条显示-->
      <el-button v-show="currentStep !== 0 "  @click="currentStep--">上一步</el-button>
      <el-button  v-show="currentStep !== 2 "  @click="currentStep++">下一步</el-button>
      <el-button  v-show="currentStep === 2 " type="primary" @click="onSubmitManagerInfo">提交</el-button>
      <div v-show=" userInfo.role === 'admin'">
        <el-button v-show="props.managerId && currentStep === 2" type="danger" @click="removeManagerDialog.initAndShow()">注销</el-button>
      </div>
    </div>

  </el-card>

<RemoveManagerDialog ref="removeManagerDialog" :manager-id="props.managerId"/>
</template>

<style scoped lang="scss">

.form-bottom-btn{
  display: flex;
  justify-content: center;
}
.form-item-basic-info{
  width: 200px;
  margin-bottom: 100px;
}

</style>