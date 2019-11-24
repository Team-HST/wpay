<template>
  <div>
    <material-card
      color="green"
      title="W-Pay에 오신것을 환영합니다."
      text="계정정보를 입력하여 주세요."
    >
      <v-text-field
        class="input"
        label="아이디"
      />
      <v-text-field
        class="input"
        label="패스워드"
      />
      <div class="text-center">
        <v-btn
          class="font-weight-bold white--text"
          color="green"
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
    
}
</script>