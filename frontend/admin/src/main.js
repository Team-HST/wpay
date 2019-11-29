import Vue from 'vue'
import App from './App.vue'
import 'vue-material-design-icons/styles.css';
import store from './store/index'
import router from '@/router'
import axios from 'axios'
import lodash from 'lodash'
import moment from 'moment'
import VueMomentJS from 'vue-momentjs'
import {common} from '@/utils/common'
import oauth from '@/utils/oauth'

// Components
import './components'
import vuetify from './plugins/vuetify';

Vue.config.productionTip = false
// axios 설정
Vue.prototype.$http = axios;
Vue.prototype._ = lodash;
Vue.prototype.common = common;

Vue.use(VueMomentJS, moment);

new Vue({
  store,
  router,
  vuetify,
  oauth,
  render: h => h(App)
}).$mount('#app')