<template>
  <div>
    <material-card
      color="pink lighten-2"
      title="W-Pay에 오신것을 환영합니다."
      text="계정정보를 입력하여 주세요."
    >
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
        >
          로그인
        </v-btn>
      </div>
    </material-card>
    <v-row>
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
export default {
  data() {
    return {
      user: {
        id: null,
        password: null,
      },
      service: {}
    }
  },
  created() {
    this.service = {
      
    }
  }
}
</script>