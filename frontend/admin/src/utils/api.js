import $http from 'axios'

export const api = {
  userToken: null,
  basic: {
    get: (url, data) => {
      return $http.get(url, data);
    },
    post: (url, data) => {
      return $http.post(url, data);
    } 
  },
  auth: {
    get: (url, data) => {
      return $http.get(url, data, {
        headers: {
          'Authorization': `Bearer ${this.userToken}`
        }
      });
    },
    post: (url, data) => {
      return $http.post(url, data, {
        headers: {
          'Authorization': `Bearer ${this.userToken}`
        }
      });
    }
  },
  setUserToken: function(token) {
    this.userToken = token;
  }
}