<template>
  <div>
    <v-tabs
      fixed-tabs
      background-color="green"
      dark
      v-model="tab"
    >
      <v-tabs-slider color="green darken-4"></v-tabs-slider>
      <v-tab
        href="#tab-1"
      >
        지출
      </v-tab>
      <v-tab
        href="#tab-2"
      >
        예식비 정산
      </v-tab>
    </v-tabs>

    <v-tabs-items v-model="tab">
      <v-tab-item
        :value="'tab-1'"
        :key="1"
      >
        <v-card>
          <v-col             
            cols="6"
            md="2">
            <v-select
              class="height"
              v-model="monthSelected"
              :items="month"
              item-text="name"
              dense
              solo
            >
            </v-select>
          </v-col>
          <v-simple-table height="350px">
            <template v-slot:default>
              <thead>
                <tr>
                  <th class="text-center">내용</th>
                  <th class="text-center">날짜</th>
                  <th class="text-center">금액</th>
                </tr>
              </thead>
              <tbody>
                <tr v-if="table.expenditure.length === 0">
                  <th
                    class="text-center"
                    colspan="3"
                  >
                    지출내역이 존재하지 않습니다.
                  </th>
                </tr>
                <tr 
                  v-for="item in table.expenditure" 
                  :key="item.sequence"
                >
                  <th class="text-center">{{ item.host.name }}</th>
                  <th class="text-center">{{ item.remittanceAt }}</th>
                  <th class="text-center">{{ item.amount }}</th>
                </tr>
              </tbody>
            </template>
          </v-simple-table>
        </v-card>
      </v-tab-item>
      <v-tab-item
        :value="'tab-2'"
        :key="2"
      >
        <v-card v-if="!isSettlement">
          <h4 class="font-weight-bold pa-3">하객리스트</h4>
          <v-simple-table height="290px">
            <template v-slot:default>
              <thead>
                <tr>
                  <th class="text-center" style="width: 17%">하객이름</th>
                  <th class="text-center" style="width: 17%">금액</th>
                  <th class="text-center" style="width: 25%">날짜</th>
                  <th class="text-center" style="width: 41%">메모</th>
                </tr>
              </thead>
              <tbody>
                <tr v-if="table.calculate.length === 0">
                  <th
                    class="text-center"
                    colspan="4"
                  >
                    정산내역이 존재하지 않습니다.
                  </th>
                </tr>
                <tr 
                  v-else
                  v-for="item in table.calculate" 
                  :key="item.no"
                >
                </tr>
              </tbody>
            </template>
          </v-simple-table>
            <div class="text-right">
              <v-btn 
                class="font-weight-bold white--text ma-5" 
                color="blue-grey"
                @click="showSettlement"
              >
                정산표 보기
              </v-btn>
            </div>
        </v-card>
        <v-card 
          v-else
          class="pt-10 pb-10 pl-3 pr-3"
        >
          <v-row class="ma-4">
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
              <span class="subtitle-1 font-weight-bold">축의금 총액</span>
            </v-flex>
            <v-flex xs6 style="text-align: right;">
              <span class="subtitle-1 font-weight-bold" color="font-weight-bold"> {{settlement.totalCelebrationPrice}} </span>
            </v-flex>
            <v-flex xs6>
              <span style="font-size: 0.9em;">신랑 측</span>
            </v-flex>
            <v-flex xs6 style="text-align: right;">
              <span color="font-weight-bold" style="font-size: 0.9em;"> {{settlement.maleHostTotalCelebrationAmount}} 원 </span>
            </v-flex>
            <v-flex xs6>
              <span style="font-size: 0.9em;">신부 측</span>
            </v-flex>
            <v-flex xs6 style="text-align: right;">
              <span color="font-weight-bold" style="font-size: 0.9em;"> {{settlement.femaleHostTotalCelebrationAmount}} 원 </span>
            </v-flex>
          </v-row>
          <v-row class="ma-4 pa-5" style="border:1px solid #EC407A; border-radius: 2em;">
            <v-flex xs4></v-flex>
            <v-flex xs8 style="text-align: right;">
              <span class="font-weight-bold red--text" style="font-size: 0.9em;">* 1인 식사비 : {{settlement.mealTicketPrice}} 원</span>
            </v-flex>
            <v-flex xs8>
              <span class="subtitle-1 font-weight-bold">발급 식권 개수</span>
            </v-flex>
            <v-flex xs4 style="text-align: right;">
              <span class="subtitle-1 font-weight-bold" color="font-weight-bold"> {{settlement.totalMealTicketCount}} 개 </span>
            </v-flex>
            <v-flex class="mt-2" xs6>
              <span class="subtitle-1 font-weight-bold">총 식대비</span>
            </v-flex>
            <v-flex class="mt-2" xs6 style="text-align: right;">
              <span class="subtitle-1 font-weight-bold" color="font-weight-bold"> {{settlement.totalMealPrice}} 원 </span>
            </v-flex>
          </v-row>
          <v-row class="ma-4 pa-5" style="border:1px solid #EC407A; border-radius: 1em;">
            <v-flex xs6>
              <span class="subtitle-1 font-weight-bold">차 액</span>
            </v-flex>
            <v-flex xs6 style="text-align: right;">
              <span class="subtitle-1 font-weight-bold" color="font-weight-bold"> {{settlement.remainingAmount}} 원 </span>
            </v-flex>
          </v-row>
          <v-card-actions class="pa-0 pb-2">
            <div class="flex-grow-1"></div>
            <v-btn
              class="font-weight-bold white--text"
              color="green"
              @click="this.closeSettlement"
            > 
              닫기
            </v-btn>
          </v-card-actions>
        </v-card>
      </v-tab-item>
    </v-tabs-items>
    <div class="text-center">
      <v-btn 
        class="font-weight-bold white--text mt-3" 
        color="pink lighten-2"
        @click="closeMypage"
      >
        나가기
      </v-btn>
    </div>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'

export default {
  data() {
    return {
      tab: null,
      isSettlement: false,
      table: {
        expenditure: [],
        calculate: []
      },
      settlement: {},
      monthSelected: {},
      month: [
        {name: '1월', key: 1},
        {name: '2월', key: 2},
        {name: '3월', key: 3},
        {name: '4월', key: 4},
        {name: '5월', key: 5},
        {name: '6월', key: 6},
        {name: '7월', key: 7},
        {name: '8월', key: 8},
        {name: '9월', key: 9},
        {name: '10월', key: 10},
        {name: '11월', key: 11},
        {name: '12월', key: 12}
      ],
      service: {}
    }
  },
  computed: {
    ...mapGetters(['getUserData'])
  },
  created() {
    this.service = {
      // 지출, 정산 api 조회
      findExpenditureHistory: () => {
        this.$http.get(`/api/remittance/user/${this.getUserData.sequence}/histories`)
          .then(response => {
            this.table.expenditure = response.data;
          })
      }
    }
  },
  mounted() {
    // @TODO 해당 달 지출 내역과 예식비 정산 여부에 따른 목록 표출
    // {api 호출 부분}
    this.service.findExpenditureHistory();

    // 현재 월 지정
    const date = new Date();
    const currentMonth = date.getMonth()+1
    this.monthSelected = this.month.filter(date => {
      return date.key === currentMonth;
    })[0];
  },
  methods: {
    /**
     * @description 마이페이지 닫기 이벤트
     */
    closeMypage: function() {
      this.$router.push('/main');
    },

    showSettlement: function() {
      // @TODO 정산 되어있는지 확인
      this.isSettlement = true;
    },
    
    closeSettlement: function() {
      this.isSettlement = false;
    }
  }
}
</script>