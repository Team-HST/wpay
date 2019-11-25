import Vue from 'vue'
import Vuex from 'vuex'
import { api } from '@/utils/api'

Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    user: {
      id: '',
      name: '',
      token: ''
    }
  },
  getters: {
    /**
     * @description 유저정보 삽입
     */
    getUserData: (state) => {
      return state.user;
    }
  },
  mutations: {
    /**
     * @description 유저정보 조회
     */
    setUserData: (state, data) => {
      state.user = data;
      // api token 전달
      api.setUserToken(data.token);
    }
  },
  actions: {
    /**
     * @description 유저 로그인 API
     */
    userSignIn: (context, user) => {
      api.basic.post('/api/users/signin', user)
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