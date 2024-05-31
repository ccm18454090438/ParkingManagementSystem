<template>
  <div style="text-align: center">
    <el-table :data="tableData" style="width: 100%" :border="none">
      <el-table-column :key="id" :prop="id" :label="name" align="center">
        <template slot-scope="scope">
          <span style="text-align: center; font-size: 24px"
            >[{{ scope.row.name }}] 剩余停车位数量：{{
              scope.row.leftAmount
            }}</span
          >
        </template>
      </el-table-column>
    </el-table>
    <span style="font-size: 30px; color: fontColor">{{ contentText }}</span>
    <div>
      <img
        v-if="showImage"
        :src="imageData"
        alt="QR Code"
        style="width: 300px; height: 300px"
      />
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      tableData: [],
      userId: "1",
      toUserId: "20",
      socket: null,
      fontSize: 30,
      fontColor: "black",
      toUserId: 20,
      contentText: "[NOTICE] 暂无新消息~_~",
      showImage: false,
      imageData:
        "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAASwAAAEsAQAAAABRBrPYAAABrUlEQVR42u3aW3LDMAgFUHbG1llSdkBtiYuQTJp8tjPXnbaJffJD9QBU8W+ul5CR/UlmMi693pra9fu6odcrXU/IOnaD++eN4n7K+YSsZXFTxos73iPK6wnZB+ZjkN7RHd9kXzHNwTlj7GS/M8cQnM8R337Wk9m+fdjzq9tlyILhymE53kgE95HVkCF/m2Gc49BK5rLekHVMsNrlBM6kRW0tg2QHizBaxBXbLUKsTtaxMYdneCMxHvFFhUH2hkVinE7LGjhTP7KOOSazrHjOKZ7ZC1nDDOVD7CHlY3GDrGWl0pdM70Lv2y7ZyVBWrJmd7YCyO5MVhmYTMj3H+DSp6QrZwbbwekj0N0tWQ7Yzx56BBl3N8aT8Fcg2hu3D0afT+CCyZCdr2aoosj0XqyBGKVnHFJNXSzsYPZNatZEdDNPZawfgOZ3JdlZaJbXdhDtkLcv2JoqIXAUxZMk6Vs5WMzXGMli7UmQHy+MbnHyV8NbuMdnBcs1DbFf5X6o2svfMVy2G0alkn9jWUV9j08l6hsPCXPxw7orshaxjeTxdatdM9NSUrGX8R0qyf8p+AP0PVXMCy8WjAAAAAElFTkSuQmCC",
    };
  },
  created() {
    //setInterval(this.load, 30000)
    this.openSocket();
    this.load();
  },
  methods: {
    openSocket() {
      const socketUrl = "ws://localhost:10001/websocket/" + this.userId;
      console.log(socketUrl);
      if (this.socket !== null) {
        this.socket.close();
        this.socket = null;
      }
      this.socket = new WebSocket(socketUrl);
      this.socket.onopen = () => {
        console.log("websocket已打开");
      };
      this.socket.onmessage = (msg) => {
        console.log(msg.data);
        if (msg.data.substring(0, 1) == "d") {
          this.showImage = true;
          this.imageData = msg.data
        } else if(msg.data.substring(2,3) == 'U'){
          this.showImage = false;
          this.contentText = msg.data;
          this.load();
          setTimeout(() => {
            this.contentText = "[NOTICE] 暂无新消息~_~";
          }, 60000);
        }else if(msg.data.substring(2,3) == 'Y'){
          this.load();
        }else{
          this.contentText = msg.data;
          this.load();
          setTimeout(() => {
            this.contentText = "[NOTICE] 暂无新消息~_~";
          }, 60000);
        }
        
      };
      this.socket.onclose = () => {
        console.log("websocket已关闭");
      };
      this.socket.onerror = () => {
        console.log("websocket发生了错误");
      };
    },
    sendMessage() {
      this.socket.send(
        JSON.stringify({
          toUserId: this.toUserId,
          contentText: this.contentText,
        })
      );
      console.log(
        JSON.stringify({
          toUserId: this.toUserId,
          contentText: this.contentText,
        })
      );
    },
    load() {
      this.$request({
        url: "/parkinglots/getAll",
        method: "GET",
      }).then((res) => {
        console.log(res);
        this.tableData = res.data;
      });
    },
  },
};
</script>
