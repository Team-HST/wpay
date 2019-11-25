/**
 * @description router path 설정 js
 */
 export default [
  {
    path: '/login',
    name: 'Login',
    view: 'Login',
    viewName: '로그인 페이지',
    props: true
  },
  {
    path: '/register',
    name: 'Register',
    view: 'Register',
    viewName: '회원가입 페이지',
    props: true
  },
  {
    path: '/main',
    name: 'Main',
    view: 'Main',
    viewName: '메인 페이지',
    props: true
  }
]