<template>
    <v-content>
        <v-container
        class="fill-height"
        fluid
        >
            <v-row>
                <v-flex xs4>
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
                    @click="searchUser"
                    x-large
                    >
                    검색
                    </v-btn>
                </v-flex>
            </v-row>
            <v-row>
                <v-flex xs4>
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
                    @click="searchUser"
                    x-large
                    >
                    검색
                    </v-btn>
                </v-flex>
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
      param: {
        maleId: '',     // 신랑
        femaleId: '',   // 신부
      }
    }
  },
  methods: {
    ...mapActions(['userSignIn']),
    searchUser: function() {
        //console.log('searchUser();');
        //api.auth.post('/api/users/searchUser', param)
        //.then({});
    }
  }
}
</script>