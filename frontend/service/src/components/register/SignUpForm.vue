<template>
    <material-card
      color="pink lighten-2"
      title="W-Pay 회원가입"
      text="사용 할 개인정보를 입력하세요."
    >
      <v-text-field
        type="text"
        label="아이디"
        v-model="user.id"
      />
      <v-text-field
        type="text"
        label="성명"
        v-model="user.name"
      />
      <v-text-field
        type="password"
        label="패스워드"
        v-model="user.password"
      />
      <v-text-field
        type="password"
        label="패스워드 확인"
        v-model="user.passwordConfirm"
      />
      <div class="text-right">
         <v-btn
          class="font-weight-bold white--text"
          color="blue-grey"
        >
          계좌인증
        </v-btn>
      </div>
      <div class="text-center">
        <v-btn
          class="font-weight-bold white--text"
          color="pink lighten-2"
          @click="createUser"
        >
          회원가입
        </v-btn>
      </div>
    </material-card>
</template>

<script>
export default {
    data() {
      return {
        user: {
          id: '',
          name: '',
          password: '',
          passwordConfirm: ''
        },
        service: {}
      }
    },
    created() {
      this.service = {
        userSingUp: () => {
          this.$api.basic.post('/api/users/signup', this.user)
          .then(response => {
            // eslint-disable-next-line no-console
            console.log(response);
          }).
          catch(error => {
            if (error.response.data.code === 201) {
              alert('이미 존재하는 아이디입니다.\n다른 아이디를 사용하여주세요.');
            } else {
              alert("일시적인 오류입니다.\n다시 시도하여 주세요.");
            }
          });
        }
      }
   },
   methods: {
     createUser: function() {
       if (!this.common.isNotBlank(this.user.id) || !this.common.isNotBlank(this.user.name) 
          || !this.common.isNotBlank(this.user.password)) {
         alert('계정정보를 입력하여 주세요.');
       } else if (this.user.password !== this.user.passwordConfirm) {
         alert('비밀번호가 일치하지 않습니다.')
       } else {
         this.service.userSingUp();
       }
     }
   }
}
</script>>