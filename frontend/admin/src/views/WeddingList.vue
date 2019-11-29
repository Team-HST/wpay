<template>
  <div>
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
              title="혼주관리"
              text="혼주를 선택하세요."
            >
              <wedding-list></wedding-list>
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