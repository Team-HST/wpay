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
                            v-model="maleId"
                            />
                        </v-flex>
                        <v-flex xs3 style="text-align:center;">
                            <v-btn
                            class="font-weight-bold white--text"
                            color="pink lighten-2"
                            @click="searchUser('M')"
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
                            v-model="femaleId"
                            />
                        </v-flex>
                        <v-flex xs3 style="text-align:center;">
                            <v-btn
                            class="font-weight-bold white--text"
                            color="pink lighten-2"
                            @click="searchUser('W')"
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
                            v-model="weddingInfo.mealTicketPrice"
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
// import { mapActions } from 'vuex';
import { api } from '@/utils/api'
import { mapGetters } from 'vuex'

export default {
    data() {
        return {
            maleId: '',
            femaleId: '',
            weddingInfo: {
                maleHostSeq: 0,
                femaleHostSeq: 0,
                mealTicketPrice: 0,   // 식권가
                weddingDt: null // 결혼일시
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
      ...mapGetters(['getUserData'])
    },
    created() {
        this.service = {
            /* 사용자 정보 조회 */
            searchUser: (sFlag) => {
                let userId = '';
                if (sFlag === "M") {
                    userId = this.maleId;
                } else if (sFlag === "W") {
                    userId = this.femaleId;
                }
                api.setUserToken(this.getUserData.token);
                api.auth.get('/api/users/'+userId)
                .then(response => {
                    if (sFlag === "M") { // 신랑정보
                        this.weddingInfo.maleHostSeq = response.data.sequence;
                        this.maleName = response.data.name;
                    } else if (sFlag === "W") { // 신부정보
                        this.weddingInfo.femaleHostSeq = response.data.sequence;
                        this.femaleName = response.data.name;
                    }   
                })
                .catch(e => {
                    alert('사용자 정보를 찾지 못했습니다.'+e);
                });
            },
            /* 결혼 정보 생성 */
            matchWedding: (weddingInfo) => {
                api.setUserToken(this.getUserData.token);
                return api.auth.post('/api/weddings/create', weddingInfo)
                .then(response => {
                    return response.data.sequence;
                })
                .catch(error => {
                    alert('error: '+error);
                });
            }
        }
    },
    methods: {
        //...mapActions(['matchWedding']),
        /* 사용자 조회 */
        searchUser: function(id) {
            this.service.searchUser(id);
        },
        /* QR코드 생성 */
        generateQR: function() {
            this.weddingInfo.weddingDt = this.$moment(this.datePicker.nowDate + "T" + this.timePicker.time, this.$moment.ISO_8601);
            this.service.matchWedding(this.weddingInfo)
            .then(response => {
                if (response !== "undefined") {
                    this.weddingInfo.weddingSeq = response;
                    this.$router.push({name:"QrGenerate", params: this.weddingInfo});
                }
            });
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