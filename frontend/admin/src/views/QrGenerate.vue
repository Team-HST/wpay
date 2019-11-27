<template>
    <v-content>
        <v-container
        class="fill-height"
        fluid
        >
            <v-row
            align="center"
            justify="center"
            >
                <v-col
                cols="12"
                lg="6"
                sm="8"
                md="8"
                >
                    <v-row style="text-align: center;">
                        <v-flex xs2></v-flex> 
                        <v-flex xs4>
                        신랑
                        </v-flex>
                        <v-flex xs4>
                        김 영 훈
                        </v-flex>
                        <v-flex xs2></v-flex>
                    </v-row>
                    <v-row style="text-align: center;">
                        <v-flex xs3></v-flex>
                        <v-flex xs6>
                            <qrcode value="this.maleQRInfo" :width="codeOptions.width" :options="codeOptions"></qrcode>
                        </v-flex>
                        <v-flex xs3></v-flex>
                    </v-row>
                    <v-row style="text-align: center;">
                        <v-flex xs2></v-flex>
                        <v-flex xs4>
                        신부
                        </v-flex>
                        <v-flex xs4>
                        이 한 울
                        </v-flex>
                        <v-flex xs2></v-flex>
                    </v-row>
                    <v-row style="text-align: center;">
                        <v-flex xs3></v-flex>
                        <v-flex xs6>
                            <qrcode value="this.femaleQRInfo" :width="codeOptions.width" :options="codeOptions"></qrcode>
                        </v-flex>
                        <v-flex xs3></v-flex>
                    </v-row>
                </v-col>
            </v-row>
        </v-container>
    </v-content>
</template>

<script>
//import { mapActions } from 'vuex';
//import { api } from '@/utils/api'
import Vue from 'vue'
import vueQRCode from 'vue-qrcode'
Vue.component('qrcode', vueQRCode);

export default {
  data() {
    return {
      maleQRInfo: [{
        maleId: '',
        maleSeq: 0,
        WeddingDt: null
      }],
      femaleQRInfo: [{
          femaleId: '',
          femaleSeq: 0,
          weddingDt: null
      }],
      codeOptions: {
        color: {
            dark: '',
            light: ''
        },
        width: 200
      }
    }
  },
  created() {
    this.maleQRInfo[0].weddingDt = this.$route.params.weddingDt;
    this.maleQRInfo[0].maleId = this.$route.params.maleId;
    this.femaleQRInfo[0].weddingDt = this.$route.params.weddingDt;
    this.femaleQRInfo[0].femaleId = this.$route.params.femaleId;
  },
  methods: {

  }
}
</script>
