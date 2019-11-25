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
                        <v-flex xs1></v-flex>
                        <v-flex xs2>
                            신랑
                        </v-flex>
                        <v-flex xs4>
                            <v-text-field
                            type="text"
                            label="아이디"
                            v-model="param.maleId"
                            />
                        </v-flex>
                        <v-flex xs1></v-flex>
                        <v-flex xs3>
                            <v-btn
                            class="font-weight-bold white--text"
                            color="pink lighten-2"
                            @click="searchUser(param.maleId)"
                            >
                            검색
                            </v-btn>
                        </v-flex>
                        <v-flex xs1></v-flex>
                    </v-row>
                    <v-row align="center" justify="center">
                        <v-flex xs1></v-flex>
                        <v-flex xs2>
                            신부
                        </v-flex>
                        <v-flex xs4>
                            <v-text-field
                            type="text"
                            label="아이디"
                            v-model="param.femaleId"
                            />
                        </v-flex>
                        <v-flex xs1></v-flex>
                        <v-flex xs3>
                            <v-btn
                            class="font-weight-bold white--text"
                            color="pink lighten-2"
                            @click="searchUser(param.femaleId)"
                            >
                            검색
                            </v-btn>
                        </v-flex>
                        <v-flex xs1></v-flex>
                    </v-row>
                    <v-row align="center" justify="center">
                        <v-flex xs4></v-flex>
                        <v-flex xs4 align="center">
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
// import { mapActions } from 'vuex';
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
    created() {
        this.service = {
            searchUser: () => {
//                console.log('searchUser();');
//                api.auth.post('/api/', id)
//                .then(response => {})
                // .catch(e => {});
            },
            generateQR: () => {
//                console.log('generateQR();');
//                api.auth.post('/api/', param)
//                .then(response => {})
                // .catch(e => {});
            }
        }
    },
    methods: {
        searchUser(id) {
            this.service.searchUser(id);
        },
        generateQR() {
            this.service.generateQR();
        }
        
    }
}
</script>