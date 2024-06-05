<script setup>
import {ref,defineExpose} from "vue";
import {ElMessage} from "element-plus";
import ImageUpload from "@/view/courses/ImageUpload.vue";
import {saveOrUpdate, getProductById, deleteProduct} from "@/api/Shopping.js";
import {getNameList, Resultmovie, shoppingMethod} from "@/composables/useShopping.js";

getNameList()

const initData = ()=> ({
  name:"",
  iamge_url:"https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png",
  status:"ENABLE",
  stock:0,
  saveTime:0,
  description:"",
  price:0,
  id:"",
})



const form = ref()

// 显示弹窗
const dialogFormVisible = ref(false)

const number = ref(0)
// 封装显示的方法
const initAndShow = async (id) => {

  form.value = initData()
  // 2024年4月30日
  if (id){
  //   更新
    title.value = "更新"
    Object.assign(form.value,{id:id})
    number.value = id;
    const {data} = await getProductById(id)
    if (data.code === "000000"){
      form.value = data.data
      form.value.price = parseInt(data.data.price)
    }

  }else {
    title.value = "新建"

  }
  dialogFormVisible.value = true
}


const onSubmit = async ()=>{
const {data} = await saveOrUpdate(form.value).finally(()=>{dialogFormVisible.value=false})
  if (data.code === "000000"){
    ElMessage.success(title.value+"成功")

  }else {
    ElMessage.error(title.value+"失败")
  }

  await shoppingMethod()

}
// 标题
const title = ref("")


// 删除

const removeOne=async ()=>{

  const {data} = await deleteProduct(number.value).finally(()=>{dialogFormVisible.value=false})
  if (data.code === "000000"){
    ElMessage.success("删除成功")

  }else {
    ElMessage.error("删除失败")
  }

  await shoppingMethod()

}


defineExpose({
  initAndShow
})

</script>

<template>
  <el-dialog v-model="dialogFormVisible" :title="title+'商品'" width="800">
    <el-form :model="form" style="display: flex">
<div>
      <el-form-item label="商品名" style="width: 400px" prop="name" :rules="[{ required: true, message: '请输入商品名', trigger: 'blur' }]">
        <el-input v-model="form.name" show-word-limit maxlength="20"/>
      </el-form-item>
      <el-form-item label="相关电影" style="width: 200px">
        <el-select v-model="form.description" >
          <el-option v-for="item in Resultmovie" :key="item.courseName" :label="item.courseName" :value="item.courseName"/>
        </el-select>
      </el-form-item>

  <el-form-item label="价格">
    <el-input-number controls-position="right" v-model="form.price" :min="0" :step="2"/>
  </el-form-item>
  <el-form-item label="库存">
    <el-input-number controls-position="right" v-model="form.stock" :min="0"/>
  </el-form-item>

  <el-form-item label="保质期">
    <el-input-number controls-position="right" v-model="form.saveTime"/>
  </el-form-item>


  <el-form-item label="上架销售">
    <el-switch
        v-model="form.status"
        class="ml-2"
        style="--el-switch-on-color: #13ce66; --el-switch-off-color: #ff4949"
        active-text="是"
        inactive-text="否"
        active-value="ENABLE"
        inactive-value="DISABLE"
    />
  </el-form-item>
</div>



        <el-form-item label="图片">
          <ImageUpload v-model="form.iamge_url"/>
        </el-form-item>


    </el-form>
    <template #footer>
      <div v-if="title === '更新'">
        <el-button type="danger" @click="removeOne">删除</el-button>
      </div>
      <div class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取消</el-button>
        <el-button type="primary" @click="onSubmit">
          确认
        </el-button>
      </div>

    </template>
  </el-dialog>

</template>

<style scoped lang="scss">

</style>