<template>
  <div class="brand-container">
    <div class="filter-container">
      <el-select
        v-model="listQuery.projectId"
        placeholder="项目"
        style="width:200px"
        class="filter-item"
        size="small"
      >
        <el-option v-for="item in projectList" :key="item.id" :value="item.id" :label="item.name"></el-option>
      </el-select>
      <el-button
        class="filter-item"
        @click="handleFilter"
        type="primary"
        size="small"
        icon="el-icon-search"
      >查询</el-button>
      <el-button
        class="filter-item"
        @click="handleCreate"
        type="primary"
        size="small"
        icon="el-icon-plus"
      >添加</el-button>
    </div>

    <el-table
      :data="list"
      v-loading.body="listLoading"
      border
      fit
      highlight-current-row
      size="small"
    >
      <el-table-column align="center" label="ID" width="80" prop="id"></el-table-column>
      <el-table-column width="140" label="项目名称" align="center" prop="project"></el-table-column>
      <el-table-column width="140" label="名称" align="center" prop="name"></el-table-column>
      <el-table-column label="数据库连接" align="left" prop="url" header-align="center"></el-table-column>
      <el-table-column width="100" label="用户名" align="center" prop="user"></el-table-column>
      <el-table-column width="140" label="密码" align="center" prop="passwd"></el-table-column>
      <el-table-column width="100" label="SCHEMA" align="center" prop="dbSchema"></el-table-column>
      <el-table-column width="100" label="类型" align="center" prop="dbType">
        <template slot-scope="scope">
          <span>{{scope.row.dbType|formatDbType}}</span>
        </template>
      </el-table-column>
      <el-table-column align="center" label="操作" width="250">
        <template slot-scope="scope">
          <el-button size="small" type="primary" @click="handleUpdate(scope.row)">编辑</el-button>
          <el-button size="small" type="primary" @click="handleDelete(scope.row)">删除</el-button>
          <router-link :to="{path:'/project/table',query:{dataBaseId:scope.row.id}}">
            <el-button size="small" type="primary" icon="el-icon-plus">选择表</el-button>
          </router-link>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
        @size-change="getList"
        @current-change="getList"
        :current-page.sync="listQuery.page"
        :page-sizes="[10,20,30, 50]"
        :page-size="listQuery.limit"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total"
      ></el-pagination>

    <el-dialog :title="textMap[dialogStatus]" :visible.sync="dialogFormVisible" size="tiny">
      <el-form
        class="small-space"
        :model="temp"
        label-position="left"
        label-width="100px"
        ref="postForm"
        :rules="rules"
        size="small"
      >
        <el-form-item label="归属项目:" prop="projectId">
          <el-select v-model="temp.projectId" placeholder="归属项目" style="width:400px">
            <el-option
              v-for="item in projectList"
              :key="item.id"
              :value="item.id"
              :label="item.name"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="名称(唯一):" prop="name">
          <el-input v-model="temp.name"></el-input>
        </el-form-item>
        <el-form-item label="数据库类型:" prop="dbType">
          <el-select v-model="temp.dbType" placeholder="数据库类型" @change="handleTypeChange">
            <el-option
              v-for="item in dbTypeList"
              :key="item.value"
              :value="item.value"
              :label="item.label"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="URL:" prop="url">
          <el-input v-model="temp.url" type="textarea"></el-input>
        </el-form-item>
        <el-form-item label="用户名:" prop="user">
          <el-input v-model="temp.user"></el-input>
        </el-form-item>
        <el-form-item label="密码:" prop="passwd">
          <el-input v-model="temp.passwd"></el-input>
        </el-form-item>
        <el-form-item label="schema:" prop="dbSchema">
          <el-input v-model="temp.dbSchema"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false" size="small">取 消</el-button>
        <el-button type="primary" @click="submitForm" size="small">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { fetchProjectList } from "api/project";
import { fetchDatabasePage, saveDatabase, deleteDatabase } from "api/database";
import moment from "moment";
import { dbType } from "data/basic_data";

export default {
  name: "database",
  components: {},
  data() {
    return {
      list: null,
      total: null,
      listLoading: true,
      listQuery: {
        page: 1,
        limit: 10,
        projectId: undefined
      },
      statusList: [{ label: "启用", value: 1 }, { label: "禁用", value: 0 }],
      temp: {
        id: undefined,
        projectId: undefined,
        name: undefined,
        url: undefined,
        driver: undefined,
        user: undefined,
        passwd: undefined,
        dbSchema: undefined,
        dbType: undefined
      },
      dialogFormVisible: false,
      dialogStatus: "",
      textMap: {
        update: "编辑",
        create: "创建"
      },
      dbTypeList: dbType,
      projectList: [],
      rules: {}
    };
  },
  created() {
    this.getList();
    this.getProjectList();
  },
  filters: {},
  methods: {
    getProjectList() {
      fetchProjectList({}).then(res => {
        this.projectList = res.data.data;
      });
    },
    getList() {
      this.listLoading = true;
      fetchDatabasePage(this.listQuery).then(res => {
        this.list = res.data.data.records;
        this.total = res.data.data.total;
        this.listLoading = false;
      });
    },
    handleFilter() {
      this.getList();
    },
    handleCurrentChange(val) {
      this.listQuery.page = val;
      this.getList();
    },
    handleCurrentChange(val) {
      this.listQuery.page = val;
      this.getList();
    },
    handleTypeChange(type) {
      var dbType = this.dbTypeList.find(item => {
        return item.value === type;
      });
      console.log(dbType);
      if (dbType) {
        this.temp.driver = dbType.driver;
        console.log(this.temp.driver);
      }
    },
    handleCreate() {
      this.temp = Object.assign({}, {});
      this.dialogStatus = "create";
      this.dialogFormVisible = true;
    },
    handleUpdate(row) {
      this.temp = Object.assign({}, row);
      this.temp.project = undefined;
      this.dialogStatus = "update";
      this.dialogFormVisible = true;
    },
    handleDelete(row) {
      deleteDatabase(row.db_name).then(response => {
        if (response.data.status == 1) {
          this.getList();
          this.$notify({
            title: "成功",
            message: "删除数据库成功",
            type: "success",
            duration: 2000
          });
        } else {
          this.$message({
            message: response.data.message,
            type: "error"
          });
        }
      });
    },
    submitForm() {
      console.log(this.temp);
      this.$refs.postForm.validate(valid => {
        if (valid) {
          saveDatabase(this.temp)
            .then(response => {
              // console.log(response.data);
              if (response.data.status == 1) {
                this.getList();
                this.$notify({
                  title: "成功",
                  message: "保存成功",
                  type: "success",
                  duration: 2000
                });
                this.dialogFormVisible = false;
              } else {
                this.$message({
                  message: response.data.message,
                  type: "error"
                });
              }
            })
            .catch(err => {
              this.fetchSuccess = false;
              console.log(err);
            });
        }
      });
    }
  }
};
</script>
<style scoped>
.brand-container .filter-container {
  padding: 10px 0px 10px 20px;
}

.brand-container img {
  width: 50px;
  height: 30px;
}
</style>