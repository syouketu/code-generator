<template>
  <div class="brand-container">
    <div class="filter-container">
      <el-button
        class="filter-item"
        @click="handleSave"
        type="warning"
        size="small"
        icon="el-icon-edit-outline"
      >保存</el-button>
      <el-button
        class="filter-item"
        @click="handleGenerate"
        type="success"
        size="small"
        icon="el-icon-setting"
      >生成</el-button>
    </div>

    <el-card>
      <div slot="header" class="clearfix">
        <el-row>
          <el-col :span="6">
            表备注：
            <span>
              <el-input
                v-model="tableInfo.comments"
                placeholder="表名"
                size="samll"
                style="width:200px"
              ></el-input>
            </span>
          </el-col>
          <el-col :span="10">
            父包名：
            <el-input
              v-model="packageConfig.parent"
              placeholder="父包名"
              size="samll"
              style="width:250px;padding-right:25px"
            ></el-input>模块名:
            <el-input
              v-model="packageConfig.moduleName"
              placeholder="模块名"
              size="samll"
              style="width:100px"
            ></el-input>
          </el-col>
          <el-col :span="7">
            可检索字段：
            <el-tag
              v-for="queryItem in queryColumns"
              :key="queryItem"
              style="margin-right:10px;"
            >{{queryItem}}</el-tag>
          </el-col>
        </el-row>
      </div>
      <div>
        <el-table
          ref="colTable"
          :data="tableInfo.columnList"
          v-loading.body="listLoading"
          border
          fit
          highlight-current-row
          size="small"
          @selection-change="handleSelectionChange"
        >
          <el-table-column type="selection" width="55"></el-table-column>
          <el-table-column align="center" label="ID" width="100" type="index"></el-table-column>
          <el-table-column
            width="140"
            label="字段名"
            header-align="center"
            align="left"
            prop="columnName"
          ></el-table-column>
          <el-table-column label="类型" header-align="center" align="left" prop="columnType"></el-table-column>
          <el-table-column label="备注" header-align="center" align="left">
            <template slot-scope="scope">
              <el-input v-model="scope.row.comments" placeholder="备注" size="small"></el-input>
            </template>
          </el-table-column>
        </el-table>
      </div>
    </el-card>
  </div>
</template>

<script>
import { fetchColumnList, saveTable } from "api/database";
import { generateCode } from "api/project";

export default {
  name: "columenInfo",
  components: {},
  data() {
    return {
      listLoading: false,
      packageName: "",
      moduleName: "",
      tableInfo: {
        projectId: undefined,
        dataBaseId: undefined,
        tableName: undefined,
        comments: undefined,
        columnList: [],
        queryColumns: []
      },
      packageConfig: {
        parent: "",
        moduleName: "",
        entity: "entity",
        service: "service",
        serviceImpl: "service.impl",
        mapper: "mapper",
        xml: "mapper.xml",
        controller: "web"
      },
      listQuery: {
        page: 1,
        limit: 10,
        id: this.$route.query.dataBaseId,
        tableName: this.$route.query.tableName
      },
      queryColumns: []
    };
  },
  created() {
    this.getList();
  },
  filters: {},
  methods: {
    getList() {
      this.listLoading = true;
      fetchColumnList(this.listQuery).then(response => {
        this.tableInfo = response.data.data;
        this.tableInfo.dbName = this.$route.query.dbName;
        this.packageName = this.tableInfo.parent;
        this.moduleName = this.tableInfo.moduleName;
        this.packageConfig = this.tableInfo.packageConfig;
        if (
          this.tableInfo.queryColumns &&
          this.tableInfo.queryColumns.length > 0
        ) {
          setTimeout(() => {
            // console.log(this.tableInfo.queryColumns);
            this.tableInfo.columnList.forEach(col => {
              this.tableInfo.queryColumns.forEach(qcol => {
                // console.log(col);
                if (col.columnName == qcol) {
                  console.log(qcol);
                  this.$refs.colTable.toggleRowSelection(col, true);
                }
              });
            });
          }, 500);
        }
        this.listLoading = false;
      });
    },
    handleSelectionChange(val) {
      if (val) {
        this.queryColumns = val.map(item => {
          return item.columnName;
        });
      }
      // console.log(this.queryColumns);
    },
    handleSave() {
      console.log(this.tableInfo);
      saveTable(this.tableInfo)
        .then(response => {
          console.log(response.data);
          if (response.data.status == 1) {
            this.getList();
            this.$notify({
              title: "成功",
              message: "保存表备注成功",
              type: "success",
              duration: 2000
            });
          } else {
            this.$message({
              message: response.data.message,
              type: "error"
            });
          }
        })
        .catch(err => {
          console.log(err);
        });
    },
    handleGenerate() {
      this.tableInfo.queryColumns = this.queryColumns;
      generateCode(this.tableInfo, this.packageConfig)
        .then(response => {
          console.log(response.data);
          if (response.data.status == 1) {
            document.location.href = response.data.data;
          } else {
            this.$message({
              message: response.data.message,
              type: "error"
            });
          }
        })
        .catch(err => {
          console.log(err);
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