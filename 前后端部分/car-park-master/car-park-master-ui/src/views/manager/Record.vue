<template>
  <div>
    <el-form :model="parkingQueryParams" size="small" :inline="true" label-width="68px">
      <el-form-item label="停车场" prop="parkinglotid">
        <el-select v-model="parkingQueryParams.parkinglotName" placeholder="请选择停车场" clearable style="width: 240px">
          <el-option v-for="parkinglot in parkinglots" :key="parkinglot.id" :value="parkinglot.name"
            @click.native="updateParkinglotId(parkinglot.id)" />
        </el-select>
      </el-form-item>
      <el-form-item label="车牌号" prop="carplate">
        <el-input v-model="parkingQueryParams.carplate" clearable style="width: 120px" />
      </el-form-item>
      <el-form-item label="入库日期">
        <el-date-picker v-model="parkingQueryParams.dateRange" style="width: 240px" value-format="yyyy-MM-dd HH:mm:ss"
          type="daterange" range-separator="-" start-placeholder="开始日期" end-placeholder="结束日期"></el-date-picker>
      </el-form-item>
      <el-form-item label="出库日期">
        <el-date-picker v-model="parkingQueryParams.dateRange2" style="width: 240px" value-format="yyyy-MM-dd HH:mm:ss"
          type="daterange" range-separator="-" start-placeholder="开始日期" end-placeholder="结束日期"></el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="load">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
        <el-button type="primary" plain @click="exportExcel">导出表格</el-button>
      </el-form-item>
    </el-form>

    <!-- <div style="margin: 10px 0">
      <el-button type="primary" plain @click="exportExcel">导出</el-button>
    </div> -->
    <el-table id="exportTab" :data="tableData" stripe
      :header-cell-style="{ backgroundColor: 'aliceblue', color: '#666' }" @selection-change="handleSelectionChange">
      <el-table-column label="序号" width="80" align="center">
        <template #default="scope">
          {{ scope.$index + (pageParams.pageNo - 1) * pageParams.pageSize + 1 }}
        </template>
      </el-table-column>
      <el-table-column prop="carplate" label="车牌号" align="center"></el-table-column>
      <el-table-column prop="parkinglotName" label="停车场" align="center"></el-table-column>
      <!-- <el-table-column prop="phone" label="会员电话" align="center"></el-table-column> -->
      <el-table-column prop="intime" label="入库时间" align="center"></el-table-column>
      <el-table-column prop="outtime" label="出库时间" align="center">
        <template slot-scope="scope">
            <span v-show="scope.row.outtime=='2099-12-31 23:59:59'"></span>
            <span v-show="scope.row.outtime!='2099-12-31 23:59:59'">{{ scope.row.outtime }}</span>
        </template>
      </el-table-column>
      <el-table-column label="停车时长" align="center">
        <template slot-scope="scope">
            {{computeDuration(scope.row.intime, scope.row.outtime)}}
        </template>
      </el-table-column>
    </el-table>

    <div style="margin: 10px 0">
      <el-pagination @current-change="handleCurrentChange" :current-page="pageParams.pageNo"
        :page-size="pageParams.pageSize" layout="total, prev, pager, next" :total="total">
      </el-pagination>
    </div>
  </div>
</template>

<script>
import { saveAs } from 'file-saver'
import * as XLSX from 'xlsx';
export default {
  name: "Record",
  data() {
    return {
      tableData: [], // 所有的数据
      pageParams: {
        pageNo: 1, // 当前的页码
        pageSize: 10, // 每页显示的个数
      },
      //syncInterval: "",
      total: 0,
      fromVisible: false,
      form: {},
      parkingQueryParams: {
        parkinglotid: -1,
        parkinglotName: "",
        carplate: "",
        dateRange: [],
        dateRange2: [],
        pageParams: []
      },
      parkinglots: [],
      ids: []
    };
  },
  created() {
    //setInterval(this.load, 30000)
    this.load();
    this.setParkinglots();
  },
  methods: {
    // 导出表格为Excel
    exportExcel() {
      //this.getList(); //这个是重新2加载表格,意思是每次点击导出的时候都要重新加载一次表格以确定是否在导出前新增了表格内的数据。

      //settimeout:延迟加载 如果不延迟一点加载则会出现导出的数据不是最新的。
      let wbout;
      setTimeout(() => {
        /* generate workbook object from table */
        var xlsxParam = { raw: true } // 导出的内容只做解析，不进行格式转换
        var wb = XLSX.utils.table_to_book(document.querySelector('#exportTab'), xlsxParam)

        /* get binary string as output */
        var wbout = XLSX.write(wb, { bookType: 'xlsx', bookSST: true, type: 'array' })
        try {
          saveAs.saveAs(new Blob([wbout], { type: 'application/octet-stream' }), '车辆信息表.xlsx')
        } catch (e) {
          if (typeof console !== 'undefined') {
            console.log(e, wbout)
          }
        }
      }, 1000);
      return wbout
    },
    delBatch() {
      if (!this.ids.length) {
        this.$message.warning("请选择数据");
        return;
      }
      this.$confirm("您确认批量删除这些数据吗？", "确认删除", {
        type: "warning",
      })
        .then((response) => {
          this.$request
            .delete("/user/delete/batch", { data: this.ids })
            .then((res) => {
              if (res.code === "200") {
                // 表示操作成功
                this.$message.success("操作成功");
                this.load(1);
              } else {
                this.$message.error(res.msg); // 弹出错误的信息
              }
            });
        })
        .catch(() => { });
    },
    del(id) {
      this.$confirm("是否强制退出该设备？", "确认", { type: "warning" })
        .then((response) => {
          this.$request.delete("/dool/delete/" + id).then((res) => {
            if (res.code === "200") {
              // 表示操作成功
              this.$message.success("操作成功");
              this.load(1);
            } else {
              this.$message.error(res.msg); // 弹出错误的信息
            }
          });
        })
        .catch(() => { });
    },
    handleEdit(row) {
      // 编辑数据
      this.form = JSON.parse(JSON.stringify(row));
      console.log(this.form); // 给form对象赋值  注意要深拷贝数据
      this.fromVisible = true; // 打开弹窗
    },
    handleSelectionChange(rows) {
      // 当前选中的所有的行数据
      this.ids = rows.map((v) => v.id);
    },
    reset() {
      this.load();
    },
    load() {
      this.parkingQueryParams.pageParams = this.pageParams
      this.$request({
        url: '/parking/getAll',
        method: 'POST',
        data: this.parkingQueryParams
      })
        .then((res) => {
          console.log(res.data);
          this.tableData = res.data.items;
          this.total = res.data.counts;
          //this.total = res.data.total;
          // this.carnum = res.data.carnum;
          // this.cardnum = res.data.cardnum;
          // this.parkin = res.data.parkin; 
        });
    },
    setParkinglots() {
      this.$request
        .get("/parkinglots/getAll")
        .then((res) => {
          console.log(res);
          this.parkinglots = res.data;
        });
    },
    handleEdit(row) {
      // 编辑数据
      this.form = JSON.parse(JSON.stringify(row));
      console.log(this.form); // 给form对象赋值  注意要深拷贝数据
      this.fromVisible = true; // 打开弹窗
    },
    handleCurrentChange(page) {
      this.pageParams.pageNo = page;
      this.load();
    },
    resetQuery() {
      this.parkingQueryParams.dateRange = [];
      this.parkingQueryParams.carplate = "";
      this.parkingQueryParams.parkinglotid = -1;
      this.parkingQueryParams.parkinglotName = "";
      this.load()
    },
    updateParkinglotId(id) {
      this.$set(this.parkingQueryParams, 'parkinglotid', id);
    },
    computeDuration(intime, outtime){
      if(outtime == '2099-12-31 23:59:59'){
        return "";
      }
      const startTime = new Date(intime).getTime(); // 获取入场时间的时间戳
      const endTime = new Date(outtime).getTime(); // 获取出场时间的时间戳
      const timeDiff = endTime - startTime; // 计算时间差值，单位为毫秒
    
      // 将时间差值格式化为*天*时*分*秒的字符串
      const days = Math.floor(timeDiff / (1000 * 60 * 60 * 24));
      const hours = Math.floor((timeDiff % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));
      const minutes = Math.floor((timeDiff % (1000 * 60 * 60)) / (1000 * 60));
      const seconds = Math.floor((timeDiff % (1000 * 60)) / 1000);
      if(days == 0){

        return `${hours}时${minutes}分${seconds}秒`
      }
      return `${days}天${hours}时${minutes}分${seconds}秒`;
    },
    save() {
      // 保存按钮触发的逻辑  它会触发新增或者更新
      this.fromVisible = false;
      this.$request({
        url: '/dool/update',
        method: 'POST',
        data: this.form
      })
        .then((res) => {
          if (res.code === "200") {
            // 表示成功保存
            this.$message.success("保存成功");
            this.load(1);
            this.fromVisible = false;
          } else {
            this.$message.error(res.msg); // 弹出错误的信息
          }
        });
    },
  },
};
</script>

<style scoped></style>