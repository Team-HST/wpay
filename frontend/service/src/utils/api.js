import $http from 'axios'

export const api = {
  basic: {
    get: (url, data) => {
      return $http.get(url, data);
    },
    post: (url, data) => {
      return $http.post(url, data);
    } 
  },
  auth: {
    get: (url) => {
      return $http.get(url);
    },
    post: (url, data) => {
      return $http.post(url, data, {
        headers: {
          'Authorization': `Bearer ${api.userToken}`
        }
      });
    }
  }
}