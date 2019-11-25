<template>
    <div>
        <v-row>
            <v-flex xs8>
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
            </v-flex>
            <v-flex xs1></v-flex>
            <v-flex xs3>
                    <v-btn
                    class="font-weight-bold white--text"
                    color="pink lighten-2"
                    @click="loginUser"
                    x-large
                    >
                    로그인
                    </v-btn>
            </v-flex>
        </v-row>
        <v-row style="margin-top:10px;">
        <div class="pl-5">
            아이디 / 패스워드 찾기
        </div>
        <div class="px-10">
            |
        </div>
        <div>
        회원가입
        </div>
        </v-row>
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
            this.fnGoMain();
        }  
      });
      // this.fnGoMain();
    },
    fnGoMain() {
        this.$router.push({name:"Main"});
    }
  }
}
</script>