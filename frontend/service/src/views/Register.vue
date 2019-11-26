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
            title="W-Pay 회원가입"
            text="사용 할 개인정보를 입력하세요."
          >
            <register-signUpForm></register-signUpForm>
          </material-card>
        </v-col>
      </v-row>
    </v-container> 
  </v-content>
</template>

<script>
export default {
  
}
</script>