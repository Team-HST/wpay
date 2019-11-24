<template>
    <v-form>
        <v-container>
            <v-layout row>
                <v-flex xs12 sm6>
                    <v-row cols="12">
                        <v-text-field
                            v-model="userId"
                            label="ID"
                            outlined
                        ></v-text-field>
                    </v-row>
                    <v-row cols="12">
                        <v-text-field
                            v-model="userPassword"
                            label="PW"
                            outlined
                        ></v-text-field>
                    </v-row>
                </v-flex>

            </v-layout>
        </v-container>
    </v-form>
</template>

<script>
//import {mapMutations} from 'vuex'
    
export default {
    name : 'loginForm',
    data: () => ({
        userId : ''
        ,userPassword: ''
    }),
    created() {
        this.service = {
            // 사용자 로그인
            login: (id, pw) => {
                console.log('id: ' + id + ', pw: ' + pw);
                /*
                this.$http.post('/api/user').then(response => {
                    this.setUserInfo(id);
                    this.fnGoMain();
                })
                */
            }
        }
    },
    methods : {
        fnGoMain() {
            this.$router.push({ name : 'Main'});
        }
    }
}
</script>