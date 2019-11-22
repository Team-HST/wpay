import Vue from 'vue'
import App from './App.vue'
import store from './store/index'
import axios from 'axios'
import lodash from 'lodash'

// Components
import './components'

// axios 설정
Vue.prototype.$http = axios;
Vue.prototype._ = lodash;

new Vue({
  store,
  render: h => h(App),
}).$mount('#app')
