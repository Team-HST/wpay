/* eslint-disable no-console */
import Vue from 'vue'
import Router from 'vue-router'
import store from '@/store/index'
import { common } from '@/utils/common'
// route paths.js
import paths from './paths'

const requireAuth = (to, from, next) => {
  if (to.name === 'Login' || to.name === 'Register') {
    return next();
  } else if (to.name === '') {
    // 로그인이 되어있는 경우 main 이동 아닐 경우 login 이동
    if (common.isNotBlank(store.state.user.token)) {
      next('/main');
    } else {
      next('/login');
    }
  } else {
    if (common.isNotBlank(store.state.user.token)) {
      return next();
    } else {
      alert('로그인 후 사용하여 주세요.');
      next('/login');
    }
  }
};

function route (path, view, name, props) {
  return {
    name: name || view,
    path,
    props,
    component: (resolve) => import(
        `@/views/${view}.vue`
    ).then(resolve),
    beforeEnter: requireAuth
  }
}

Vue.use(Router);

const router = new Router({
  mode: 'history',
  routes: paths.map((path) => {
    return route(path.path, path.view, path.name, path.props)
  }).concat([
    {path: '*', redirect: '/'} 
  ])
})

export default router;