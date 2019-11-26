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
        path: '/main',
        name: 'Main',
        view: 'Main',
        viewName: '메인 페이지',
        props: true
    },
    {
        path: '/weddingMatch',
        name: 'WeddingMatch',
        view: 'WeddingMatch',
        viewName: '매칭 페이지',
        props: true
    },
    {
        path: '/QrGenerate',
        name: 'QrGenerate',
        view: 'QrGenerate',
        viewName: 'QR코드 발급 페이지',
        props: true
    }
]