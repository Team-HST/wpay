<template>
  <v-layout wrap>
    <v-flex
      sm12
      xs12
      md6
      lg6
    >
      <material-statsCard
        color="green"
        icon="mdi-cash-multiple"
        value="매칭"
        class="menu-btn"
        @click="moveWeddingMatch"
      >
      </material-statsCard>
    </v-flex>
    <v-flex
      sm12
      xs12
      md6
      lg6
    >
      <material-statsCard
        color="green"
        icon="mdi-silverware"
        value="혼주관리"
        class="menu-btn"
        @click="moveWeddingList"
      >
      </material-statsCard>
    </v-flex>
  </v-layout>
</template>

<script>
export default {
  methods: {
    moveWeddingMatch: function() {
      this.$router.push({name:"WeddingMatch"});
    },
    moveWeddingList: function() {
      this.$router.push({name:"WeddingList"});
    }
  }
}
</script>

<style scoped>
  .menu-btn {cursor: pointer;}
</style>