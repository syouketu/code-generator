import Vue from 'vue';
import Router from 'vue-router';
import { deepClone } from 'utils';

const _import = require('./_import');
// in development env not use Lazy Loading,because Lazy Loading large page will cause webpack hot update too slow.so only in production use Lazy Loading

/* layout */
import Layout from '../views/layout/Layout';

/* login */
const Login = _import('login/index');
/* dashboard */
const dashboard = _import('dashboard/index');

Vue.use(Router);

export const constantRouterMap = [{ path: '/login', component: Login, hidden: true },
{
  path: '',
  component: Layout,
  redirect: 'dashboard',
  icon: 'desktop',
  name: '首页',
  hidden: true,
  children: [{
    path: 'dashboard',
    component: _import('dashboard/index'),
    name: '首页',
    children: []
  }]
}, {
  path: '/project',
  name: '生成项目',
  component: Layout,
  redirct: '/project/index',
  icon: 'database',
  hidden: false,
  children: [{
    path: 'project',
    component: _import('project/project'),
    name: '项目管理',
    children: []
  }, {
    path: 'database',
    component: _import('project/database/database'),
    name: '数据库管理',
    children: []
  }, {
    path: 'table',
    component: _import('project/database/table'),
    name: '数据库表',
    hidden: true,
    children: []
  }, {
    path: 'column',
    component: _import('project/database/column'),
    name: '数据表字段',
    hidden: true,
    children: []
  }, {
    path: 'template',
    component: _import('project/template'),
    name: '模板管理',
    children: []
  }]
}, {
  path: '/system',
  name: '系统管理',
  component: Layout,
  redirct: '/system/index',
  icon: 'database',
  hidden: false,
  children: [{
    path: 'template',
    component: _import('system/bs_template'),
    name: '初始模板',
    children: []
  }]
}];

export default new Router({
  routes: constantRouterMap
});