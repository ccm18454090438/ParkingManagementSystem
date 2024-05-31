<template>
  <div class="upload-container">
    <div class="upload-group">
      <input type="file" ref="fileInput" @change="handleFileChange1" accept="image/*" />
      <button @click="takePhoto">拍照入库</button>
      <img v-if="imageUrl" :src="imageUrl" alt="Preview" />
      <button @click="uploadImage">上传入库</button>
    </div>

    <div class="upload-group">
      <input type="file" ref="fileInput" @change="handleFileChange2" accept="image/*" />
      <button @click="takePhoto">拍照出库</button>
      <img v-if="imageUrl2" :src="imageUrl2" alt="Preview" />
      <button @click="uploadImage2">上传出库</button>
    </div>
  </div>
</template>

<script>
import axios from 'axios';
export default {
  data() {
    return {
      imageUrl: null,
      imageUrl2: null,
      file: null,
      file2: null
    };
  },
  methods: {
    handleFileChange1(event) {
      const file = event.target.files[0];
      this.file = file;
      this.imageUrl = URL.createObjectURL(file);
    },
    handleFileChange2(event) {
      const file2 = event.target.files[0];
      this.file2 = file2;
      this.imageUrl2 = URL.createObjectURL(file2);
    },
    takePhoto() {
      const video = document.createElement('video');
      const canvas = document.createElement('canvas');
      const context = canvas.getContext('2d');

      navigator.mediaDevices.getUserMedia({ video: true })
        .then(stream => {
          video.srcObject = stream;
          video.play();
          video.onloadedmetadata = () => {
            canvas.width = video.videoWidth;
            canvas.height = video.videoHeight;
            context.drawImage(video, 0, 0, canvas.width, canvas.height);
            this.imageUrl = canvas.toDataURL('image/png');
            video.srcObject.getVideoTracks().forEach(track => track.stop());
          };
        })
        .catch(error => {
          console.error('Error accessing the camera: ', error);
        });
    },
    uploadImage() {
      const formData = new FormData();
      formData.append('file', this.file);
      axios.post('http://localhost:9090/car/checkin', formData)
        .then(response => {
          console.log(response)
          // 处理上传成功的响应
          if (response.status == 200) {
            this.$message.success(response.data);
            this.$router.push("/car")
          } else {
            this.$message.error("车辆识别失败，请重试")
          }
        })
      // .catch(error => {
      //   // 处理上传失败的错误
      //   console.error(error);
      // });
    },
    uploadImage2() {
      const formData = new FormData();
      formData.append('file', this.file2);
      axios.post('http://localhost:9090/car/checkout', formData)
        .then(response => {
          console.log(response)
          // 处理上传成功的响应
          if (response.status == 200) {
            this.$message.success(response.data);
            this.$router.push("/orders")
          } else {
            this.$message.error("车辆识别失败，请重试")
          }
        })
      // .catch(error => {
      //   // 处理上传失败的错误
      //   console.error(error);
      // });
    }
  }
};
</script>

<style scoped>
.upload-container {
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
}

.upload-group {
  display: flex;
  align-items: center;
  margin-bottom: 40px;
}
</style>
