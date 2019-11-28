<template>
  <div>
    <layout-header></layout-header>
    <v-content>
      <v-container
        class="mt-10 fill-height grid-list-xl"
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
              title="송금하기"
              text="혼주의 QR코드를 읽어주세요."
            >
              <remittance-qrReader></remittance-qrReader>
            </material-card>
          </v-col>
        </v-row>
      </v-container>

      <remittance-hostAccountDialog></remittance-hostAccountDialog>
    </v-content>
  </div>
</template>

<script>
export default {
}
</script>