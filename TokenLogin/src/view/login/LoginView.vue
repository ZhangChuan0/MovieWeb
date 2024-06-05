<script setup>
import {reactive,ref} from "vue";
import {ElMessage} from "element-plus";
import {login} from "@/api/users.js";
import {useToken} from "@/stores/mytoken.js";
import router from "@/router/router.js";
import {useRoute} from "vue-router";
import {userInfo} from "@/composables/useLogin.js";


// token创建持久化
const store = useToken()

// 创建用于拼接网址
const route = useRoute()
p()

// 模拟的返回值
const loginResult = {
  success: true,  // Boolean类型的值
  state: 1,       // Number类型的值
  message: '登录成功', // String类型的值
  content: '{"access_token":"xx"}' // String类型的值
};

// 定义是否登录加载中
const isLoading =ref(false)

const onSubmit = async ()=>{  //async 同步
  // 点击按键后马上改变登录状态
  isLoading.value = true
  // await 等待校验之后向下运行
  await fromRef.value?.validate().catch(err=> {
    ElMessage.error("校验失败")
    isLoading.value = false
    throw err //抛出错误，不继续执行
  })

  const jform = {
    "phone":form.phone,
    "password":form.password,
    "role":form.role
  }
  userInfo.value = jform

  //  信息校验之后，启动登录
  const data = await login(jform).then(res=>{

    console.log("真实后端信息",res.data.success)
    if (!res.data.success){
      ElMessage.error("登录信息有误")
      isLoading.value = false
      throw new Error("not run")
    }
    return res //返回四个结果
  })

  store.saveToken(data.data.content)


  console.log("前端收集的数据",jform)



  // 跳转到指定页面
  console.log("role:"+userInfo.value.role)
  if (userInfo.value.role === "admin"){
     await router.push((route.query.redirect) || {name:"sales-data"}) // login默认跳转到后一个，涉及到加密就第一个
  }else {
     await router.push((route.query.redirect) || {name:"query"}) // login默认跳转到后一个，涉及到加密就第一个
  }






  isLoading.value = false //将登录状态重置

}

//输出保存的token信息
 function p(){
  console.log("持久化是否成功",store.tokenJson)
}


// 表单的数据
const form = reactive({
  phone:"18783174270",
  password:"123456",
  role:"staff"
})


// 表单数据规则  表单采用prop匹配
const rules = reactive({
  phone:[
    {required:true,message:"电话号码不能为空",trigger:"blur"},
    {pattern:/^1\d{10}$/,message: "手机号码必须是11位数字"},
  ]
  ,
  password:[
    {required:true,message:"请输入电话号码",trigger:"blur"},
    {min:6,max:18,message: "密码需要6~18位数",trigger: "blur"},
  ]
})

  const fromRef = ref(0)

</script>

<template>
  <div class="login">
    <el-form :model="form" label-width="auto" :rules="rules" ref="fromRef" style="max-width: 600px" label-position="top" size="large">   <!--:rules=校验规则，ref="fromRef" 表单数据 label-position是组件的位置-->
      <h2>登录</h2>
      <el-form-item label="手机号" prop="phone">
        <el-input v-model="form.phone" />
      </el-form-item>

      <el-form-item label="密码" prop="password">
        <el-input type="password" v-model="form.password" />
      </el-form-item>

      <el-form-item label="登录角色">
        <el-select v-model="form.role" style="width: 600px" placeholder="请选择角色">
          <el-option
              label="管理员"
              value="admin"
          />
          <el-option
              label="员工"
              value="staff"
          />
        </el-select>
      </el-form-item>

      <el-form-item>
        <el-button type="primary" @click="onSubmit" v-bind:loading="isLoading">登录</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<style scoped lang="scss">
.login{
  background-color: #ffffff;
  height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
  background-position: left center; /* 初始背景图位置在左边 */
  animation: slideshow 30s infinite linear; /* 切换背景图片动画，10秒循环 */

  .el-form{
    width: 300px;
    background-color: #dedada;
    padding: 30px;
    border-radius:10px ;
    box-shadow: 0 4px 10px 10px  rgb(128, 128, 128);

    .el-form-item{
      margin-top: 30px;
    }
    .el-button{
      width: 100%;
      margin-top: 30px;
    }

  }



}
.login:hover {
  animation-play-state: paused;
}

@keyframes slideshow {
  0% {
    background-image: url("@/assets/pic1.jpg");
    background-position: 0 center; /* 背景图位置在最左边 */

  }
  25% {
    background-image: url("@/assets/pic2.jpg");
    background-position: 25% center;


  }
  50% {
    background-image: url("@/assets/pic3.jpg");
    background-position: 55% center;

  }
  75% {
    background-image: url("@/assets/pic5.jpg");
    background-position: 70% center;

  }
  100% {
    background-image: url("@/assets/pic4.jpg");
    background-position: 100% center; /* 背景图位置在最右边 */

  }
}

</style>