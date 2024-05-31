<template>
  <div>
    <el-form size="small" :inline="true" label-width="68px">
      <el-form-item>
        <el-button type="primary" @click="add">新增停车场</el-button>
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
      <el-table-column prop="name" label="停车场" align="center"></el-table-column>
      <el-table-column prop="total" label="总车位数" align="center"></el-table-column>
      <el-table-column prop="carAmount" label="已停车位数" align="center"></el-table-column>
      <el-table-column prop="leftAmount" label="空闲车位数" align="center"></el-table-column>
      <el-table-column prop="administrator" label="管理员" align="center"></el-table-column>
      <el-table-column prop="phone" label="联系方式" align="center"></el-table-column>
      <el-table-column prop="address" label="停车场地址" align="center"></el-table-column>
      <el-table-column label="操作" align="center" width="160" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)">修改</el-button>
          <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>


    <!-- 添加或修改用户配置对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="600px" append-to-body>
      <el-form ref="form" :model="form" label-width="80px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="名称" prop="name">
              <el-input v-model="form.name" placeholder="请输入停车场名称" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="地址" prop="address">
              <el-input v-model="form.address" placeholder="请输入停车场地址" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="总数" prop="total">
              <el-input v-model="form.total" placeholder="请输入总车位数" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="可用" prop="leftAmount">
              <el-input v-model="form.leftAmount" placeholder="请输入空闲车位数" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="管理员" prop="administrator">
              <el-input v-model="form.administrator" placeholder="请输入管理员姓名" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="电话" prop="phone">
              <el-input v-model="form.phone" placeholder="请输入管理员联系方式" />
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

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
import { MessageBox } from 'element-ui';
import axios from 'axios';
export default {
  name: "Parkinglots",
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
      ids: [],
      open: false,
      title: ""
    };
  },
  created() {
    //setInterval(this.load, 30000)
    this.load();
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
          saveAs.saveAs(new Blob([wbout], { type: 'application/octet-stream' }), '在库车辆信息表.xlsx')
        } catch (e) {
          if (typeof console !== 'undefined') {
            console.log(e, wbout)
          }
        }
      }, 1000);
      return wbout
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
      this.$request({
        url: '/parkinglots/getAll',
        method: 'POST',
        data: this.pageParams
      })
        .then((res) => {
          console.log(res);
          this.tableData = res.data.items;
          this.total = res.data.counts;
        });
    },
    submitForm() {
      this.open = false;
      this.$request({
        url: '/parkinglots/add',
        method: 'POST',
        data: this.form
      })
        .then((res) => {
          console.log(res)
          if (res.code == 0) {
            this.load();
            this.$message("操作成功！")
          } else {
            this.$message("保存失败，请重试！")
          }
        });
      this.form = {}
    },
    cancel() {
      this.form = {}
      this.open = false;
    },
    handleUpdate(row) {
      console.log(row)
      this.form = row;
      this.title = "修改停车场信息";
      this.open = true;
    },
    handleDelete(row) {
      const name = row.name;
      const id = row.id;
      this.$confirm('是否确认删除【' + name + '】的信息？').then(() => {
        console.log("调用删除程序" + id)
        this.$request({
        url: '/parkinglots/delete/' + id,
        method: 'DELETE'
      })
        .then((res) => {
          console.log(res)
          if (res.code == 0) {
            this.load();
            this.$message("操作成功！")
          } else {
            this.$message("保存失败，请重试！")
          }
        });
      }).then(() => {
        
      }).catch(() => {});
    },
    delUser(id){
      console.log("调用删除程序")
      this.$request({
        url: '/parkinglots/delete' + id,
        method: 'DELETE'
      })
        .then((res) => {
          console.log(res)
          if (res.code == 0) {
            this.load();
            this.$message("操作成功！")
          } else {
            this.$message("保存失败，请重试！")
          }
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
    },
    updateParkinglotId(id) {
      this.$set(this.parkingQueryParams, 'parkinglotid', id);
    },
    add() {
      this.title = "新增停车场信息"
      this.open = true;
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