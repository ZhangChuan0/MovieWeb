<script setup>
import {useRouter} from "vue-router";
import {getUserById, removeUserById, saveOrUpdateUser} from "@/api/custom.js";
import { reactive } from 'vue'
import ImageUpload from "@/view/courses/ImageUpload.vue";
import {ElMessage} from "element-plus";
const router = useRouter()
const props = defineProps({
  userId:{
    type:String,
    default:""
  }
})

// do not use same name with ref
const form = ref({
  id:"",
  name:"", //商品名
  password:0,  //库存
  phone:0.0,  //售价
  portrait: 'https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png',  //封面
  regIp:"预制品", //食物类型
  status:"ENABLE", //是否上架
  updateTime:"",
  createTime:""//生产日期
})

const title = ref("")

title.value = props.userId ? "修改商品" : "添加商品"


// 页面加载完成时呈现内容
onMounted(async ()=>{
  if (props.userId){
    const {data} = await getUserById(props.userId)
    if (data.code === "000000"){

      form.value = data.data
       form.value.password = parseInt(form.value.password)
      form.value.phone = parseInt(form.value.phone)
    }
  }

})
// 返回
const goBack = ()=>{
  router.push({name:"users"})

}

// 保存

const onSubmitUserSave = async ()=>{

  if (props.userId){
    Object.assign(form.value,{id:props.userId})
  }

  const res = await saveOrUpdateUser(form.value)


  console.log("收到的信息反馈",res)
  if (res.data.code){
    ElMessage.success(title.value+"成功")
    await router.push({name:"users"})
  }

}

// 删除商品

const removeUser =async ()=>{

  const {data} = await removeUserById(props.userId)

  if (data.code === "000000"){
  ElMessage.success("删除成功")
  await router.push({name:"users"})
}else {
  ElMessage.error("删除失败")
}



}


// 时间转化
const handleDate = (item)=>{
  form.value.createTime =item.toLocaleDateString()
}

</script>

<template>


  <el-card style="width: auto">
    <template #header>
      <div class="card-header">
        <h1>{{ title }}</h1>
      </div>
    </template>


    <el-form :model="form" label-width="auto" style="width: auto;display: flex">

      <div class="form-big">
      <el-form-item label="商品名" prop="name" :rules="[{ required: true, message: '请输入商品名称', trigger: 'blur' }]">
        <el-input v-model="form.name" />
      </el-form-item>
      <el-form-item label="食物类型">
        <el-select v-model="form.regIp" placeholder="please select your zone">
          <el-option label="包装" value="包装" />
          <el-option label="预制品" value="预制品" />
        </el-select>
      </el-form-item>
      <el-form-item label="生产日期">
        <el-col :span="11">
          <el-date-picker
              v-model="form.createdTime"
              type="date"
              placeholder="选择时间"
              format="YYYY-MM-DD"
              style="width: 200%"
              @change="handleDate"
          />
        </el-col>

      </el-form-item>
      <el-form-item label="上架">
        <el-switch v-model="form.status"
                    active-value="ENABLE"
                    inactive-value="DISABLE"
        />
      </el-form-item>


      <el-form-item label="库存">
        <el-input-number v-model="form.password"/>
      </el-form-item>
      <el-form-item label="价格">
        <el-input-number controls-position="right" v-model="form.phone" type="textarea" />
      </el-form-item>


      </div>
      <div class="img-2">

      <el-form-item label="食物封面">
        <ImageUpload v-model="form.portrait"/>
      </el-form-item>

      </div>


    </el-form>



    <template #footer>
      <div class="btm-group">
        <el-button  type="primary" @click="onSubmitUserSave">保存</el-button>
        <el-button @click="goBack">返回</el-button>
        <el-button  v-show="props.userId" @click="removeUser" type="danger">删除</el-button>
      </div>

    </template>
  </el-card>



</template>

<style scoped lang="scss">

.btm-group{

  text-align: center;
}

.img-2{
  margin-left: 250px;
}
</style>