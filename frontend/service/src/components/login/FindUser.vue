<template>
  <v-row>
    <div class="pl-5 div-focus">
      아이디 / 패스워드 찾기
    </div>
    <div class="px-10">
      |
    </div>
    <div 
      class="div-focus"
      @click="moveSignInPage"
    >
      회원가입
    </div>
  </v-row>
</template>

<script>
export default {
  methods: {
    moveSignInPage: function() {
      this.$router.push('register');
    }
  }
}
</script>

<style scoped>
  .div-focus {cursor: pointer;}
</style>