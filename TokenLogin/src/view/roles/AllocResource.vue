<script setup>
import {ref,reactive} from "vue";
import {getRoleResource,allocateRoleResource} from "@/api/roles.js";
import AllocCategory from "@/view/roles/AllocCategory.vue";
import {useRouter} from "vue-router";
import {ElMessage} from "element-plus";
const router = useRouter()
const props = defineProps({
  roleId: {
    required:true,
    type:String
  }

})

// 存储数据
const roleResources = ref()

// 获取数据

const loadRoleResource = async ( )=>{
  const {data} = await getRoleResource(props.roleId)
  if (data.code === "000000"){
    roleResources.value = data.records
  }

}

loadRoleResource()



// 保存
const onSave = async ()=>{
//   所有被选择中的资源

  const  allCheckIds = collectionCheckIds.reduce((temp,item)=>{
    return [...temp,...item]
  },[])
  console.log("get info form front",allCheckIds,"id info",props.roleId)
  //调用接口 传数据给服务器
  const {data} = await allocateRoleResource(props.roleId,allCheckIds)
  if (data){
    ElMessage.success("更新Resource权限成功")
    await router.push({name: 'roles'})
  }else {
    ElMessage.error("更新Resource权限失败")
  }

}


// 存储子组件的被选项

const collectionCheckIds = []

// 处理子组件的消息
const handleAddCheckIds = (payload) =>{
  collectionCheckIds.push(payload)
}

// 清空
const onClear=()=>{


}


</script>

<template>
  <AllocCategory v-for="category in roleResources" :key="category.name" :category="category" @postCheckIdsRef="handleAddCheckIds"/>
  <div class="resource-btn">
    <el-button type="primary" @click="onSave">  保存</el-button>
    <el-button type="info" @click="onClear">  重置</el-button>

  </div>
</template>

<style scoped lang="scss">

</style>