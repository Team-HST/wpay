<template>
  <v-dialog
    v-model="this.getIsMealDialog"
    persistent max-width="600px"
  >
    <div
      ref="diagram"
      v-if="meal1Step"
    >
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
              <div class="title font-weight-bold">식권 발급 서비스</div>
            </v-flex>
            <v-flex xs12 sm12 lg12 text-xs-right text-sm-center text-lg-center>
              <div class="subtitle-1">식권코드가 발급될 예정입니다.</div>
              <div class="subtitle-1">식권을 받으시겠습니까?</div>
            </v-flex>
          </v-card-title>
        </v-card>
        <v-card-text>
          <div class="text-center">
            <v-btn
              class="font-weight-bold white--text ml-2"
              color="green"
              @click="changeMealStep"
            > 예
            </v-btn>
            <v-btn
              class="font-weight-bold white--text ml-2"
              color="green"
              @click="cancleMealIssue"
            > 아니오
            </v-btn>
          </div>
        </v-card-text>
      </v-card>
    </div>

    <div
      ref="diagram"
      v-if="!meal1Step"
    >
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
              <div class="title font-weight-bold">식권 발급 서비스</div>
            </v-flex>
            <v-flex xs12 sm12 lg12 text-xs-right text-sm-center text-lg-center>
              <div class="subtitle-1">원하시는 식권 개수를 입력하여주세요.</div>
            </v-flex>
          </v-card-title>
        </v-card>
        <v-card-text class="pb-0">
          <v-text-field
            type="text"
            label="식권개수"
            class="pb-0"
            v-model="mealCount"
          />
          <div class="text-right">
            <v-btn 
              class="font-weight-bold white--text ma-3"
              color="green"
              @click="issueMeal"
            >발급
            </v-btn>
            <v-btn 
              class="font-weight-bold white--text" 
              color="green"
              @click="cancleMealIssue"
            >취소
            </v-btn>
          </div>
        </v-card-text>
      </v-card>
    </div>
  </v-dialog>
</template>

<script>
import { mapGetters, mapMutations } from 'vuex';

export default {
  data() {
    return {
      meal1Step: true,
      mealCount: 0,
      service: {}
    }
  },
  computed: {
    ...mapGetters(['getIsMealDialog', 'getHostData', 'getUserData'])
  },
  created() {
    this.service = {
      getMealTicket: () => {
        this.$http.post("/api/meal-tickets/issue", {
          'mealTicketCount': this.mealCount,
          'guestSeq': this.getUserData.sequence,
          'weddingSeq': this.getHostData.weddingSequence
        })
          .then(() => {
            alert('식권이 발급 되었습니다.');
            this.$router.push('/main');
          })
          .catch(error => {
            console.log(error);
          });
      }
    }
  },
  methods: {
    ...mapMutations(['changeIsMealDialog']),
    changeMealStep: function() {
      this.meal1Step = false;
    },
    /**
     * @description 식권 발급 취소
     */
    cancleMealIssue: function() {
      if (confirm('식권발급을 취소하시겠습니까?')) {
        this.changeIsMealDialog();
        this.$router.push('/main');
      }
    },
    /**
     * 식권 발급 이벤트
     */
    issueMeal: function() {
      const numberReg = /^[0-9]*$/
      if (!numberReg.test(this.mealCount)) {
        alert('숫자만 입력하세요.');
      } else if (this.mealCount > 50) {
        alert('식권 50개 이상은 관리자에게 문의하여주세요.');
      } else if (this.mealCount == 0) {
        alert('식권은 1개 이상으로 설정하여 주세요.');
      } else {
        this.service.getMealTicket();
      }
    }
  }
}
</script>