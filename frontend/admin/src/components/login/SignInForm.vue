<template>
  <div>
    <v-text-field
      type="text"
      label="아이디"
      v-model="user.id"
    />
    <v-text-field
      type="password"
      label="패스워드"
      v-model="user.password"
    />
    <div class="text-center">
      <v-btn
        class="font-weight-bold white--text"
        color="pink lighten-2"
        @click="loginUser"
      >
        로그인
      </v-btn>
    </div>
  </div>
</template>

<script>
import { mapActions, mapGetters, mapMutations } from 'vuex';

export default {
  data() {
    return {
      user: {
        id: null,
        password: null,
      }
    }
  },
  mounted() {
    // 사용자 정보 초기화
    this.setUserData({});
  },
  computed: {
    ...mapGetters(['getUserData'])
  },
  methods: {
    ...mapActions(['userSignIn']),
    ...mapMutations(['setUserData']),

    loginUser: function() {
      this.userSignIn(this.user)
        .then(() => {
        if (this.common.isNotBlank(this.getUserData.token)) {
          this.moveMain();
        }
      })
      // this.fnGoMain();
    },
    moveMain: function() {
        this.$router.push({name:"Main"});
    }
  }
}
</script>