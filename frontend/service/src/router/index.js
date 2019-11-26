import Vue from 'vue'
import Router from 'vue-router'
// route paths.js
import paths from './paths'

function route (path, view, name, props, isSingleLayout) {
  return {
    name: name || view,
    path,
    props,
    isSingleLayout,
    component: (resolve) => import(
        `@/views/${view}.vue`
    ).then(resolve)
  }
}

Vue.use(Router);

const router = new Router({
  mode: 'history',
  routes: paths.map((path) => {
    return route(path.path, path.view, path.name, path.props, path.isSingleLayout)
  }).concat([
    {path: '*', redirect: '/main'} 
  ])
})

export default router;