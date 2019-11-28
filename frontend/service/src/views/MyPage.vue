<template>
  <div>
    <layout-header></layout-header>
    <v-content>
      <v-container
        class="mt-8 fill-height grid-list-xl"
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
              title="마이페이지"
              text="지출과 정산내역을 확인합니다."
            >
              <mypage-tab></mypage-tab>
            </material-card>
          </v-col>
        </v-row>
      </v-container> 
    </v-content>
  </div>
</template>

<script>
  export default {
    data() {
      return {
        tab: null
      }
    }
  }
</script>