import Vue from 'vue'
import App from './App.vue'
import store from './store/index'
import router from '@/router'
import lodash from 'lodash'
import { common } from '@/utils/common'
import { api } from '@/utils/api'

// Components
import './components'

import vuetify from './plugins/vuetify';

// axios 설정
Vue.prototype._ = lodash;
Vue.prototype.common = common;
Vue.prototype.$api = api;

new Vue({
  store,
  router,
  vuetify,
  render: h => h(App)
}).$mount('#app')
