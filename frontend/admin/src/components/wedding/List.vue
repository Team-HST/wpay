<template>
  <v-content>
    <v-container>
      <v-row align="center" justify="center">
        <v-flex xs3 style="text-align:center;">
          결혼일시
        </v-flex>
        <v-flex xs6 style="text-align:center;">
        <v-flex xs3></v-flex>
          <v-dialog
            ref="dialog"
            v-model="datePicker.modal"
            :return-value.sync="datePicker.nowDate"
            persistent
            width="290px"
          >
            <template v-slot:activator="{ on }">
              <v-text-field
                v-model="datePicker.nowDate"
                readonly
                v-on="on"
              ></v-text-field>
            </template>
            <v-date-picker 
              v-model="datePicker.nowDate"
              :show-current="datePicker.showCurrent"
              :type="datePicker.month ? 'month' : 'date'"
              color="pink lighten-2" scrollable>
              <v-spacer></v-spacer>
              <v-btn text color="primary" @click="datePicker.modal = false">취소</v-btn>
              <v-btn text color="primary" @click="$refs.dialog.save(datePicker.nowDate)">확인</v-btn>
            </v-date-picker>
          </v-dialog>
        </v-flex>
      </v-row>
      <v-list three-line>
        <template v-for="(wedding, index) in weddingList">
          <v-subheader
            v-if="wedding.header"
            :key="wedding.header"
            v-text="wedding.header"
          ></v-subheader>

          <v-divider
            v-else-if="wedding.divider"
            :key="index"
            :inset="wedding.inset"
          ></v-divider>

          <v-list-item
            v-else
            :key="wedding.seq"
            @click="moveCalculate(wedding.seq)"
          >
            <v-list-item-icon>
              <v-icon text="mdi-account-multiple"></v-icon>
            </v-list-item-icon>

            <v-list-item-content>
              <v-list-item-title class="font-weight-bold pink lighten-2" v-html="wedding.weddingDt"></v-list-item-title>
              <v-list-item-subtitle v-html="'신 랑: ' + wedding.maleName"></v-list-item-subtitle>
              <v-list-item-subtitle v-html="'신 부: ' + wedding.femaleName"></v-list-item-subtitle>
            </v-list-item-content>

            <v-list-item-icon>
              <v-icon text=""></v-icon>
            </v-list-item-icon>
          </v-list-item>
        </template>
      </v-list>
    </v-container>
  </v-content>
</template>

<script>
// import { mapActions } from 'vuex';
import { api } from '@/utils/api';
export default {
  data: () => ({
    weddingDt: null,
    weddingList: [
      {
        seq: 1,
        maleName: '김영훈',
        femaleName: '허현',
        weddingDt: '2019-12-07'
      },
      { divider: true, inset: true},
      {
        seq: 2,
        maleName: '이한울',
        femaleName: '김다혜',
        weddingDt: '2019-12-07'
      },
      { divider: true, inset: true},
      {
        seq: 3,
        maleName: '이현규',
        femaleName: '김영순',
        weddingDt: '2019-12-08'
      },
      { divider: true, inset: true},
    ],
    datePicker: {
      nowDate: new Date().toISOString().substr(0, 10),
      showCurrent: true,
      month: false,
      modal: false
    }
  }),
  created() {
    this.service = {
      searchWeddingList: (weddingDt) => {
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
  computed: {
    functionEvents () {
      return this.month ? this.monthFunctionEvents : this.dateFunctionEvents
    },
  },
  methods: {
    moveCalculate: function(seq) {
      /* 수정필요 */
      this.$router.push({name:"WeddingCalculate", params:seq});
    },
    dateFunctionEvents: function(date) {
      const [,, day] = date.split('-')
      if ([12, 17, 28].includes(parseInt(day, 10))) return true
      if ([1, 19, 22].includes(parseInt(day, 10))) return ['red', '#00f']
      return false
    },
    monthFunctionEvents: function(date) {
        const month = parseInt(date.split('-')[1], 10)
        if ([1, 3, 7].includes(month)) return true
        if ([2, 5, 12].includes(month)) return ['error', 'purple', 'rgba(0, 128, 0, 0.5)']
        return false
    }
  }
}
</script>