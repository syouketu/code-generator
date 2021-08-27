<template>
	<div class="menu-wrapper">
		<template>
			<router-link to="/dashboard">
				<el-menu-item index="/dashboard">
					<icon name="desktop"></icon>
					<span slot="title">首页</span>
				</el-menu-item>
			</router-link>
		</template>
		<template v-for="item in routes" v-if="!item.hidden&&item.children">
			<router-link v-if="item.children.length==0" :to="item.path" :key="item.path">
				<el-menu-item index="item.path">
					<icon v-if='item.icon' :name="item.icon"></icon>
					<span>{{item.name}}</span>
				</el-menu-item>
			</router-link>
			<el-submenu :index="item.name" :key="item.id" v-else>
				<template slot="title">
					<icon v-if='item.icon' :name="item.icon"></icon>
					<span>{{item.name}}</span>
				</template>
				<template v-for="child in item.children" v-if='item.children.length>0&&!child.hidden'>
					<router-link v-if="child.children.length==0" class="menu-indent" :to="item.path+'/'+child.path" :key="child.id">
						<el-menu-item :index="item.path+'/'+child.path">{{child.name}}</el-menu-item>
					</router-link>
				</template>
			</el-submenu>
		</template>
	</div>
</template>

<script>
import icon from 'vue-awesome/components/Icon'
export default {
  name: 'SidebarItem',
  components: { icon },
  props: {
    routes: {
      type: Array
    },
    isNest: {
      type: Boolean,
      default: false
    }
  },
  methods: {
    hasOneShowingChildren(children) {
      const showingChildren = children.filter(item => {
        return !item.hidden
      })
      if (showingChildren.length === 1) {
        return true
      }
      return false
    }
  }
}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
.fa-icon {
  width: auto;
  height: 0.9em;
  padding-right: 15px;
}
</style>

