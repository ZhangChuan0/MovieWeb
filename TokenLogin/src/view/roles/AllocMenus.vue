<script setup>
import {getRoleMenus,allocateRoleMenus} from "@/api/roles.js";
import {useRouter} from "vue-router";
import {ElMessage} from "element-plus";
const router = useRouter()
//不用useRoute的方法
// 获取路由中的参数
const props = defineProps({
  roleId: {
    required:true,
    type:String
  }
})


const data = [
  {
    name: '时间管理',
    records: [
      {
        name: '二级 1-1'
      }
    ]
  },
  {
    name: '课程管理',
    records: [
      {
        name:'nihao'
      },
      {
        name:'再见'
      }
    ]
  }
  , {
    name: '用户管理',
    records: [
      {
        name:'nihao'
      },
      {
        name:'再见'
      }
    ]
  }
  , {

    name: '广告管理',
    records: [
      {
        name:'nihao'
      },
      {
        name:'再见'
      }
    ]

  }
]

// 默认被选中的key
const checkedIds = ref([])

// 存储数据
const roleMenus = ref([])



// 获取被选中的元素id
const getCheckIds = (arr=[])=>{
  arr.forEach((roleMenu)=>{
    if (roleMenu.records){
    // 跳过
      getCheckIds(roleMenu.records)
    }else if (roleMenu.selected){
      checkedIds.value.push(roleMenu.index)
    }
  })

}


// 获取数据的方法
const loadRoleMenus = async () => {
  const {data} = await getRoleMenus(props.roleId)
  if (data.code === "000000"){
    roleMenus.value = data.records
    getCheckIds(data.records)  //将id保存到数组中
  }
  console.log("服务器传来的数据",roleMenus.value)
  console.log("已经选中的id",checkedIds.value)

}


// 数据类型

const dataStruct =  ({
  label: "name",
  children: "records"
})

const menuTree = ref()

// 清空
const onClear = ()=>{

menuTree.value?.setCheckedKeys([])  //空数组



}

// 保存
const onSave = async () => {

  const currentCheckIds = menuTree.value?.getCheckedKeys()
  menuTree.value?.get

// 发送数据给接口保存
  const {data} = await allocateRoleMenus(props.roleId,currentCheckIds)
  if (data){
    ElMessage.success("更新Role权限成功")
      await router.push({name: 'roles'})
  }else {
    ElMessage.error("更新Role权限失败")
  }
}

// 加载数据
loadRoleMenus()
//
</script>

<template>
  <el-tree
      ref="menuTree"
      :data="roleMenus"
      style="max-width: 1000px;"
      :props="dataStruct"
      show-checkbox
      @check-change="(node,checked)=>{}"
      default-expand-all
      node-key="index"
      :default-checked-keys="checkedIds"
  />
  <el-button type="primary" @click="onSave">添加</el-button>
  <el-button type="info" @click="onClear">重置</el-button>


</template>

<style scoped lang="scss">

.el-tree{
  margin-bottom: 30px;
  background-color: #dcf5fc;
}
</style>