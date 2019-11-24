import Vue from 'vue'
import App from './App.vue'
import store from './store/index'
import router from '@/router'
import axios from 'axios'
import lodash from 'lodash'
// import material from "./material";

// Components
import './components'

import vuetify from './plugins/vuetify';

// axios 설정
Vue.prototype.$http = axios;
Vue.prototype._ = lodash;

// Vue.use(material);

new Vue({
  store,
  router,
  vuetify,
  render: h => h(App)
}).$mount('#app')
