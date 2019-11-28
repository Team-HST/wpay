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
        title="축의금을 송금합니다"
        value="송금"
        class="menu-btn"
        @click="moveRemittancePage"
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
        title="식권을 확인합니다."
        value="식권"
        class="menu-btn"
        @click="moveMealPage"
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
        icon="mdi-account"
        title="계정정보를 확인합니다."
        value="마이페이지"
        class="menu-btn"
        @click="meveMyPage"
      >
      </material-statsCard>
    </v-flex>
  </v-layout>
</template>

<script>
export default {
  methods: {
    moveRemittancePage: function() {
      this.$router.push('/remittance');
    },
    moveMealPage: function() {
      this.$router.push('/meal');
    },
    meveMyPage: function() {
      this.$router.push('/mypage');
    }
  }
}
</script>

<style scoped>
  .menu-btn {cursor: pointer;}
</style>