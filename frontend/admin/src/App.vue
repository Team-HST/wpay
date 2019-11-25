<template>
  <v-app>
    <v-app-bar app dark color="teal" clipped-left>      
        <h3>Todo.io</h3>
      <v-spacer></v-spacer>

      <v-btn
        href="https://github.com/HyunGyu-Lee/daily-todo"
        target="_blank"
        text
      >
        <span class="mr-2">contact</span>
        <v-icon>mdi-open-in-new</v-icon>
      </v-btn>
    </v-app-bar>

    <router-view></router-view>

    <v-bottom-navigation v-model="bottomNav" app background-color="teal" grow dark shift fixed>
      <v-btn :to="{name: 'todoPage'}" height="100%">
        <span>ToDo</span><v-icon>mdi-newspaper-variant-multiple</v-icon>
      </v-btn>
      <v-btn to="/settings" height="100%">
        <span>Settings</span><v-icon>mdi-settings</v-icon>
      </v-btn>
    </v-bottom-navigation>
  </v-app>
</template>

<script>
export default {
  components: {
  },
  data: () => ({
    bottomNav: 0
  }),
};
</script>

<style lang="scss">
@import "@/assets/scss/vuetify/index.scss";
/* Remove in 1.2 */
.v-datatable thead th.column.sortable i {
  vertical-align: unset;
}
.v-application {
  font-family: "KoPub Dotum" !important;
}
</style>