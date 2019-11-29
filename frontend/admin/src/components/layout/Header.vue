<template>
    <v-app-bar app dark color="pink lighten-2" clipped-left>      
        <h3 @click="moveMain">W-PAY</h3>
      <v-spacer></v-spacer>
    </v-app-bar>
</template>

<script>
export default {
    methods: {
      moveMain: function() {
        this.$router.push({name:"Main"});
      }
    }
}
</script>