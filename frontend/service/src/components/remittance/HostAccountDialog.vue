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
            class="headline green lighten-2 text-center"
            primary-title
          >
            <v-flex xs12 sm12 lg12 text-xs-center text-sm-center text-lg-center>
              <div class="title font-weight-bold">혼주 O O O</div>
            </v-flex>
            <v-flex xs12 sm12 lg12 text-xs-right text-sm-center text-lg-center>
              <div class="subtitle-1">우리) 010 22222 ***</div>
            </v-flex>
          </v-card-title>
        </v-card>
        <v-card-text class="pb-0">
              <v-text-field
                type="text"
                label="이름"
              />
              <v-text-field
                type="text"
                label="금액"
              />
              <v-text-field
                type="text"
                label="메모"
              />
              <div class="text-center">
                <v-btn
                  class="font-weight-bold white--text"
                  color="green"
                >
                  송금하기
                </v-btn>
              </div>
        </v-card-text>
        <v-card-actions class="pa-0 pb-2">
          <div class="flex-grow-1"></div>
          <v-btn
            class="font-weight-bold"
            color="blue darken-1"
            text
            @click="this.closeAccountDialog"
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
    mounted() {
      // 팝업이 켜져있는 경우 닫음
      if (this.getAccountDialog) {
        this.changeAccountDialog();
      }
    },
    computed: {
      ...mapGetters(['getAccountDialog'])
    },
    methods: {
      ...mapMutations(['changeAccountDialog']),

      /**
       * @description 계좌송금 팝업창 종료 이벤트
       */
      closeAccountDialog: function() {
        if (confirm('계좌송금을 취소하시겠습니까?')) {
          this.changeAccountDialog();
          this.$router.push('/main');
        }
      }
    }
  }
</script>