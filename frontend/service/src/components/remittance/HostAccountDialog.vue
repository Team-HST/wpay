<template>
  <v-dialog
    v-model="this.getIsAccountDialog"
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
              <div class="title font-weight-bold">혼주 {{ this.getHostData.name }}</div>
            </v-flex>
            <v-flex xs12 sm12 lg12 text-xs-right text-sm-center text-lg-center>
              <div class="subtitle-1">{{ this.getHostData.bankName }}) {{ this.getHostData.bankAccountNumber }}</div>
            </v-flex>
          </v-card-title>
        </v-card>
        <v-card-text class="pb-0">
          <v-text-field
            type="text"
            label="금액"
            v-model="amount"
          />
          <v-text-field
            type="text"
            label="메모"
            v-model="comment"
          />
          <div class="text-center">
            <v-btn
              class="font-weight-bold white--text"
              color="green"
              @click="this.sendHostRemittance"
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
    data() {
      return {
        comment: '',
        amount: 0
      }
    },
    computed: {
      ...mapGetters(['getIsAccountDialog', 'getIsMealDialog', 'getHostData', 'getUserData'])
    },
    created() {
      this.service = {
        /**
         * 축의금 송금 API
         */
        sendHostRemittance: () => {
          // 송금 데이터
          const remittanceData = {
            'guestSequence': this.getUserData.sequence,
            'hostSequence': this.getHostData.sequence,
            'weddingSequence': this.getHostData.weddingSequence,
            'comment': this.comment,
            "amount": this.amount
          }

          this.$http.post('/api/remittance/transfer', remittanceData)
            .then(() => {
              this.changeIsAccountDialog();
              this.changeIsMealDialog();
            })
            .catch(error => {
              console.log(error);
              alert('일시적인 오류입니다./n관리자에게 문의하여 주세요.');
            })
        }
      }
    },
    mounted() {
      // 송금 다이얼로그가 켜져있는 경우 닫음
      if (this.getIsAccountDialog) {
        this.changeIsAccountDialog();
      }
      // 식권 다이얼로그가 켜져있는 경우 닫음
      if (this.getIsMealDialog) {
        this.changeIsMealDialog();
      }
    },
    methods: {
      ...mapMutations(['changeIsAccountDialog', 'changeIsMealDialog']),
      /**
       * @description 계좌송금 팝업창 종료 이벤트
       */
      closeAccountDialog: function() {
        if (confirm('계좌송금을 취소하시겠습니까?')) {
          this.changeIsAccountDialog();
          this.$router.push('/main');
        }
      },
      /**
       * @description 혼주 계좌 송금
       */
      sendHostRemittance: function() {
        if (confirm('혼주에게 축의금을 송금하시겠습니까?')) {
          this.service.sendHostRemittance();
        }
      }
    }
  }
</script>