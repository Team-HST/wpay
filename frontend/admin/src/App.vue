<template>
  <v-app>
    <layout-header></layout-header>
    <router-view></router-view>
    <layout-footer></layout-footer>
  </v-app>
</template>

<script>
export default {
  components: {
  },
  data: () => ({
    
  }),
};
</script>

<style lang="scss">
@import "@/assets/scss/vuetify/index.scss";
/* Remove in 1.2 */
.v-datatable thead th.column.sortable i {
  vertical-align: unset;
}
</style>