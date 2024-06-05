<script setup>
import { reactive ,computed} from 'vue'
import {
  Iphone,
  Location,
  OfficeBuilding,
  Tickets,
  User,
} from '@element-plus/icons-vue'
import {changeEditPassword, getOneMember, recharge, saveOrUpdate} from "@/api/member.js";
import {ElMessage} from "element-plus";
import {useRouter} from "vue-router";
import {form} from "@/composables/useMember.js";
import SimulatedDialog from "@/view/member/SimulatedDialog.vue";
import DisplayHistoricalData from "@/view/member/DisplayHistoricalData.vue";
import PopPage from "@/view/member/PopPage.vue";


const router = useRouter()
const form_basic = ref({
  name: '',
  gender:"",
  phone:"",
  password:"",

})

const props = defineProps({
  id:{
    type:String,
    required:true,
    default:0
  }
})
const onSubmitForm = async ()=>{

  // 更新
if (form_basic.value.id){
  Object.assign(form_basic.value, {id:form_basic.value.id})
}

  // 保存
  const {data} = await saveOrUpdate(form_basic.value)


  //  重构
  if (data.code === "000000"){
    ElMessage.success("开户成功")
    await router.push({name:"members"})
  }else if (data.code === "222222"){
    ElMessage.success("更新成功")
    await router.push({name:"members"})
  }else if (data.code === "333333"){
    ElMessage.error("用户已经存在了")
  }



}

// 更新
const editMember =async ()=>{
  console.log("传往后端",form.value)

  const {data} = await getOneMember(form.value)
  if (data.code === "000000"){
    form_basic.value = data.records[0]
  }


}



// 显示数据
const showData = ref([])
onMounted(async ()=>{

  if (form.value.name && form.value.phone) {
    const {data} = await getOneMember(form.value)
    showData.value = data.records[0]

  }


})




// 展示
const iconStyle = computed(() => {
  const marginMap = {
    large: '8px',
    default: '6px',
    small: '4px',
  }
  return{
    marginMap
  }
})

// 金额
const chargeBalance =async (value)=>{

  const a = Object.assign({},{id:showData.value.id},{balance:value})
  const {data}  = await recharge(a)
  console.log("反馈",data.code)
  await simulatedDialog.value.initAndShow()


  if (form.value.name && form.value.phone) {
    const {data} = await getOneMember(form.value)
    showData.value = data.records[0]
  }


}


// 模拟充值
const simulatedDialog = ref()

// 充值渠道
const data = ref([
  {label:"99",value:99},
  {label:"119",value:119},
  {label:"159",value:159},
  {label:"189",value:189},
  {label:"219",value:219},
  {label:"259",value:259},
  {label:"299",value:299},
  {label:"500",value:500},
])


// 修改密码

const formInline = reactive({
  old: '',
  new_one:"",
  new_two:""
})

const changePassword =async ()=>{

  if (formInline.new_one !== formInline.new_two){
    ElMessage.error("两次密码不一致")
    return false;
  }

  form.value.password = formInline.new_two
  form.value.id = showData.value.id;

  // 更改
 const {data}= await changeEditPassword(form.value)
if (data.code === "000000"){
  ElMessage.success("更新成功")
  await router.push({name:"members"})

}




}

</script>

<template>
  <el-card style="width: auto;padding: 30px">

  <div v-if="props.id === '0'|| props.id === '2'">
    <el-form :model="form_basic"
             label-width="auto"
             style="max-width: 600px;margin-left: 30%"

      >
    <el-form-item label="姓名" prop="name" :rules="[{ required: true, message: '请输入姓名' }]">
      <el-input v-model="form_basic.name" />
    </el-form-item>
    <el-form-item label="性别">
      <el-radio-group v-model="form_basic.gender">
        <el-radio value="女">女</el-radio>
        <el-radio value="男">男</el-radio>
      </el-radio-group>
    </el-form-item>

    <el-form-item label="密码" v-show="props.id === '0'" prop="password" :rules="[{ required: true, message: '请输入密码' }]">
      <el-col :span="11">
        <el-input v-model="form_basic.password" type="password"/>
      </el-col>
      <el-col :span="2" class="text-center">
        <span class="text-gray-500">重复</span>
      </el-col>
      <el-col :span="11">
        <el-input v-model="form_basic.password" />
      </el-col>
    </el-form-item>
    <el-form-item label="手机号" prop="phone" :rules="[{ required: true, message: '请输入手机号' },{pattern: /^1[3-9}]\d{9}$/, message: '请输入正确的手机号'}]">
      <el-input v-model="form_basic.phone" />
    </el-form-item>

      <el-form-item>
        <el-button type="primary"  @click="onSubmitForm">提交</el-button>
        <el-button v-show="props.id === '2'" @click="editMember">编辑</el-button>
      </el-form-item>
  </el-form>
  </div>
  <div v-if="props.id === '1'">
    <div>
      <h1>余额：{{showData.balance}}</h1>
      <el-descriptions
          class="margin-top"
          :column="4"
          border
      >
        <template #extra>
          <el-button type="primary" @click="router.push({name:'members-operation',params:{id:6}})">充值</el-button>
        </template>


        <el-descriptions-item>
          <template #label>
            <div class="cell-item">
              <el-icon :style="iconStyle">
                <iphone />
              </el-icon>
              编号
            </div>
          </template>
          {{ showData.id}}
        </el-descriptions-item>

        <el-descriptions-item>
          <template #label>
            <div class="cell-item">
              <el-icon :style="iconStyle">
                <user />
              </el-icon>
              姓名
            </div>
          </template>
          {{ showData.name }}
        </el-descriptions-item>


        <el-descriptions-item>
          <template #label>
            <div class="cell-item">
              <el-icon :style="iconStyle">
                <iphone />
              </el-icon>
              手机号
            </div>
          </template>
          {{ showData.phone }}
        </el-descriptions-item>
<!--        性别-->
        <el-descriptions-item>
          <template #label>
            <div class="cell-item">
              <el-icon :style="iconStyle">
                <location />
              </el-icon>
              性别
            </div>
          </template>
          {{ showData.gender }}
        </el-descriptions-item>

      </el-descriptions>



    </div>
  </div>
  <div v-if="props.id === '3'">

    <el-form  :model="formInline" style="display: flex">
      <div>
      <el-form-item label="原始密码" prop="old" :rules="[{ required: true, message: '请输入原始密码', trigger: 'blur' }]">
        <el-input v-model="formInline.old" placeholder="Approved by" clearable />
      </el-form-item>

      <el-form-item label="新密码" prop="new_one" :rules="[{ required: true, message: '请输入新密码', trigger: 'blur' }]">
        <el-input v-model="formInline.new_one" placeholder="Approved by" clearable />
      </el-form-item>

      <el-form-item label="重复" prop="new_two" :rules="[{ required: true, message: '请输入新密码', trigger: 'blur' }]">
        <el-input v-model="formInline.new_two" placeholder="Approved by" clearable />
      </el-form-item>
      </div>

      <el-form-item style="margin-left: 200px">
        <el-button type="primary" @click="changePassword">提交</el-button>
      </el-form-item>
    </el-form>

  </div>
  <div v-if="props.id === '4'">


  </div>
  <div v-if="props.id === '5'">
    <PopPage :form="form"/>
  </div>



  <div v-if="props.id === '6'">

    <div>

      <h1 style="text-align: center;margin: 19px">充值中心</h1>
      <el-row :gutter="20" style="text-align: center">
        <el-col :span="6" v-for="iten in data" :key="iten.label ">
          <el-card style="width: 100px;height: 60px;margin: 20px;font-size: 20px;
          font-family: Courier New;"
                   shadow="hover" @click="chargeBalance(iten.value)">
            <span>{{iten.label}}￥</span>
          </el-card>
        </el-col>
      </el-row>
    <SimulatedDialog :type="props.id" ref="simulatedDialog"/>
    </div>

  </div>


  <div v-if="props.id === '7'">
    <DisplayHistoricalData :form="form"/>
  </div>


  </el-card>
</template>

<style scoped lang="scss">


.el-descriptions {
  margin-top: 20px;
}
.cell-item {
  display: flex;
  align-items: center;
}
.margin-top {
  margin-top: 20px;
}


</style>