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
import { mapActions } from 'vuex';
export default {
  data() {
    return {
      user: {
        id: null,
        password: null,
      }
    }
  },
  methods: {
    ...mapActions(['userSignIn']),
    loginUser: function() {
      this.userSignIn(this.user).then(response => {
        if (response !== undefined) {
            this.moveMain();
        }  
      });
      // this.fnGoMain();
    },
    moveMain: function() {
        this.$router.push({name:"Main"});
    }
  }
}
</script>