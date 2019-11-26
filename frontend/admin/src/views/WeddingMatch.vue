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
                        <v-flex xs3 style="text-align:center;">
                            식권
                        </v-flex>
                        <v-flex xs6 style="text-align:center;">
                            <v-text-field
                            type="number"
                            label="식권가"
                            v-model="weddingInfo.ticketPrice"
                            />
                        </v-flex>
                        <v-flex xs3></v-flex>
                    </v-row> 
                    <v-row align="center" justify="center">
                        <v-flex xs3 style="text-align:center;">
                            일시
                        </v-flex>
                        <v-flex xs3 style="text-align:center;">
                            <v-dialog
                                ref="dialog1"
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
                                    <v-btn text color="primary" @click="$refs.dialog1.save(datePicker.nowDate)">확인</v-btn>
                                </v-date-picker>
                            </v-dialog>
                        </v-flex>
                        <v-flex xs1></v-flex>
                        <v-flex xs3 style="text-align: center;">
                            <v-dialog
                                ref="dialog2"
                                v-model="timePicker.modal"
                                :return-value.sync="timePicker.time"
                                persistent
                                width="290px"
                            >
                                <template v-slot:activator="{ on }">
                                <v-text-field
                                    v-model="timePicker.time"
                                    readonly
                                    v-on="on"
                                ></v-text-field>
                                </template>
                                <v-time-picker
                                v-if="timePicker.modal"
                                v-model="timePicker.time"
                                full-width
                                color="pink lighten-2"
                                >
                                <v-spacer></v-spacer>
                                <v-btn text color="primary" @click="timePicker.modal = false">취소</v-btn>
                                <v-btn text color="primary" @click="$refs.dialog2.save(timePicker.time)">확인</v-btn>
                                </v-time-picker>
                            </v-dialog>
                        </v-flex>
                        <v-flex xs2></v-flex>
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
//import { mapActions } from 'vuex';
//import { api } from '@/utils/api'

export default {
    data() {
        return {
            weddingInfo: {
                maleId: '',     // 신랑
                maleSeq: 0,
                femaleId: '',   // 신부
                femaleSeq: 0,
                tickPrice: 0,   // 식권가
                weddingDt: null,// 결혼일시
                regDt: null     // 등록일시
            },
            datePicker: {
                nowDate: new Date().toISOString().substr(0, 10),
                showCurrent: true,
                month: false,
                modal: false
            },
            timePicker: {
                time: "00:00",
                modal: false
            }
        }
    },
    computed: {
      functionEvents () {
        return this.month ? this.monthFunctionEvents : this.dateFunctionEvents
      },
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
        //...mapActions(['matchWedding']),
        /* 사용자 조회 */
        searchUser(id) {
            this.service.searchUser(id);
        },
        /* WEDDING MATCH AND QR코드 생성 */
        generateQR() {
            this.weddingInfo.weddingDt = this.$moment(this.datePicker.nowDate + "T" + this.timePicker.time, this.$moment.ISO_8601);
            this.weddingInfo.regDt = this.$moment(new Date(), this.$moment.ISO_8601);
            //this.matchWedding(this.weddingInfo);
            //.then(response => {
                // this.$router.push({name:"QrGenerate"});
            //});
            this.$router.push({name:"QrGenerate", params: this.weddingInfo});
        },
        dateFunctionEvents (date) {
            const [,, day] = date.split('-')
            if ([12, 17, 28].includes(parseInt(day, 10))) return true
            if ([1, 19, 22].includes(parseInt(day, 10))) return ['red', '#00f']
            return false
        },
        monthFunctionEvents (date) {
            const month = parseInt(date.split('-')[1], 10)
            if ([1, 3, 7].includes(month)) return true
            if ([2, 5, 12].includes(month)) return ['error', 'purple', 'rgba(0, 128, 0, 0.5)']
            return false
        }
    }
}
</script>