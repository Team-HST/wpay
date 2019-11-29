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
                            <span class="title font-weight-bold">신랑</span>
                        </v-flex>
                        <v-flex xs4>
                            <span class="title font-weight-bold">{{ maleName }}</span>
                        </v-flex>
                        <v-flex xs2></v-flex>
                    </v-row>
                    <v-row style="text-align: center;">
                        <v-flex xs3></v-flex>
                        <v-flex xs6>
                            <qrcode :value="this.maleQRInfo" :width="codeOptions.width" :options="codeOptions"></qrcode>
                        </v-flex>
                        <v-flex xs3></v-flex>
                    </v-row>
                    <v-row style="text-align: center;">
                        <v-flex xs2></v-flex>
                        <v-flex xs4>
                            <span class="title font-weight-bold">신부</span>
                        </v-flex>
                        <v-flex xs4>
                            <span class="title font-weight-bold">{{ femaleName }}</span>
                        </v-flex>
                        <v-flex xs2></v-flex>
                    </v-row>
                    <v-row style="text-align: center;">
                        <v-flex xs3></v-flex>
                        <v-flex xs6>
                            <qrcode :value="this.femaleQRInfo" :width="codeOptions.width" :options="codeOptions"></qrcode>
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
      maleQRInfo: '',
      femaleQRInfo: '',
      codeOptions: {
        color: {
            dark: '',
            light: ''
        },
        width: 200,
      },
      femaleName: '',
      maleName: ''
    }
  },
  created() {
    let weddingSeq = this.$route.params.weddingSeq;
    let maleSeq = this.$route.params.maleHostSeq;
    let femaleSeq = this.$route.params.femaleHostSeq;
    this.femaleName = this.$route.params.femaleName;
    this.maleName = this.$route.params.maleName;

    this.maleQRInfo = '{"weddingSeq":' + weddingSeq + ',"hostSeq":' + maleSeq + '}';
    this.femaleQRInfo = '{"weddingSeq":' + weddingSeq + ',"hostSeq":' + femaleSeq + '}';
  },
  methods: {

  }
}
</script>
