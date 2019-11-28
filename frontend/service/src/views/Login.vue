<template>
  <v-content>
    <v-container
      class="fill-height"
      fluid
    >
      <v-row
        align="center"
        justify="center"
      >
        <v-col
          cols="12"
          lg="6"
          sm="8"
          md="8"
        >
          <material-card
            color="pink lighten-2"
            title="W-Pay에 오신것을 환영합니다."
            text="계정정보를 입력하여 주세요."
          >
            <login-signInForm></login-signInForm>
          </material-card>
          <login-findUser></login-findUser>
        </v-col>
      </v-row>
    </v-container> 
  </v-content>
</template>
<script>
  export default {
  }
</script>
