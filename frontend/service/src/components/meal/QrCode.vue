<template>
  <div>
    <div class="text-center">
      <qr-code
        class="d-inline-block pt-12 pb-12"
        :text="qrText"
      >
      </qr-code>
      <hr />
      <div class="text-center">
        <v-btn
          class="font-weight-bold white--text ml-2 mt-3"
          color="pink lighten-2"
          @click="closeMealQR"
        > 나가기
        </v-btn>
      </div>
    </div>
  </div>
</template>

<script>
  import { mapGetters } from 'vuex'

  export default {
    data() {
      return {
        qrText: '',
        service: {}
      }
    },
    computed: {
      ...mapGetters(['getUserData'])
    },
    created() {
      this.service = {
        getUserQrCode: () => {
          this.$http.get(`/api/meal-tickets/users/${this.getUserData.sequence}/today-active-users`)
            .then(response => {
              this.qrText = JSON.stringify(response.data);
              console.log(this.qrText);
            })
            .catch(error => {
              if (error.response.data.code === 4030) {
                alert('발급 된 식권이 존재하지 않습니다.');
                this.$router.push('/main');
              }
            });
        }
      }
    },
    mounted() {
      this.service.getUserQrCode();
    },
    methods: {
      /**
       * @description 식권 QR 나가기 이벤트
       */
      closeMealQR: function() {
        this.$router.push('/main');
      }
    }
  }
</script>