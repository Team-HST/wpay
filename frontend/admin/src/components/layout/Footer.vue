<template>
    <v-bottom-navigation v-model="bottomNav" app background-color="pink lighten-2" grow dark shift fixed>
      <v-btn :to="{name: 'todoPage'}" height="100%">
        <span>ToDo</span><v-icon>mdi-newspaper-variant-multiple</v-icon>
      </v-btn>
      <v-btn to="/settings" height="100%">
        <span>혼주관리</span><v-icon>mdi-settings</v-icon>
      </v-btn>
    </v-bottom-navigation>
</template>

<script>
export default {
    data: () => ({
    bottomNav: 0
  })
}
</script>