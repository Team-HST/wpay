<template>
  <v-layout wrap>
    <v-flex
      sm12
      xs12
      md6
      lg6
    >
      <material-statsCard
        color="#FFD54F"
        icon="mdi-account-multiple-plus"
        title="결혼정보를 등록합니다."
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
        color="#FFD54F"
        icon="mdi-account-supervisor-circle"
        value="혼주관리"
        title="혼주정보를 관리합니다."
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