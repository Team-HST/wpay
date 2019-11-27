import Vue from 'vue'
import Vuex from 'vuex'
import { api } from '@/utils/api'
import { common } from '@/utils/common'
import createPersistedState from 'vuex-persistedstate'

Vue.use(Vuex);

export default new Vuex.Store({
  plugins: [
    createPersistedState()
  ],
  state: {
    user: {
      id: '',
      name: '',
      token: ''
    },
    host: {
      name: '',
      accountNum: '',
      bankCode: ''
    },
    views: {
      remittance: {
        isDialog: false
      }
    }
  },
  getters: {
    /**
     * @description 유저 정보 조회
     */
    getUserData: (state) => {
      return state.user;
    },
    /**
     * @description 혼주 정보 조회
     */
    getHostData: (state) => {
      return state.host;
    },
    /**
     * @description 혼주 송금 계좌 다이얼로그 표출 여부 조회
     */
    getAccountDialog: (state) => {
      return state.views.remittance.isDialog;
    }
  },
  mutations: {
    /**
     * @description 유저 정보 삽입
     */
    setUserData: (state, data) => {
      state.user = data;
      if (common.isNotBlank(data)) {
        // api token 전달
        api.setUserToken(data.token);
      } else {
        api.setUserToken(null);
      }
    },
    /**
     * @description 혼주 정보 삽입
     */
    setHostData: (state, data) => {
      state.host = data;
    },
    /**
     * @description 혼주 송금 계좌 다이얼로그 표출 여부 변경
     */
    changeAccountDialog: (state) => {
      state.views.remittance.isDialog = !state.views.remittance.isDialog;
    }
  },
  actions: {
    /**
     * @description 유저 로그인 API
     */
    userSignIn: (context, user) => {
      return api.basic.post('/api/users/signin', user)
        .then(response => {
          let user = {};
          user.id = response.data.user.id;
          user.name = response.data.user.name;
          user.token = response.data.token;
          context.commit('setUserData', user);
        })
        .catch(error => {
          if (error.response.data.code === 101) {
            alert('존재하지 않는 사용자입니다.');
          } else if (error.response.data.code === 102) {
            alert('비밀번호가 올바르지 않습니다.');
          } else {
            alert('계정정보가 올바르지 않습니다.');
          }
        });
    }
  }
});