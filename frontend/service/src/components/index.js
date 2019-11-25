import Vue from 'vue'
import upperFirst from 'lodash/upperFirst'
import camelCase from 'lodash/camelCase'

/**
 * @description 전체 컴포넌트 조회
 * @param 경로, 하위 폴더 포함여부. 정규표현식
 */ 
const requireComponent = require.context(
  '@/components', true, /\.vue$/
)

/**
 * @description 컴포넌트 [forder]-[vueFile]
 */
requireComponent.keys().forEach(fileName => {
  const componentConfig = requireComponent(fileName)

  const componentName = upperFirst(
    camelCase(fileName.replace(/^\.\//, '').replace(/\.\w+$/, ''))
  )
  
  // 컴포넌트 등록
  Vue.component(componentName, componentConfig.default || componentConfig)
})