<template>
  <div>
    <qrcode-stream class="pa-10" :camera="camera" :track="repaint" @decode="onDecode"></qrcode-stream>
    <div class="text-center">
      <v-btn
        class="font-weight-bold white--text ml-2"
        color="pink lighten-2"
        @click="testDialogEvent"
      > 테스트
      </v-btn>
      <v-btn
        class="font-weight-bold white--text ml-2"
        color="pink lighten-2"
        @click="moveMainPage"
      > 취소
      </v-btn>
    </div>
  </div>
</template>

<script>
  import { mapMutations, mapActions } from 'vuex';

  export default {
    data() {
      return {
        camera: 'auto',
        service: {}
      }
    },
    methods: {
      ...mapMutations(['changeIsAccountDialog']),
      ...mapActions(['findHostData']),
      testDialogEvent: function() {
        this.findHostData({hostSeq: 2, weddingSeq: 16});
        this.changeIsAccountDialog();
      },
      /**
       * @description 메인 페이지 이동
       */
      moveMainPage: function() {
        this.$router.push('/main');
      },
      /**
       * @description QR코드 디코딩 문자열
       *
       */
      onDecode: function (content) {
        const hostData = JSON.parse(content);
        // 혼주 정보 입력 (계좌, 기본정보)
        this.findHostData(hostData)
          .then(() => {
            // 송금 다이얼로그 표출
            this.changeIsAccountDialog();
          });
        this.turnCameraOff();
      },
      turnCameraOff () {
        this.camera = 'off'
      },
      /**
       * @description 리더기 색상 변경
       */
      repaint: function (location, ctx) {
        const {
          topLeftCorner,
          topRightCorner,
          bottomLeftCorner,
          bottomRightCorner
          // topLeftFinderPattern,
          // topRightFinderPattern,
          // bottomLeftFinderPattern
        } = location

        ctx.strokeStyle = 'green' // 테두리 색상

        ctx.beginPath();
        ctx.moveTo(topLeftCorner.x, topLeftCorner.y);
        ctx.lineTo(bottomLeftCorner.x, bottomLeftCorner.y);
        ctx.lineTo(bottomRightCorner.x, bottomRightCorner.y);
        ctx.lineTo(topRightCorner.x, topRightCorner.y);
        ctx.lineTo(topLeftCorner.x, topLeftCorner.y);
        ctx.closePath();

        ctx.stroke();
      }
    }
  }
</script>