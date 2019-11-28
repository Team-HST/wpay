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
              </v-row>
              <v-row class="ma-4 pa-5" style="border:1px solid #EC407A; border-radius: 2em;">
                <v-flex xs8>
                  <span class="title">발급된 식권 개수</span>
                </v-flex>
                <v-flex xs4 style="text-align: right;">
                  <span class="title" color="font-weight-bold"> {{calculate.totalMealCnt}} 개 </span>
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
                  <span class="title" color="font-weight-bold"> {{calculate.diffPrice}} 원 </span>
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
// import { mapActions } from 'vuex';
import { api } from '@/utils/api';
export default {
  data: () => ({
    calculate: {
      seq: 0,                   // 일련번호
      weddingSeq: 0,            // 결혼일련번호
      totalCelebrationPrice: 0, // 총 축의금
      totalMealCnt: 0,          // 총 식권갯수
      totalMealPrice: 0,        // 총 식대비
      diffPrice: 0              // 차액
    }
  }),
  created() {
    this.calculate.weddingSeq = this.$route.params.weddingSeq;
    this.service = {
      searchWedding: (weddingDt) => {
        /* 수정필요 */
        api.auth.post('/api/wedding/list', weddingDt)
        .then(response => {
            return response;
        });
      }
    }
  },
  mounted() {
    //this.initialize();
  },
  methods: {
    createCalculate: function() {

    },
    moveWeddingList: function() {
      this.$router.push({name:"WeddingList"});
    }
  }
}
</script>
<style scoped>

</style>