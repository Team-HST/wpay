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
                  <th class="text-center" v-for="header in expenditure.header" :key="header" >
                    {{ header }}
                  </th>
                </tr>
              </thead>
              <tbody>
                <tr v-for="item in expenditure.data" :key="item.no">
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
        <v-card>
          <h4 class="font-weight-medium pa-3">하객리스트</h4>
          <v-simple-table height="300px">
            <template v-slot:default>
              <thead>
                <tr>
                  <th class="text-center" v-for="header in calculate.header" :key="header" >
                    {{ header }}
                  </th>
                </tr>
              </thead>
              <tbody>
                <tr v-for="item in calculate.data" :key="item.no">
                </tr>
              </tbody>
            </template>
          </v-simple-table>
            <div class="text-right">
              <v-btn class="font-weight-bold white--text ma-5" color="blue-grey">정산표 보기</v-btn>
            </div>
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
export default {
  data() {
    return {
      tab: null,
      expenditure: {
        header: [
          '내용',
          '날짜',
          '금액'
        ],
        data: []
      },
      calculate: {
        header: [
          '하객이름',
          '금액',
          '날짜',
          '메모'
        ],
        data: []
      },
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
  created() {
    this.service = {
      // 지출, 정산 api 조회
    }
  },
  mounted() {
    // @TODO 해당 달 지출 내역과 예식비 정산 여부에 따른 목록 표출
    // {api 호출 부분}

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
    }
  }
}
</script>