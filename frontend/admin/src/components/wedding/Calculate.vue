<template>
    <v-content>
        <v-container>
          <v-row>
            <v-flex xs8></v-flex>
            <v-flex xs4>
              혼주: 김 부 시
            </v-flex>
          </v-roW>
          <v-row style="margin-top: 15px;">
            <v-flex xs2></v-flex>
            <v-flex xs4>
              축의금 총액
            </v-flex>
            <v-flex xs4>
              <span color="font-weight-bold"></span> {{calculate.totalCelebrationPrice}} 원
            </v-flex>
            <v-flex xs2></v-flex>
          </v-row>
          <v-row>
            <v-row>
              <v-flex xs2></v-flex>
              <v-flex xs4>
                발급된 식권 개수
              </v-flex>
              <v-flex xs4>
                <span color="font-weight-bold"></span> {{calculate.totalMealCnt}} 원
              </v-flex>
              <v-flex xs2></v-flex>
            </v-row>
            <v-row>
              <v-flex xs2></v-flex>
              <v-flex xs4>
                총 식대비
              </v-flex>
              <v-flex xs4>
                <span color="font-weight-bold"></span> {{calculate.totalMealPrice}} 원
              </v-flex>
              <v-flex xs2></v-flex>
            </v-row>
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
  method: {
    createCalculate: function() {

    }
  }
}
</script>