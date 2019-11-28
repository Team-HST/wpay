import Vue from 'vue'
import App from './App.vue'
import store from '@/store/index'
import router from '@/router'
import lodash from 'lodash'
import { common } from '@/utils/common'
import { api } from '@/utils/api'
import VueQrcodeReader from "vue-qrcode-reader";

Vue.use(VueQrcodeReader);

// Components
import './components'
import vuetify from './plugins/vuetify';

Vue.config.productionTip = false
// axios 설정
Vue.prototype.$api = api;
// lodash 설정
Vue.prototype._ = lodash;
// 공통 util 설정
Vue.prototype.common = common;

new Vue({
  store,
  router,
  vuetify,
  render: h => h(App)
}).$mount('#app')
