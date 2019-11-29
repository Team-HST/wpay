<template>
    <v-content class="pa-0">
        <v-container>
              <v-row class="ma-4" >
                <v-flex xs6 style="text-align: left;">
                  <span class="title font-weight-bold">신랑</span>
                  <span class="title"> {{calculate.maleHostName}} </span>
                </v-flex>
                <v-flex xs6 style="text-align: right;">
                  <span class="title font-weight-bold">신부</span>
                  <span class="title"> {{calculate.femaleHostName}}</span>
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
                  <span style="font-size: 0.9em;">신랑 측</span>
                </v-flex>
                <v-flex xs6 style="text-align: right;">
                  <span color="font-weight-bold" style="font-size: 0.9em;"> {{calculate.maleHostTotalCelebrationAmount}} 원 </span>
                </v-flex>
                <v-flex xs6>
                  <span style="font-size: 0.9em;">신부 측</span>
                </v-flex>
                <v-flex xs6 style="text-align: right;">
                  <span color="font-weight-bold" style="font-size: 0.9em;"> {{calculate.femaleHostTotalCelebrationAmount}} 원 </span>
                </v-flex>
              </v-row>
              <v-row class="ma-4 pa-5" style="border:1px solid #EC407A; border-radius: 2em;">
                <v-flex xs4></v-flex>
                <v-flex xs8 style="text-align: right;">
                  <span class="font-weight-bold red--text" style="font-size: 0.9em;">* 1인 식사비 : {{calculate.mealTicketPrice}} 원</span>
                </v-flex>
                <v-flex xs8>
                  <span class="title">발급 식권 개수</span>
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
              <v-row class="ma-6">
                <v-flex xs2></v-flex>
                <v-flex xs4 style="text-align: center;">
                  <v-btn
                    class="font-weight-bold white--text"
                    color="pink lighten-2"
                    @click="moveWeddingList"
                    >
                    목록
                  </v-btn>
                </v-flex>
                <v-flex xs4 style="text-align: center;">
                  <v-btn
                    class="font-weight-bold"
                    color="amber lighten-2"
                    @click="createCalculate"
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
import $http from 'axios';

export default {
  data: () => ({
    calculate: {
      weddingSeq: 0,            // 결혼일련번호
      totalCelebrationPrice: 0, // 총 축의금
      femaleHostName: '',       // 신부 이름
      maleHostName: '',         // 신랑 이름
      femaleHostTotalCelebrationAmount: 0,  // 신부 측 축의금
      maleHostTotalCelebrationAmount: 0,    // 신랑 측 축의금
      mealTicketPrice: 0,       // 식권 금액
      totalMealTicketCount: 0,          // 총 식권갯수
      totalMealPrice: 0,        // 총 식대비
      remainingAmount: 0              // 차액
    }
  }),
  computed: {
    ...mapGetters(['getUserData'])
  },
  created() {
    this.calculate.weddingSeq = this.$route.params.seq;
    this.service = {
      /* 정산 API */
      getCalculate: () => {
        $http.get('/api/weddings/'+this.calculate.weddingSeq+'/current-settlement')
        .then(response => {
            if (response !== "undefined") {
              this.calculate.maleHostName = response.data.maleHostName;
              this.calculate.femaleHostName = response.data.femaleHostName;
              this.calculate.totalCelebrationPrice = this.numberFormat(response.data.totalCelebrationAmount);
              this.calculate.femaleHostTotalCelebrationAmount = this.numberFormat(response.data.femaleHostTotalCelebrationAmount);
              this.calculate.maleHostTotalCelebrationAmount = this.numberFormat(response.data.maleHostTotalCelebrationAmount);
              this.calculate.totalMealPrice = this.numberFormat(response.data.totalMealPrice);
              this.calculate.totalMealTicketCount = this.numberFormat(response.data.totalMealTicketCount);
              this.calculate.remainingAmount = this.numberFormat(response.data.remainingAmount);
              this.calculate.mealTicketPrice = this.numberFormat(response.data.mealTicketPrice);
            }
        });
      },
      createCalculate: () => {
        $http.post('/api/weddings/'+this.calculate.weddingSeq+'/settle')
        .then(response => {
          if (response !== "undefined") {
            alert("정산이 완료되었습니다.");
          }
        })
      }
    }
  },
  mounted() {
    this.initialize();
  },
  methods: {
    initialize: function() {
      this.service.getCalculate();
    },
    createCalculate: function() {
      this.service.createCalculate();
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