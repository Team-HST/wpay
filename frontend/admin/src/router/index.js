import Vue from 'vue'
import Router from 'vue-router'

import paths from './path'

function route (path, view, name, props, isSingleLayout) {
    return {
        name: name || view,
        path,
        props,
        isSingleLayout,
        component: (resolve) => import (
            `@/views/${view}.vue`
        ).then(resolve)
    }
}

Vue.use(Router);

// Create a new router
const router = new Router({
    mode: 'history',
    routes: paths.map(path => route(path.path, path.view, path.name, path.props, path.isSingleLayout)).concat([
        { path : '*', redirect : '/main' }
    ])
})

export default router