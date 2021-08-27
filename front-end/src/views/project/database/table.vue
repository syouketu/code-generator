<template>
  <div class="brand-container">
    <div class="filter-container">
      <el-input
        v-model="listQuery.tableName"
        placeholder="表名称"
        class="filter-item"
        size="small"
        style="width:200px"
      ></el-input>
      <el-button
        class="filter-item"
        @click="getList"
        type="primary"
        size="small"
        icon="el-icon-search"
      >查询</el-button>
    </div>
    <el-table
      :data="list"
      v-loading.body="listLoading"
      border
      fit
      highlight-current-row
      size="small"
    >
      <el-table-column align="center" label="ID" width="80" type="index"></el-table-column>
      <el-table-column width="250" label="表名" header-align="center" align="left" prop="tableName"></el-table-column>
      <el-table-column label="注解" header-align="center" align="left" prop="comments"></el-table-column>
      <el-table-column align="center" label="操作" width="350">
        <template slot-scope="scope">
          <router-link
            :to="{path:'/project/column',query:{
            tableName:scope.row.tableName,
            dataBaseId:listQuery.id
            }}"
          >
            <el-button size="small" type="primary" icon="el-icon-plus">编辑</el-button>
          </router-link>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
import { fetchTableList } from "api/database";

export default {
  name: "tableInfo",
  components: {},
  data() {
    return {
      list: null,
      listLoading: true,
      listQuery: {
        page: 1,
        limit: 10,
        tableName: undefined,
        id: this.$route.query.dataBaseId
      }
    };
  },
  created() {
    this.getList();
  },
  filters: {},
  methods: {
    getList() {
      this.listLoading = true;
      fetchTableList(this.listQuery).then(response => {
        this.list = response.data.data;
        this.listLoading = false;
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