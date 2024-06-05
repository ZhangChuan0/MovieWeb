<script setup>
import {queryCondition,queryCourses,queryResult} from "@/composables/useCourses.js";
import {changeState, deleteById, getCourse_largeById} from "@/api/courses.js";
import {ElMessage, ElMessageBox} from "element-plus";

queryCourses()



const small = ref(false)
const disabled = ref(false)

const handleSizeChange = (val) => {
  queryCourses({
    pageSize:val,
    currentPage:1
  })
}
const handleCurrentChange = (val) => {
  queryCourses({
    currentPage:val
  })
}

// 上架下架的变化
const changeStatus = async (val,row)=>{

const  xx = [
    {msg:"下架",oldVal : 1},
    {msg:"上架",oldVal : 0 }
]

  console.log( val, row.id)

  const res = await changeState(row.id,val)

  if (res.data){
    ElMessage.success(`${xx[0].msg}成功`)
  }else {
    row.status = xx[val].oldVal
    ElMessage.error(`${xx[1].msg}失败`)
  }

}

// 删除一条记录
const removeOne =async (event)=>{

  await ElMessageBox.confirm('真的确认删除吗?', {

    cancelButtonText: '取消',
    confirmButtonText: '确认',
    type: 'warning'
  }).catch(()=>{
    ElMessage.info("已取消")
   throw new Promise(()=>{})
  })

//   传递删除接口
  const {data} = await deleteById(event)
     if (data.code === "000000"){
        ElMessage.success("删除成功")
       await queryCourses({
         current: 1
       })
     }else {
       ElMessage.error("删除失败")
     }

}


</script>

<template>
  <el-card >
    <template #header>
      <div class="card-header">

<!--        搜索  -->
        <el-form :inline="true" :model="queryCondition">
        <el-form-item>
          <el-form-item label="电影">
            <el-input v-model="queryCondition.name" placeholder="请输入名称" clearable />
          </el-form-item>
          <el-form-item label="状态" >
            <el-select v-model="queryCondition.status" placeholder="请选择状态" clearable style="width: 150px;">
              <el-option label="全部" value=""/>
              <el-option label="上架" value="ENABLE"/>
              <el-option label="下架" value="DISABLE"/>
            </el-select>
          </el-form-item>
        </el-form-item>

          <el-form-item>
          <el-button type="primary" @click="()=>queryCourses({current:1})">搜索</el-button>
        </el-form-item>

        </el-form>
          <el-button type="primary" @click="$router.push({name:'course-create'})">新建</el-button>
      </div>
    </template>
<!--    展示表格-->
    <el-table :data="queryResult.records" border >
      <el-table-column type="index" label="序号" width="auto"  align="center"/>
      <el-table-column prop="courseName" label="电影名称" width="auto"  align="center"/>
      <el-table-column prop="discounts" label="价格" width="auto" align="center"/>
      <el-table-column prop="discountTag" label="状态" width="auto" align="center" v-slot="{row}">

        <el-switch
            v-model="row.discountTag"
            active-text="上映"
            inactive-text="下架"
            active-value="ENABLE"
            inactive-value="DISABLE"
            @change="changeStatus($event,row)"
        />

      </el-table-column>
      <el-table-column prop="sortNum" label="所在厅" width="auto" align="center"/>
      <el-table-column  label="操作" width="auto" align="center" v-slot="{row}">
        <el-button type="info" @click="$router.push({name:'course-edit',params:{courseId:row.id}})">编辑</el-button>
        <el-button type="danger" @click="removeOne(row.id)">删除</el-button>

      </el-table-column>

    </el-table>

    <el-pagination
        v-model:current-page="queryResult.hitCount"
        v-model:page-size="queryResult.current"
        :page-sizes="[5, 10, 15, 20]"
        :small="small"
        :background="true"
        layout="total, sizes, prev, pager, next, jumper"
        :total="queryResult.pages"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
    />

  </el-card>
</template>

<style scoped lang="scss">
.el-form{
  display: flex;
  justify-content: space-around;
  align-content: center;
}
.el-card{
  width: auto;
}
.el-table{
  width: auto;
}
.el-pagination{
  display: flex;
  justify-content: center;
  align-content: center;
}
.el-form-item{
  margin-bottom: 0;
}
</style>