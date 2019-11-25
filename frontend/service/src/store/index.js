import Vue from 'vue';
import Vuex from 'vuex';

Vue.use(Vuex);

export default new Vuex.Store({
  state: {

  },
  getters: {

  },
  mutations: {
      
  },
  actions: {
    userSignin: () => {
      this.$http.post('/api/users/signin')
      .then(response => {
        // eslint-disable-next-line no-console
        console.log(response);
      })
      .catch(e => {
        // eslint-disable-next-line no-console
        console.error("error : ", e);
      });
    }
  }
});