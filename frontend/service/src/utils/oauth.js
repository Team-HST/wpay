import axios from 'axios'
import store from '@/store/index'

axios.interceptors.request.use(function(config) {
  const user = store.getters.getUserData;

  if (user) {
    config.headers.Authorization = `Bearer ${user.token}`;
  }

  return config;
}, function(err) {
  return Promise.reject(err);
});

export default axios;