import Vue from 'vue'
import App from './App.vue'
import store from './store/index'
import router from '@/router'
import axios from 'axios'
import lodash from 'lodash'
import {common} from '@/utils/common'
import QrcodeVue from 'vue-qrcode-component';

// Components
import './components'

import vuetify from './plugins/vuetify';

// axios 설정
Vue.prototype.$http = axios;
Vue.prototype._ = lodash;
Vue.prototype.common = common;

new Vue({
  store,
  router,
  vuetify,
  QrcodeVue,
  render: h => h(App)
}).$mount('#app')