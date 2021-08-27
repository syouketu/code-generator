<template>
  <el-scrollbar wrapClass="scrollbar-wrapper">
    <!-- <div class="profile" v-if="sidebar.opened">
      <div class="user-avatar">
        <img :src="avatar" class="avatar">
      </div>
      <div>
        <h4>{{name}}</h4>
        <small>{{roleName}}</small>
      </div>
    </div> -->
    <el-menu mode="vertical" :show-timeout="200" :default-active="$route.path" :collapse="isCollapse" background-color="#304156" text-color="#bfcbd9" active-text-color="#409EFF">
      <sidebar-item :routes='menus'></sidebar-item>
    </el-menu>
  </el-scrollbar>
</template>

<script>
import { mapGetters } from 'vuex';
import SidebarItem from './SidebarItem';
import { constantRouterMap } from 'src/router';
import defaultAvatar from '@/assets/images/default-avatar.png';

export default {
  components: { SidebarItem },
  computed: {
    ...mapGetters(['sidebar', 'name', 'roleName']),
    isCollapse() {
      return !this.sidebar.opened
    },
    avatar() {
      if (this.$store.state.user.avatar) {
        return this.$store.state.user.avatar
      } else {
        return defaultAvatar
      }
    },
    menus() {
      return constantRouterMap
    }
  }
}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
</style>
