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
                    <v-row align="center" justify="center">
                        <v-flex xs3>
                        신랑
                        </v-flex>
                        <v-flex xs5>
                        김 영 훈
                        </v-flex>
                        <v-flex xs4></v-flex>
                    </v-row>
                    <v-row align="center" justify="center">
                        <v-flex xs4></v-flex>
                        <v-flex xs4>
                        QR코드
                        </v-flex>
                        <v-flex xs4></v-flex>
                    </v-row>
                    <v-row align="center" justify="center">
                        <v-flex xs3>
                        신부
                        </v-flex>
                        <v-flex xs5>
                        이 한 울
                        </v-flex>
                        <v-flex xs4></v-flex>
                    </v-row>
                    <v-row align="center" justify="center">
                        <v-flex xs4></v-flex>
                        <v-flex xs4>
                            QR코드
                        </v-flex>
                        <v-flex xs4></v-flex>
                    </v-row>
                </v-col>
            </v-row>
        </v-container>
    </v-content>
</template>

<script>
//import { mapActions } from 'vuex';
//import { api } from '@/utils/api'

export default {
  data() {
    return {
      param: {
        maleId: '',     // 신랑
        femaleId: '',   // 신부
      }
    }
  },
  methods: {
    
  }
}
</script>