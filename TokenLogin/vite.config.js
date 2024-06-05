import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import AutoImport from 'unplugin-auto-import/vite'
import Components from 'unplugin-vue-components/vite'
import { ElementPlusResolver } from 'unplugin-vue-components/resolvers'
import Icons from 'unplugin-icons/vite'
import IconsResolver from 'unplugin-icons/resolver'
import * as path from 'path';

export default defineConfig({
  plugins: [
      vue(),
    //   element plus组件的自动导入
    AutoImport({
      imports:["vue"],
      resolvers: [ElementPlusResolver(), IconsResolver()],
  }),
    Components({
      resolvers: [ElementPlusResolver(),
        IconsResolver({
        enabledCollections: ["ep"],
      })],
    }),
    Icons({
      autoInstall: true,
    }), //按需自动导入图标

  ],
  resolve: {
    alias: {
      '@': path.resolve(__dirname, 'src')  //配置之后就可以用@替换../的方式
    }
  },
    server:{
        port:8085
    },
})
