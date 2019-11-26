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
                        <v-flex xs3 style="text-align:center;">
                            신랑
                        </v-flex>
                        <v-flex xs6 style="text-align:center;">
                            <v-text-field
                            type="text"
                            label="아이디"
                            v-model="weddingInfo.maleId"
                            />
                        </v-flex>
                        <v-flex xs3 style="text-align:center;">
                            <v-btn
                            class="font-weight-bold white--text"
                            color="pink lighten-2"
                            @click="searchUser(weddingInfo.maleId)"
                            >
                            검색
                            </v-btn>
                        </v-flex>
                    </v-row>
                    <v-row align="center" justify="center">
                        <v-flex xs3 style="text-align:center;">
                            신부
                        </v-flex>
                        <v-flex xs6 style="text-align:center;">
                            <v-text-field
                            type="text"
                            label="아이디"
                            v-model="weddingInfo.femaleId"
                            />
                        </v-flex>
                        
                        <v-flex xs3 style="text-align:center;">
                            <v-btn
                            class="font-weight-bold white--text"
                            color="pink lighten-2"
                            @click="searchUser(weddingInfo.femaleId)"
                            >
                            검색
                            </v-btn>
                        </v-flex>
                    </v-row>
                    <v-row align="center" justify="center">
                        <v-flex xs4></v-flex>
                        <v-flex xs4 style="text-align:center; margin-top:10px;">
                            <v-btn
                            class="font-weight-bold white--text"
                            color="pink lighten-2"
                            @click="generateQR"
                            >
                            QR 생성
                            </v-btn>
                        </v-flex>
                        <v-flex xs4></v-flex>
                    </v-row>
                </v-col>
            </v-row>
        </v-container>
    </v-content>
</template>

<script>
import { mapActions } from 'vuex';
//import { api } from '@/utils/api'

export default {
    data() {
        return {
            weddingInfo: {
                maleId: '',     // 신랑
                femaleId: '',   // 신부
            }
        }
    },
    created() {
        this.service = {
            searchUser: () => {
//                console.log('searchUser();');
//                api.auth.post('/api/', id)
//                .then(response => {
                //console.log('id: ' + id);
//                 })
                // .catch(e => {});
            }
        }
    },
    methods: {
        ...mapActions(['matchWedding']),
        /* 사용자 조회 */
        searchUser(id) {
            this.service.searchUser(id);
        },
        /* QR코드 생성 */
        generateQR() {
            this.matchWedding(this.weddingInfo);
            //.then(response => {
                // this.$router.push({name:"QrGenerate"});
            //});
        }
        
    }
}
</script>