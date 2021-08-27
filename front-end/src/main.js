// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue';
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import '@/styles/index.scss' // global css
import App from './App';
import router from './router'
import store from './store'
import * as filters from './filters'; // 全局vue filter
import 'vue-awesome/icons'//引入fontawsome
import './mock';  // 该项目所有请求使用mockjs模拟
import hljs from 'highlight.js' //导入代码高亮文件
import 'highlight.js/styles/monokai-sublime.css'  //导入代码高亮样式

// register global utility filters.
Object.keys(filters).forEach(key => {
  Vue.filter(key, filters[key])
});

Vue.use(ElementUI)
Vue.config.productionTip = false;

//自定义一个代码高亮指令
Vue.directive('highlight',function (el) {
  let highlight = el.querySelectorAll('pre code');
  highlight.forEach((block)=>{
      hljs.highlightBlock(block)
  })
})

global.api_url = process.env.BASE_API;

new Vue({
  el: '#app',
  router,
  store,
  template: '<App/>',
  components: { App }
})


