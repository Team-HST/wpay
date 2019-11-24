<template>
  <div>
    <md-card-content>
      <div class="md-layout">
        <div class="md-layout-item md-small-size-100 md-size-33">
          <md-field>
            <label>Company (disabled)</label>
            <md-input v-model="disabled" disabled></md-input>
          </md-field>
        </div>
            <div class="md-layout-item md-small-size-100 md-size-33">
          <md-field>
            <label>User Name</label>
            <md-input v-model="username" type="text"></md-input>
          </md-field>
        </div>
      </div>
    </md-card-content>
  </div>  
</template>

<script>
export default {
    data() {
      return {
      }
    }
}
</script>>