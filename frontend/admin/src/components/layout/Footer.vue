<template>
    <v-bottom-navigation v-model="bottomNav" app background-color="pink lighten-2" grow dark shift fixed>
      <v-btn height="100%">
        <v-icon>mdi-newspaper-variant-multiple</v-icon>
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