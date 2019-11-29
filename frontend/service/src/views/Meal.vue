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
          no-gutters
        >
          <v-col
            cols="12"
            lg="6"
            sm="8"
            md="8"
          >
            <material-card
              color="pink lighten-2"
              title="식권 QR코드"
              text="식사시 직원에게 보여주세요."
            >
              <meal-qrCode></meal-qrCode>
            </material-card>
          </v-col>
        </v-row>
      </v-container> 
    </v-content>
  </div>
</template>

<script>
  export default {
  }
</script>