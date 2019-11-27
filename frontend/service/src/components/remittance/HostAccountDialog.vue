<template>
  <v-dialog
    v-model="this.getAccountDialog"
    persistent max-width="900px"
  >
    <div ref="diagram">
      <v-card>
        <v-card
          class="v-card--material__header"
          dark
        >
          <v-card-title
            class="headline green lighten-2"
            primary-title
          >
          혼주
          계좌
          </v-card-title>
        </v-card>
        <v-card-text>
        </v-card-text>
        <v-card-actions>
          <div class="flex-grow-1"></div>
          <v-btn
            class="font-weight-bold"
            color="blue darken-1"
            text
            @click="this.changeAccountDialog"
          > 
            닫기
          </v-btn>
        </v-card-actions>
      </v-card>
    </div>
  </v-dialog>
</template>

<script>
  import { mapGetters, mapMutations } from 'vuex';

  export default {
    computed: {
      ...mapGetters(['getAccountDialog'])
    },
    methods: {
      ...mapMutations(['changeAccountDialog'])
    }
  }
</script>