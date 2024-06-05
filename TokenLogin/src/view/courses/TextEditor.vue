<script setup>
import {Editor, Toolbar} from "@wangeditor/editor-for-vue";

import '@wangeditor/editor/dist/css/style.css' // 引入 css

import { onBeforeUnmount, ref, shallowRef, onMounted ,watchEffect} from 'vue'

// 编辑器实例，必须用 shallowRef
const editorRef = shallowRef()

// 内容 HTML
const valueHtml = ref('<p>hh </p>')
const props = defineProps({
  modelValue:{
    type:String,
    default:''
  }
})

const emit = defineEmits()

watchEffect(()=>{
  valueHtml.value = props.modelValue  //将接收到的props的值赋给valueHtml
})
watch(valueHtml,(newValue)=>{
  emit('update:modelValue',newValue)  //监听到valueHtml有变化时，自动发送到父组件

})




    const toolbarConfig = {}
    const editorConfig = { placeholder: '请输入内容...' }

    // 组件销毁时，也及时销毁编辑器
    onBeforeUnmount(() => {
      const editor = editorRef.value
      if (editor == null) return
      editor.destroy()
    })

    const handleCreated = (editor) => {
      editorRef.value = editor // 记录 editor 实例，重要！
    }


</script>

<template>
  <div style="border: 1px solid #ccc">
    <Toolbar
        style="border-bottom: 1px solid #ccc"
        :editor="editorRef"
        :defaultConfig="toolbarConfig"
    />
    <Editor
        style="height: 500px; overflow-y: hidden;"
        v-model="valueHtml"
        :defaultConfig="editorConfig"
        @onCreated="handleCreated"
    />
  </div>
</template>

<style scoped lang="scss">

</style>