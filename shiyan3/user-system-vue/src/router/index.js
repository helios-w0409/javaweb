import { createRouter, createWebHistory } from 'vue-router'

import Register from '../views/Register.vue'
import Login from '../views/Login.vue'
import Home from '../views/Home.vue'
import AdminUsers from '../views/AdminUsers.vue'

const routes = [
    { path: '/', redirect: '/login' },
    { path: '/register', name: 'Register', component: Register },
    { path: '/login', name: 'Login', component: Login },
    { path: '/home', name: 'Home', component: Home },
    { path: '/admin/users', name: 'AdminUsers', component: AdminUsers },
    {
        path: '/books',
        name: 'Books',

        component: () => import('../views/Books.vue') // ✅ 正确！

    }

]

const router = createRouter({
    history: createWebHistory(),
    routes
})

export default router
