<template>
  <div>
    <v-text-field type="text" label="아이디" v-model="user.id" />
    <v-text-field type="text" label="성명" v-model="user.name" />
    <v-text-field type="password" label="패스워드" v-model="user.password" />
    <v-text-field type="password" label="패스워드 확인" v-model="user.passwordConfirm" />
    <div class="text-center">
      <v-btn class="font-weight-bold white--text" color="pink lighten-2" @click="createUser">회원가입</v-btn>
      <v-btn
        class="font-weight-bold white--text ml-2"
        color="pink lighten-2"
        @click="moveLoginPage"
      > 취소
      </v-btn>
    </div>
  </div>
</template>

<script>
  export default {
    data() {
      return {
        user: {
          id: "",
          name: "",
          password: "",
          passwordConfirm: ""
        },
        service: {}
      };
    },
    created() {
      this.service = {
        userSingUp: () => {
          this.$http.post("/api/users/signup", this.user)
            .then(response => {
              alert("회원가입이 정상적으로 처리되었습니다.\n계좌인증페이지로 이동합니다.");
              
              // 계좌 인증 페이지 이동
              this.$http.get(`/api/users/${response.data.userResponse.sequence}/account-authentication`)
                .then(response => {
                  location.href = response.data;
                  // window.open(response.data, "_blank");
                  // this.$router.push('/login');
                })
            })
            .catch(error => {
              if (error.response.data.code === 1021) {
                alert("이미 존재하는 아이디입니다.\n다른 아이디를 사용하여주세요.");
              } else {
                alert("일시적인 오류입니다.\n관리자에게 문의하여 주세요.");
              }
            });
        }
      };
    },
    methods: {
      /**
       * @description 사용자 정보검사 및 생성
       */
      createUser: function() {
        if (
          !this.common.isNotBlank(this.user.id) ||
          !this.common.isNotBlank(this.user.name) ||
          !this.common.isNotBlank(this.user.password)
        ) {
          alert("계정정보를 입력하여 주세요.");
        } else if (this.user.password !== this.user.passwordConfirm) {
          alert("비밀번호가 일치하지 않습니다.");
        } else {
          this.service.userSingUp();
        }
      },
      /**
       * @description 로그인 페이지 이동
       */
      moveLoginPage: function() {
        this.$router.push("login");
      }
    }
  };
</script>