<template>
  <div>
    <el-form :model="memberQueryParams" size="small" :inline="true" label-width="68px">
      <el-form-item label="会员姓名" prop="name">
        <el-input v-model="memberQueryParams.name" clearable style="width: 120px" />
      </el-form-item>
      <el-form-item label="电话" prop="phone">
        <el-input v-model="memberQueryParams.phone" clearable style="width: 120px" />
      </el-form-item>
      <el-form-item label="车牌号" prop="carplate">
        <el-input v-model="memberQueryParams.carplate" clearable style="width: 120px" />
      </el-form-item>
      <el-form-item label="停车场" prop="parkinglotid">
        <el-select v-model="memberQueryParams.parkinglotName" placeholder="请选择停车场" clearable style="width: 240px">
          <el-option v-for="parkinglot in parkinglots" :key="parkinglot.id" :value="parkinglot.name"/>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="load">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
        <el-button type="primary" plain @click="exportExcel">导出表格</el-button>
        <el-button type="primary" @click="add" >新增会员</el-button>
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
      <el-table-column prop="name" label="姓名" align="center"></el-table-column>
      <el-table-column prop="type" label="会员类型" align="center">
        <template slot-scope="scope">
          <span v-show="scope.row.type == 1">月卡</span>
          <span v-show="scope.row.type == 2">季卡</span>
          <span v-show="scope.row.type == 3">年卡</span>
        </template>
      </el-table-column>
      <!-- <el-table-column prop="basedate" label="可用天数" align="center">
      </el-table-column> -->
      <el-table-column prop="basedate" label="可用天数" align="center">
        <template slot-scope="scope">
          <span >{{dayDifference(scope.row.basedate)}}</span>
        </template>
      </el-table-column>
      <el-table-column prop="carplate" label="车牌号" align="center"></el-table-column>
      <el-table-column prop="phone" label="联系方式" align="center"></el-table-column>
      <el-table-column prop="parkinglotName" label="所在停车场" align="center"></el-table-column>
      <el-table-column prop="createtime" label="创建日期" align="center"></el-table-column>
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
            <el-form-item label="姓名" prop="name">
              <el-input v-model="form.name" placeholder="请输入会员姓名" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="电话" prop="phone">
              <el-input v-model="form.phone" placeholder="请输入会员联系方式" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="类型" prop="type">
              <el-select v-model="form.typeName" placeholder="请选择类型">
                <el-option label="月卡" value="月卡"></el-option>
                <el-option label="季卡" value="季卡"></el-option>
                <el-option label="年卡" value="年卡"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="车牌号" prop="carplate">
              <el-input v-model="form.carplate" placeholder="请输入车牌号" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="停车场" prop="parkinglotName">
              <el-select v-model="form.parkinglotName" placeholder="请选择停车场">
                <el-option v-for="parkinglot in parkinglots" :key="parkinglot.id" :value="parkinglot.name"/>
              </el-select>
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
export default {
  name: "Members",
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
      parkinglots: [],
      memberQueryParams: {
        parkinglotName: "",
        name: "",
        carplate: "",
        phone: "",
        pageParams: []
      },
      title: ""
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
      this.memberQueryParams.pageParams = this.pageParams;
      this.$request({
        url: '/member/getAll',
        method: 'POST',
        data: this.memberQueryParams
      })
        .then((res) => {
          console.log(res);
          this.tableData = res.data.items;
          this.total = res.data.counts;
        });
    },
    dayDifference(basedate) {
      const basedayDate = new Date(basedate);
      const today = new Date();
      const diffTime = basedayDate.getTime() - today.getTime();
      const diffDays = Math.ceil(diffTime / (1000 * 60 * 60 * 24));
      return diffDays < 0 ? 0 : diffDays;
    },
    submitForm() {
      this.open = false;
      this.$request({
        url: '/member/add',
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
      this.title = "修改会员信息";
      this.open = true;
    },
    handleDelete(row) {
      const name = row.name;
      const id = row.id;
      this.$confirm('是否确认删除【' + name + '】的信息？').then(() => {
        console.log("调用删除程序" + id)
        this.$request({
          url: '/member/delete/' + id,
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

      }).catch(() => { });
    },
    delUser(id) {
      console.log("调用删除程序")
      this.$request({
        url: '/member/delete' + id,
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
      this.memberQueryParams.name = "";
      this.memberQueryParams.carplate = "";
      this.memberQueryParams.parkinglotName = "";
      this.memberQueryParams.phone = "";
      this.load()
    },
    updateParkinglotId(id) {
      this.$set(this.parkingQueryParams, 'parkinglotid', id);
    },
    add() {
      this.title = "新增会员信息"
      this.open = true;
    },
    setParkinglots() {
      this.$request
        .get("/parkinglots/getAll")
        .then((res) => {
          console.log(res);
          this.parkinglots = res.data;
        });
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