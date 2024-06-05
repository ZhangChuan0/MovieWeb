<script setup>
import {ref} from "vue";
import {ArrowDown, Calendar, Expand, Fold} from "@element-plus/icons-vue";
import {isCollapse} from "@/view/layout/isCollapse.js";
import {getInfo,logout} from "@/api/users.js";
import {dayjs, ElMessage, ElMessageBox} from "element-plus";
import {useToken} from "@/stores/mytoken.js";
import {useRouter} from "vue-router";
import {userInfo as publicInfo} from "@/composables/useLogin.js";
import {getToolList, lockList, queriedResult, queryUsers,} from "@/composables/useUser.js";
import {Resultshopping} from "@/composables/useShopping.js";

const userInfo = ref({
    portrait:"",
    name:"小帅",
    role:""
})

const store = useToken()
const router = useRouter()

getToolList()

queryUsers()


getInfo(publicInfo.value).then((res) => {
  console.log("得到的信息",res.data.data)
  userInfo.value.name = res.data.data.name
  userInfo.value.portrait = res.data.data.portrait
  userInfo.value.role = res.data.data.role

})


// 退出
const handleLogout = async ()=>{
  // 二次询问  清空token，跳转到login
  await ElMessageBox.confirm("确认退出吗？","退出提示",{
    confirmButtonText:"Yes",   //成功
    cancelButtonText:"wait",
    type:'warning'
  }).catch(()=>{
    ElMessage.info("取消退出")
    return new Promise(()=>{})  //捕获，从而不会向下执行
  })

  await logout(store.token).then((res)=>{
    console.log("后端已处理",res.data.content)
    ElMessage.success("用户成功退出")
  })



//   跳转回登录界面
   await router.push({
    name:"login"
  })
  store.saveToken("") //清空

}

const handleSet = async ()=>{

  // 跳转到用户设置界面

  const {data} = await getInfo(publicInfo.value)

  await router.push({name:'manager-edit',params:{managerId:data.data.id}});

}

const lockInfo = ref({
  foods:[],
  tools:[]
})

const getLockList = computed(()=>{
  lockInfo.value.foods = queriedResult.value.records.filter(item => item.status === 'ENABLE' && parseInt(item.password) <= 5)

  lockInfo.value.tools = Resultshopping.value.filter(item => item.status === 'ENABLE' && item.stock <= 5)

  console.log("锁定的信息",lockInfo.value)
  return lockList.value = lockInfo.value.tools.length+lockInfo.value.foods.length
})

watch([queriedResult, Resultshopping], () => {
  // 手动触发 computed 属性的重新计算
  getLockList.value;
});

const drawer = ref(false)

// 时间
const currentTime = ref('');

const updateTime = () => {
  const now = new Date();
  const year = now.getFullYear();
  const month = now.getMonth() + 1;
  const date = now.getDate();
  const day = now.getDay();
  const weekDays = ['日', '一', '二', '三', '四', '五', '六'];
  const weekDay = weekDays[day];
  let hour = now.getHours();
  const ampm = hour >= 12 ? '下午' : '上午';
  hour = hour % 12 || 12; // 转换为12小时制
  const minute = now.getMinutes();
  const second = now.getSeconds();

  currentTime.value = `${year}年${month}月${date}日 星期${weekDay} ${ampm} ${hour}:${minute}:${second}`;
};

onMounted(() => {
  updateTime();
  setInterval(updateTime, 1000); // 每隔1秒更新一次时间
});

onUnmounted(() => {
  clearInterval(updateTime); // 组件卸载时清除定时器
});

const showDrawer = ()=>{

  drawer.value = true

  console.log("tools",lockInfo.value.tools.length ,"foods",lockInfo.value.foods.length )
  console.log("tools",lockInfo.value.tools)
}

</script>

<template>
  <el-header>
<!--    展开和关闭的图标-->

    <el-icon @click="isCollapse=!isCollapse">  <!--    模板中使用ref不用.value 但是js需要-->
      <Expand v-if="isCollapse" />
    <!--      可以改成v-show展示-->
      <Fold    v-else />
    </el-icon>

<!--    面包屑-->
    <el-breadcrumb separator="/">
      <el-breadcrumb-item v-for="(item,index) in $route.matched "
                          :to="{ path: item.path }"
                          :key="index">{{item.meta.title}}</el-breadcrumb-item><!--用for遍历得到所有的路由地址-->

    </el-breadcrumb>

<!--抽屉-->
    <el-drawer v-model="drawer" title="库管中心">

        <div v-if="lockInfo.tools.length > 0">
          <div v-for="(category,index) in lockInfo.tools" :key="index" class="category-container">
            <div class="category-header">{{ category.name }}</div>
            <div class="item-container">
              <span class="item-name">库存：{{ category.stock }}</span>
            </div>
          </div>
        </div>

      <div v-if="lockInfo.foods.length > 0">
        <div v-for="(category,index) in lockInfo.foods" :key="index" class="category-container">
          <div class="category-header">{{ category.name }}</div>
          <div class="item-container">
            <span class="item-name">库存：{{ category.password }}</span>
          </div>
        </div>
      </div>

      <div v-else-if="lockInfo.tools.length === 0 && lockInfo.foods.length === 0">
        库存状况良好
      </div>

    </el-drawer>


<!--    显示当前时间-->
    <div class="time-card">
      <div class="time-header">当前时间</div>
      <div class="time-body">{{ currentTime }}</div>
    </div>



<!--    下拉菜单-->
    <el-dropdown>
    <span class="el-dropdown-link">
<!--      头像框-->
      <el-avatar v-bind:src="userInfo.portrait"/>
      <el-icon class="el-icon--right">
        <arrow-down />
      </el-icon>
    </span>
      <template #dropdown>
        <el-dropdown-menu>
          <el-dropdown-item>{{userInfo.name}}</el-dropdown-item>
          <el-dropdown-item divided  @click="handleSet">设置</el-dropdown-item>
          <el-dropdown-item divided  >
            <el-badge :value="lockList" class="item">
              <el-button @click="showDrawer">库存</el-button>
            </el-badge>

          </el-dropdown-item>
          <el-dropdown-item divided @click="handleLogout">退出登录</el-dropdown-item>
        </el-dropdown-menu>
      </template>
    </el-dropdown>
  </el-header>
</template>

<style scoped lang="scss">

.time-card {
  background-color: #aed9ff;
  border-radius: 4px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2), 0 0 12px rgba(0, 0, 0, 0.08);
  padding: 10px 15px;
  margin-left: 380px;
  font-family: 'Helvetica Neue', sans-serif;
}

.time-header {
  font-size: 14px;
  color: #909399;
  margin-bottom: 5px;
}

.time-body {
  font-size: 20px;
  font-weight: 500;
  color: #606266;
}


.el-header{
  display: flex;  //默认内部组件水平显示
  align-items: center;
  background-color:  #c6e2ff;
  .el-icon{
    margin-right: 20px;
  }
}
.el-dropdown{
  margin-left: auto;
  .el-dropdown-link{
    //使得下拉菜单和图标对齐
    display: flex;
    align-items: center;
  }
}

.item {
  margin-top: 10px;
  margin-right: 30px;
}

.category-container {
  margin-bottom: 16px;

  .category-header {
    font-size: 18px;
    font-weight: bold;
    color: #333;
    background-color: #f2f2f2;
    padding: 8px 12px;
    border-radius: 4px 4px 0 0;
  }

  .item-container {
    display: flex;
    justify-content: space-between;
    background-color: #fff;
    border: 1px solid #ccc;
    border-top: none;
    padding: 8px 12px;

    &:last-child {
      border-radius: 0 0 4px 4px;
    }

    .item-name {
      font-size: 16px;
      color: #333;
    }

    .item-value {
      font-size: 16px;
      font-weight: bold;
      color: #18282d;
    }
  }
}
</style>