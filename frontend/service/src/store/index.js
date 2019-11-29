import Vue from 'vue'
import Vuex from 'vuex'
import $http from 'axios'
import createPersistedState from 'vuex-persistedstate'

Vue.use(Vuex);

export default new Vuex.Store({
  plugins: [
    createPersistedState()
  ],
  state: {
    user: {},
    host: {},
    views: {
      remittance: {
        isAccountDialog: false,
        isMealDialog: false
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
    getIsAccountDialog: (state) => {
      return state.views.remittance.isAccountDialog;
    },
    /**
     * @description 식권 발급 다이얼로그 여부 조회
     */
    getIsMealDialog: (state) => {
      return state.views.remittance.isMealDialog;
    }
  },
  mutations: {
    /**
     * @description 유저 정보 삽입
     */
    setUserData: (state, data) => {
      state.user = data;
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
    changeIsAccountDialog: (state) => {
      state.views.remittance.isAccountDialog = !state.views.remittance.isAccountDialog;
    },
    /**
     * @description 식권 발급 다이얼로그 표출 여부 변경
     */
    changeIsMealDialog: (state) => {
      state.views.remittance.isMealDialog = !state.views.remittance.isMealDialog;
    }
  },
  actions: {
    /**
     * @description 유저 로그인 API
     */
    userSignIn: (context, user) => {
      return $http.post('/api/users/signin', user)
        .then(response => {
          let user = {};
          user.id = response.data.user.id;
          user.name = response.data.user.name;
          user.sequence = response.data.user.sequence;
          user.token = response.data.token;
          context.commit('setUserData', user);
        })
        .catch(error => {
          const errorData = error.response.data;
          if (errorData.code === 1011) {
            alert('존재하지 않는 사용자입니다.');
          } else if (errorData.code === 1012) {
            alert('비밀번호가 올바르지 않습니다.');
          } else if (errorData.code === 1013) {
            alert('계좌등록이 되어있지 않습니다. \n인증 페이지로 이동합니다.');
            // 계좌 인증 페이지 이동
            $http.get(`/api/users/${errorData.extraData.userSequence}/account-authentication`)
            .then(response => {
              location.href = response.data;
              // window.open(response.data);
            });
          } else {
            alert('계정정보가 올바르지 않습니다.');
          }
        });
    },
    /**
     * 혼주 정보 조회
     */
    findHostData: (context, data) => {
      $http.get(`/api/users/${data.hostSeq}`)
        .then(response => {
          response.data.weddingSequence = data.weddingSeq;
          context.commit('setHostData', response.data);
        })
        .catch(error => {
          console.log(error);
          alert('일시적인 오류입니다.\n관리자에게 문의하여 주세요.');
        });
    }
  }
});