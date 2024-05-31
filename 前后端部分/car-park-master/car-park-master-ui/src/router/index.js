import Vue from 'vue'
import VueRouter from 'vue-router'

// 解决导航栏或者底部导航tabBar中的vue-router在3.0版本以上频繁点击菜单报错的问题。
const originalPush = VueRouter.prototype.push
VueRouter.prototype.push = function push (location) {
  return originalPush.call(this, location).catch(err => err)
}

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'Manager',
    component: () => import('../views/Manager.vue'),
    redirect: '/home',  // 重定向到主页
    children: [
      { path: '403', name: 'Auth', meta: { name: '无权限' }, component: () => import('../views/manager/Auth') },
      { path: 'home', name: 'Home', meta: { name: '系统首页' }, component: () => import('../views/manager/Home') },
      { path: 'user', name: 'User', meta: { name: '用户信息' }, component: () => import('../views/manager/User') },
      { path: 'car', name: 'Car', meta: { name: '停车信息' }, component: () => import('../views/manager/Car') },
      { path: 'record', name: 'Record', meta: { name: '停车记录' }, component: () => import('../views/manager/Record') },
      { path: 'person', name: 'Person', meta: { name: '个人信息' }, component: () => import('../views/manager/Person') },
      { path: 'password', name: 'Password', meta: { name: '修改密码' }, component: () => import('../views/manager/Password') },
      { path: 'check', name: 'Check', meta: { name: '车辆识别' }, component: () => import('../views/manager/Check') },
      { path: 'parkinglots', name: 'Parkinglots', meta: { name: '停车场管理' }, component: () => import('../views/manager/Parkinglots') },
      { path: 'logs', name: 'Logs', meta: { name: '系统日志' }, component: () => import('../views/manager/Logs') },
      { path: 'charts', name: 'Charts', meta: { name: '数据统计' }, component: () => import('../views/manager/Charts') },
      { path: 'orders', name: 'Orders', meta: { name: '订单管理' }, component: () => import('../views/manager/Orders') },
      { path: 'members', name: 'Members', meta: { name: '会员管理' }, component: () => import('../views/manager/Members') },
      { path: 'display', name: 'Display', meta: { name: '模拟显示屏' }, component: () => import('../views/manager/Display') },
    ]
  },
  { path: '/login', name: 'Login', meta: { name: '登录' }, component: () => import('../views/Login.vue') },
  { path: '/register', name: 'Register', meta: { name: '注册' }, component: () => import('../views/Register.vue') },
  { path: '*', name: '404', meta: { name: '无法访问' }, component: () => import('../views/404.vue') },
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

router.beforeEach((to, from, next) => {
  // to 是到达的路由信息
  // from 是开源的路由信息
  // next 是帮助我们跳转路由的函数
  let user = JSON.parse(localStorage.getItem('honey-user') || '{}')
  // console.log(user);
  //let adminPaths = ['/auth/login']

  if (to.path === '/login') next()
  const token = localStorage.getItem("token");
  // 增加判断条件
  if (!user) return next('/login')
  next()

})

export default router
