/**
 * @description router path 설정 js
 */
 export default [
  {
    path: '/',
    name: '',
    view: ''
  },
  {
    path: '/login',
    name: 'Login',
    view: 'Login',
    viewName: '로그인페이지',
    props: true
  },
  {
    path: '/register',
    name: 'Register',
    view: 'Register',
    viewName: '회원가입페이지',
    props: true
  },
  {
    path: '/main',
    name: 'Main',
    view: 'Main',
    viewName: '메인페이지',
    props: true
  },
  {
    path: '/mypage',
    name: 'MyPage',
    view: 'MyPage',
    viewName: '마이페이지',
    props: true
  },
  {
    path: '/remittance',
    name: 'Remittance',
    view: 'Remittance',
    viewName: '송금페이지',
    props: true
  },
  {
    path: '/meal',
    name: 'Meal',
    view: 'Meal',
    viewName: '식권QR페이지',
    props: true
  }
]