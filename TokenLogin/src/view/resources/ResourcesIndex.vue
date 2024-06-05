<script setup>
import {queryCondition,queriedResult,queryResources} from "@/composables/useResources.js";
import {useRouter} from "vue-router";
import {ref} from "vue";
import {allResourceCategory,getAllResourceCategory} from "@/composables/useResourceCategory.js";
import DlgResourceCreateOrEdit from "@/view/resources/DlgResourceCreateOrEdit.vue";
import {deleteById} from "@/api/resources.js";
import {ElMessage, ElMessageBox} from "element-plus";

const router = useRouter()
queryResources();
getAllResourceCategory()
const queryFm = ref()
// 分页


// 展示弹窗
const dlgResourceCreateOrEdit = ref()


// 删除一行
 const deleteRecordsById = async (id) =>{
  const {data} = await deleteById(id)
  await ElMessageBox.confirm("确定删除吗？","删除提醒",{
    confirmButtonText:"确定", //成功后顺序执行
    cancelButtonText:"取消", // 失败被捕获
    type:"warning"
  }).catch(()=>{
    ElMessage.info("取消删除")
    return new Promise(()=>{})
  })
  if (data){
    ElMessage.success("删除成功")
  }else {
    ElMessage.error("删除失败")
    throw new Error("删除失败了")
  }

  await queryResources()
}
</script>
<template>
  <el-card>

    <template #header>
      <el-form :inline="true" :model="queryCondition" ref="queryFm" class="demo-form-inline">

        <el-form-item label="资源名称"  prop="name">
          <el-input   v-model="queryCondition.name" placeholder="名称" clearable />
        </el-form-item>

        <el-form-item label="资源路径" prop="url">
          <el-input   v-model="queryCondition.url" placeholder="路径" clearable />
        </el-form-item>

        <el-form-item label="资源分类" prop="category"  >
          <el-select v-model="queryCondition.category" placeholder="分类"  >
            <el-option label="不限" value=""/>
            <el-option v-for="cate in allResourceCategory" :key="cate.id" :label="cate.name" :value="cate.id"/>
          </el-select>
        </el-form-item>

        <el-form-item>
          <el-button type="primary" @click="queryResources()">查询</el-button>
          <el-button type="info" @click="queryFm?.resetFields()">重置</el-button>
        </el-form-item>
      </el-form>
    </template>

    <el-button type="primary" @click="dlgResourceCreateOrEdit.initAndShow()">添加资源</el-button>
    <el-button type="primary" @click="router.push({name:'resource-category'})">资源分类</el-button>

<!--   表格-->
    <el-table  :data="queriedResult.records" border style="width: 100%">
      <el-table-column type="index" label="序号" width="auto" align="center"/>
      <el-table-column prop="name" label="资源名称" width="auto" align="center"/>
      <el-table-column prop="url" label="资源路径" width="auto" align="center"/>
      <el-table-column prop="description" label="描述" width="auto" align="center"/>
      <el-table-column prop="createdTime" label="添加时间" width="auto" align="center"/>
      <el-table-column prop="name" label="操作" width="auto" align="center" #="{row}">
        <el-button type="primary" @click="dlgResourceCreateOrEdit.initAndShow(row)">编辑</el-button>
        <el-button type="danger" @click="deleteRecordsById(row.id)">删除</el-button>
      </el-table-column>
    </el-table>

    <!--    分页控制-->
    <el-pagination
        @current-page="queriedResult.current"
        @page-size="queriedResult.size"
        :page-sizes="[5, 10, 15, 20]"
        layout="total, sizes, prev, pager, next, jumper"
        :total="queriedResult.total"
        @size-change="(pageSize)=>{
          queryResources({pageSize})
          console.log('每页条数改变')
        }"
        @current-change="(pageNumber)=>{
          queryResources({pageNumber})
          console.log('选中页码改变')
        }"
    />
  </el-card>
  <DlgResourceCreateOrEdit ref="dlgResourceCreateOrEdit"/>
</template>

<style scoped lang="scss">

.demo-form-inline{
  display: flex;
  justify-content: space-around;
}
.card-header{
  display: flex;
  justify-content: space-between;
  align-content: center;
}
.el-table{
  //按钮和上边框有层级
  margin-top: 20px;
}
.el-select{
  width: 90px;
}
.text{
  font-size: 14px;
}

.item{
  margin-bottom: 18px;
}
.box-card{
  width: auto;
}

</style>