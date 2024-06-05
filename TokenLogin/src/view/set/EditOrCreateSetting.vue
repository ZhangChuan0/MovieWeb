<script setup>
import {getPositionList,resultPosition,resultRoom,resultLanguage,getLanguageList,getRoomList} from "@/composables/useSet.js";
import {useRouter}  from "vue-router";
import { reactive, ref } from 'vue'
import DialogSetting from "@/view/set/DialogSetting.vue";
import {deleteById} from "@/api/set.js";

const router = useRouter()
const props = defineProps({
  until:{
        type:String,
        default:''
      }
    }
)

getPositionList()
getLanguageList()
getRoomList()

const title = ref("")

onMounted(()=>{
  if (props.until=== "position"){
    title.value = "岗位设置"
  }else if (props.until === "language"){
    title.value = "语言设置"
  }else {
    title.value = "影厅设置"
  }

})



const formLabelAlign = reactive({
  name: '',
  region: '',
  type: '',
})

const dialogSetting = ref()



const removeOne = async (id,data)=>{
  const init = ({})

  Object.assign(init, data)
  Object.assign(init,id)
  console.log(init)

  console.log(init," is information")
  const res = await deleteById(init)
  await  router.push({name:"set"})

}
</script>
<template>
  <el-page-header @back="()=>{router.go(-1)}">
    <template #content>
      <span class="text-large font-600 mr-3"> {{title}} </span>
    </template>

    <template #extra>
      <el-button @click="dialogSetting.initAndShow(undefined,{description:props.until})">新增</el-button>
    </template>
  </el-page-header>
  <div class="box">
    <el-card style="width: 600px; ">

      <div v-if="props.until === 'position'">
        <el-table :data="resultPosition"
                  style="width: 100%"
        >
          <el-table-column type="index" label="序号" width="180" />
          <el-table-column prop="name" label="名称" width="180" />
          <el-table-column  label="操作" v-slot="{row}">
            <el-button type="danger"  @click="removeOne({id:row.id},{description:props.until})">删除</el-button>
            <el-button  @click="dialogSetting.initAndShow(row.id,{description:props.until})">操作</el-button>
          </el-table-column>
        </el-table>
      </div>

      <div v-else-if="props.until ===  'language' ">
        <el-table :data="resultLanguage" style="width: 100%">
          <el-table-column type="index" label="序号" width="180" />
          <el-table-column prop="name" label="名称" width="180" />
          <el-table-column  label="操作" v-slot="{row}">

            <el-button type="danger"  @click="removeOne({id:row.id},{description:props.until})">删除</el-button>
            <el-button @click="dialogSetting.initAndShow(row.id,{description:props.until})">操作</el-button>
          </el-table-column>
        </el-table>
      </div>

      <div v-else>
        <el-table :data="resultRoom" style="width: 100%">
          <el-table-column type="index" label="序号" width="180" />
          <el-table-column prop="name" label="名称" width="180" />
          <el-table-column label="操作" v-slot="{row}">
            <el-button type="danger" @click="removeOne({id:row.id},{description:props.until})">删除</el-button>
            <el-button @click="dialogSetting.initAndShow(row.id,{description:props.until})">操作</el-button>
          </el-table-column>
        </el-table>
      </div>
    </el-card>
  </div>
  <DialogSetting ref="dialogSetting"/>
</template>

<style scoped lang="scss">
.box{
  margin-top: 20px;
  display: flex;
  .el-card{
   margin-left: 200px;
  }

.el-form{
  width: 50%;
  margin-left: 100px;
  border-radius: 10px;
  background-color: #c5e1fd;
}
}
</style>