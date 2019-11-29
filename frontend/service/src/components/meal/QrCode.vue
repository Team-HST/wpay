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
  export default {
    data() {
      return {
        qrText: ''
      }
    },
    created() {
      // QR코드 셋팅
      this.qrText = '{"weddingSeq": 1, "hostSeq": 2}';
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