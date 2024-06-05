import { createApp } from 'vue'
import App from './App.vue'
import 'element-plus/dist/index.css'
import {createPinia} from "pinia";
import piniaPluginPersistedstate from 'pinia-plugin-persistedstate'

import router  from "./router/router.js";
import "@/style/index.scss"
const app = createApp(App)
const pinia = createPinia()
pinia.use(piniaPluginPersistedstate)
app.use(pinia)
app.use(router)
app.mount("#app")