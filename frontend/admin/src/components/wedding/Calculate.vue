<template>
    <v-content>
        <v-container>
              <v-row class="ma-4" >
                <v-flex xs6></v-flex>
                <v-flex xs6 style="text-align: right;">
                  <span class="title">혼주: 김 부 시</span>
                </v-flex>
              </v-row>
              <v-row class="ma-4 pa-5" style="border:1px solid #EC407A; border-radius: 1em;">
                <v-flex xs6>
                  <span class="title">축의금 총액</span>
                </v-flex>
                <v-flex xs6 style="text-align: right;">
                  <span class="title" color="font-weight-bold"> {{calculate.totalCelebrationPrice}} 원 </span>
                </v-flex>
                <v-flex xs6>
                  <span class="title">신랑 측</span>
                </v-flex>
                <v-flex xs6 style="text-align: right;">
                  <span class="title" color="font-weight-bold"> {{calculate.maleHostTotalCelebrationAmount}} 원 </span>
                </v-flex>
                <v-flex xs6>
                  <span class="title">신부 측</span>
                </v-flex>
                <v-flex xs6 style="text-align: right;">
                  <span class="title" color="font-weight-bold"> {{calculate.femaleHostTotalCelebrationAmount}} 원 </span>
                </v-flex>
              </v-row>
              <v-row class="ma-4 pa-5" style="border:1px solid #EC407A; border-radius: 2em;">
                <v-flex xs8>
                  <span class="title">발급된 식권 개수</span>
                </v-flex>
                <v-flex xs4 style="text-align: right;">
                  <span class="title" color="font-weight-bold"> {{calculate.totalMealTicketCount}} 개 </span>
                </v-flex>
                <v-flex class="mt-2" xs6>
                  <span class="title">총 식대비</span>
                </v-flex>
                <v-flex class="mt-2" xs6 style="text-align: right;">
                  <span class="title" color="font-weight-bold"> {{calculate.totalMealPrice}} 원 </span>
                </v-flex>
              </v-row>
              <v-row class="ma-4 pa-5" style="border:1px solid #EC407A; border-radius: 1em;">
                <v-flex xs6>
                  <span class="title">차 액</span>
                </v-flex>
                <v-flex xs6 style="text-align: right;">
                  <span class="title" color="font-weight-bold"> {{calculate.remainingAmount}} 원 </span>
                </v-flex>
              </v-row>
              <v-row class="ma-12">
                <v-flex xs2></v-flex>
                <v-flex xs4>
                  <v-btn
                    class="font-weight-bold white--text"
                    color="pink lighten-2"
                    @click="moveWeddingList"
                    >
                    목록
                  </v-btn>
                </v-flex>
                <v-flex xs4>
                  <v-btn
                    class="font-weight-bold"
                    color="amber lighten-2"
                    >
                    정산
                  </v-btn>
                </v-flex>
                <v-flex xs2></v-flex>
              </v-row>
        </v-container>
    </v-content>
</template>

<script>
import { mapGetters } from 'vuex';
import { api } from '@/utils/api';

export default {
  data: () => ({
    calculate: {
      weddingSeq: 0,            // 결혼일련번호
      totalCelebrationPrice: 0, // 총 축의금
      femaleHostTotalCelebrationAmount: 0,  // 신부 측 축의금
      maleHostTotalCelebrationAmount: 0,    // 신랑 측 축의금
      totalMealTicketCount: 0,          // 총 식권갯수
      totalMealPrice: 0,        // 총 식대비
      remainingAmount: 0              // 차액
    }
  }),
  computed: {
    ...mapGetters(['getUserData'])
  },
  created() {
    alert('thi'+this.$route.params.seq);
    this.calculate.weddingSeq = this.$route.params;
    this.service = {
      /* 정산 API */
      calculate: () => {
        api.setUserToken(this.getUserData.token);
        api.auth.get('/api/weddings/'+this.calculate.weddingSeq+'/settle')
        .then(response => {
            if (response !== "undefined") {
              this.calculate.totalCelebrationPrice = this.numberFormat(response.data.totalCelebrationAmount);
              this.calculate.femaleHostTotalCelebrationAmount = this.numberFormat(response.data.femaleHostTotalCelebrationAmount);
              this.calculate.maleHostTotalCelebrationAmount = this.numberFormat(response.data.maleHostTotalCelebrationAmount);
              this.calculate.totalMealPrice = this.numberFormat(response.data.totalMealPrice);
              this.calculate.totalMealTicketCount = this.numberFormat(response.data.totalMealTicketCount);
              this.calculate.remainingAmount = this.numberFormat(response.data.remainingAmount);
            }
        });
      }
    }
  },
  mounted() {
    this.initialize();
  },
  methods: {
    initialize: function() {
      this.service.calculate();
    },
    moveWeddingList: function() {
      this.$router.push({name:"WeddingList"});
    },
    numberFormat: function(inputNumber) {
      return inputNumber.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
    }
  }
}
</script>
<style scoped>

</style>