<script setup>
import { ref } from 'vue';
import {useRouter} from "vue-router";
import {getLanguageList,resultLanguage} from "@/composables/useSet.js";
import {
  getMovieList,
  getType,
  getYear,
  handleDate,
  handleLanguage, handleType,
  movieList,
  salesList,
  typeList
} from "@/composables/usequery.js";
import {queryCondition} from "@/composables/useCourses.js";


const router = useRouter()
// 全部电影
getMovieList()

// 语言
getLanguageList()

// 全部类型
getType()

// 年份
getYear()

// 搜索条件
const isShowQuery = ref(true)

</script>

<template>
  <el-card style="width: auto">
    <template #header>

      <el-form :model="queryCondition" label-width="auto" style="text-align: center;">
        <el-form-item label="电影名" style="width: 50%; display: flex; justify-content: center; align-items: center; margin: 0 auto;">
          <el-input v-model="queryCondition.name" placeholder="请输入电影名称" style="flex: 1;" clearable/>
          <el-button type="primary" style="margin-left: 20px;" @click="()=>getMovieList({current:1})">搜索</el-button>
        </el-form-item>
      </el-form>
      <!--        分割线-->
        <el-divider/>
      <el-button type="primary" style="margin-left: 20px;" @click="isShowQuery = !isShowQuery" > {{ isShowQuery ? '收起' : '展开' }}</el-button>

      <transition name="slide-fade">
      <div v-show="isShowQuery" class="query-container">
      <el-form>
        <el-form-item label="电影类型" style="width: auto; ">
          <div class="filter-container" v-for="type in typeList" >
            <el-card class="filter-card" @click="()=>{handleType(type)}">
              {{ type }}
            </el-card>
          </div>
        </el-form-item>
        <el-divider/>
        <el-form-item label="影片语言" style="width: auto ">
          <div class="filter-container" v-for="language in resultLanguage" >
            <el-card class="filter-card" @click="()=>{handleLanguage(language.name)}">
              {{language.name}}
            </el-card>
          </div>

        </el-form-item>
        <el-divider/>
        <el-form-item label="上映年份" style="width: auto ">

          <div class="filter-container">
            <el-card class="filter-card" v-for="year in salesList" :key="year" @click="() => { handleDate(year) }">
              {{ year }}
            </el-card>
          </div>
        </el-form-item>
      </el-form>
      </div>
      </transition>
    </template>

    <div class="movie-container">
      <el-card class="movie-card" v-for="movie in movieList" :key="movie.id" @click="router.push({name:'movie'})">
        <img :src="movie.courseListImg" class="movie-img" alt="Unknown">
        <div class="movie-info">
          <div class="movie-title">{{ movie.courseName }}</div>
          <div class="movie-detail">
            <div><strong>主演：</strong>{{ movie.teacherDescription }}</div>
            <div><strong>语言：</strong>{{ movie.teacherPosition }}</div>
            <div><strong>价格：</strong>{{ movie.discounts }}</div>
            <div><strong>类型：</strong>{{ movie.previewFirstField }}/{{ movie.previewSecondField }}</div>
            <div><strong>上映日期：</strong>{{ movie.sales }}</div>
            <div><strong>概要：</strong>{{ movie.courseDescriptionMarkDown }}</div>
          </div>
        </div>
      </el-card>
    </div>

  </el-card>
</template>

<style scoped lang="scss">
.movie-container {
  display: flex;
  flex-wrap: wrap;
  justify-content: center;
}

.movie-card {
  width: calc(20% - 40px);
  margin: 20px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  transition: box-shadow 0.3s ease;
}

.movie-card:hover {
  box-shadow: 0 8px 16px rgba(0, 0, 0, 0.2);
}

.movie-img {
  width: 100%;
  height: auto;
  object-fit: cover;
}

.movie-info {
  padding: 15px;
}

.movie-title {
  font-size: 18px;
  font-weight: bold;
  margin-bottom: 10px;
}

.movie-detail div {
  margin-bottom: 5px;
}


//筛选框
.filter-container {
  display: flex;
  flex-wrap: wrap;
  justify-content: center;
}

.filter-card {
  font-size: 17px;
  margin: 10px;
  padding: 5px;
  cursor: pointer;
  background-color: #bbe5fd;
  border: 1px solid #e8e8e8;
  border-radius: 5px;
  transition: background-color 0.3s ease;
}


//搜索框的消失动画
/* 定义过渡效果 */
.slide-fade-enter-active, .slide-fade-leave-active {
  transition: all 0.3s ease;
}

.slide-fade-enter, .slide-fade-leave-to /* .slide-fade-leave-active in <2.1.8 */ {
  transform: translateY(-10px);
  opacity: 0;
}

.query-container {
  border:1px red solid;
  background-color: #f9f9f9; /* 给搜索区域一个背景色 */
  padding: 20px; /* 增加内边距 */
  margin-top: 10px; /* 增加顶部间距 */
  border-radius: 8px; /* 增加圆角 */
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.3); /* 增加阴影效果 */
}

</style>