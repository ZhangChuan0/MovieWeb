<script setup>
import {
  Dish, Film, Food, Handbag,
  Help,
  IceCream,
  Magnet,
  Paperclip,
  Promotion,
  School,
  Setting, ShoppingBag, ShoppingCartFull, Star, Ticket,
  TrendCharts, User,
  UserFilled
} from "@element-plus/icons-vue";
import {isCollapse} from "@/view/layout/isCollapse.js";
import {userInfo} from "@/composables/useLogin.js";

</script>

<template>
  <el-aside>
    <el-scrollbar>
    <!--      只有一个下拉框处开启状态-->
    <el-menu router :collapse="isCollapse">
    <a href="/index" class="loge">
      <img src="@/assets/loge.svg" alt="Unknow"/>
      <h1>MOVIE</h1>
    </a>

      <el-sub-menu v-show="false" index="1" >
<!--        可以打开的菜单-->
        <template #title>
          <el-icon><Setting/></el-icon><span>权限管理</span>
        </template>
        <el-menu-item index="/index/menus">
          <el-icon><IceCream/></el-icon><span>菜单</span>
        </el-menu-item>
        <el-menu-item index="/index/resource">
          <el-icon><Setting/></el-icon><span>资源</span>
        </el-menu-item>
        <el-menu-item index="/index/roles">
          <el-icon><Setting/></el-icon><span>角色</span>
        </el-menu-item>
      </el-sub-menu>

      <!--电影管理-->
      <el-menu-item v-show="userInfo.role === 'admin'"  index="/courses">
        <el-icon><Help /></el-icon><span>电影</span>
      </el-menu-item>

<!--商品-->
      <el-sub-menu  v-show="userInfo.role === 'admin'" index="4">
        <template #title>
          <el-icon><ShoppingCartFull /></el-icon><span>商品管理</span>
        </template>
        <el-menu-item index="/users">
          <el-icon><Dish /></el-icon><span>食物</span>
        </el-menu-item>
        <el-menu-item index="/shopping">
          <el-icon><Handbag /></el-icon><span>周边</span>
        </el-menu-item>

      </el-sub-menu>

<!--员工管理-->
      <el-sub-menu v-show="userInfo.role === 'admin'"  index="3">
        <template #title>
          <el-icon><User /></el-icon><span>员工管理</span>
        </template>
<!--        添加新员工-->
        <el-menu-item index="/manager">
          <el-icon><UserFilled /></el-icon><span>人员控制</span>
        </el-menu-item>
      </el-sub-menu>

<!--      设置-->
      <el-sub-menu  v-show="userInfo.role === 'admin'" index="2">
        <template #title>
          <el-icon><Setting /></el-icon><span>设置</span>
        </template>
        <el-menu-item index="/setting">
          <el-icon><Magnet /></el-icon><span>岗位信息</span>
        </el-menu-item>

        <el-menu-item index="/sales/data">
          <el-icon><TrendCharts /></el-icon><span>信息分析</span>
        </el-menu-item>

      </el-sub-menu>

<!--销售-->
      <el-sub-menu v-show="userInfo.role === 'admin'|| 'staff'" index="5">
        <template #title>
          <el-icon><ShoppingBag /></el-icon><span>销售</span>
        </template>
        <el-menu-item index="/sales/eat">
          <el-icon><Food /></el-icon><span>卖品</span>
        </el-menu-item>

        <el-menu-item index="/sales/movie">
          <el-icon><Ticket /></el-icon><span>电影</span>
        </el-menu-item>


        <el-menu-item index="/query">
          <el-icon><Film /></el-icon><span>查询</span>
        </el-menu-item>

      </el-sub-menu>

<!--会员管理-->
      <el-menu-item v-show="userInfo.role === 'admin'|| 'staff'"  index="/members">
        <el-icon><Star /></el-icon><span>会员</span>
      </el-menu-item>

    </el-menu>
    </el-scrollbar>
  </el-aside>
</template>

<style scoped lang="scss">
.el-aside{
  background-color:#d9ecff;
  height: 100vh;
  width: auto;
}
.el-menu{
  width: 150px;
  background-color: #d9ecff;
  border-right: none;
  &.el-menu--collapse{
    width: 60px;
    & h1{     //让字体消失
      display: none;
    }
  }

  .el-menu-item:hover{
    background-color: #d0e5fa;
  }
}

//菜单栏中的每一个小项 暂时取消


.loge{
  display: flex;
  justify-content: center;
  align-items: center;
  height: 60px;
  text-decoration: none;
  color: black;
  img{
    width: 64px;
    height: 64px;
  }

}
</style>