<template>
  <div class="dashboard-container">
    <!-- <component v-bind:is="currentRole"> </component> -->
  </div>
</template>

<script>
  import { mapGetters } from 'vuex'

export default {
    name: 'Dashboard',
    components: { },
    data() {
      return {
        currentRole: ''
      }
    },
    computed: {
      ...mapGetters([
        'name',
        'avatar',
        'email',
        'introduction',
        'roles'
      ])
    },
    created() {
    }
  }
</script>
