<template>
  <v-app-bar
    app
    color="pink lighten-2"
    dark
  >
    <v-toolbar-title class="font-weight-bold">{{ getUserData.name }} 님 환영합니다!</v-toolbar-title>
    <v-flex class="my-2 text-right">
      <v-btn text @click="userLogout">Logout</v-btn>
    </v-flex>
  </v-app-bar>
</template>

<script>
import { mapGetters } from 'vuex'
export default {
    data() {
      return {
      }
    },
    computed: {
      ...mapGetters(['getUserData'])
    },
    methods: {
      userLogout: function() {
        this.$router.push('/login');
      }
    }
}
</script>