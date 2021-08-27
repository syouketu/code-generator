<template>
  <div class="brand-container">
    <div class="filter-container">
      <el-select
        v-model="listQuery.projectId"
        placeholder="项目"
        style="width: 200px"
        class="filter-item"
        size="small"
        clearable
      >
        <el-option
          v-for="item in projectList"
          :key="item.id"
          :value="item.id"
          :label="item.name"
        ></el-option>
      </el-select>
      <el-button
        class="filter-item"
        @click="handleFilter"
        type="primary"
        size="small"
        icon="el-icon-search"
        >查询</el-button
      >
      <el-button
        class="filter-item"
        @click="handleGenerateTemplate"
        type="primary"
        size="small"
        icon="el-icon-copy-document"
        >生成模板</el-button
      >
      <el-button
        class="filter-item"
        @click="handleCreate"
        type="primary"
        size="small"
        icon="el-icon-plus"
        >添加</el-button
      >
    </div>

    <el-table
      :data="list"
      v-loading.body="listLoading"
      border
      fit
      highlight-current-row
      size="small"
    >
      <el-table-column
        align="center"
        label="ID"
        width="100"
        prop="id"
      ></el-table-column>
      <el-table-column label="展开" align="center" type="expand">
        <template slot-scope="scope">
          <div v-highlight>
            <pre>
              <code v-html="scope.row.content"></code>
              </pre>
          </div>
        </template>
      </el-table-column>
      <el-table-column
        width="140"
        label="项目名称"
        align="center"
        prop="project"
      ></el-table-column>
      <el-table-column width="200" label="类型" align="center" prop="type">
        <template slot-scope="scope">{{
          scope.row.type | formatTemplateType
        }}</template>
      </el-table-column>
      <el-table-column
        label="内容"
        align="center"
        prop="content"
        show-overflow-tooltip
      ></el-table-column>
      <el-table-column align="center" label="操作" width="250">
        <template slot-scope="scope">
          <el-button
            size="small"
            type="primary"
            @click="handleUpdate(scope.row)"
            >编辑</el-button
          >
          <el-button
            size="small"
            type="primary"
            @click="handleDelete(scope.row)"
            >删除</el-button
          >
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

    <el-dialog
      :title="textMap[dialogStatus]"
      :visible.sync="dialogFormVisible"
      size="tiny"
    >
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
          <el-select
            v-model="temp.projectId"
            placeholder="归属项目"
            style="width: 400px"
          >
            <el-option
              v-for="item in projectList"
              :key="item.id"
              :value="item.id"
              :label="item.name"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="模板类型:" prop="type">
          <el-select v-model="temp.type" placeholder="模板类型">
            <el-option
              v-for="item in templateTypeList"
              :key="item.value"
              :value="item.value"
              :label="item.label"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="模板内容:" prop="content">
          <el-input
            v-model="temp.content"
            type="textarea"
            :autosize="{ minRows: 10 }"
            :rows="10"
          ></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false" size="small"
          >取 消</el-button
        >
        <el-button type="primary" @click="submitForm" size="small"
          >确 定</el-button
        >
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { fetchProjectList } from 'api/project'
import {
  fetchTemplatePage,
  saveTemplate,
  deleteTemplate,
  generateTemplate
} from 'api/template'
import moment from 'moment'
import { tempalteType } from 'data/basic_data'

export default {
  name: 'Template',
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
      statusList: [
        { label: '启用', value: 1 },
        { label: '禁用', value: 0 }
      ],
      temp: {
        id: undefined,
        projectId: undefined,
        type: undefined,
        content: undefined
      },
      dialogFormVisible: false,
      dialogStatus: '',
      textMap: {
        update: '编辑',
        create: '创建'
      },
      templateTypeList: tempalteType,
      projectList: [],
      rules: {}
    }
  },
  created() {
    this.getList()
    this.getProjectList()
  },
  filters: {},
  methods: {
    getProjectList() {
      fetchProjectList({}).then((res) => {
        this.projectList = res.data.data
      })
    },
    getList() {
      this.listLoading = true
      fetchTemplatePage(this.listQuery).then((res) => {
        this.list = res.data.data.records
        this.total = res.data.data.total
        this.listLoading = false
      })
    },
    handleSizeChange(val) {
      this.listQuery.limit = val
      this.getList()
    },
    handleFilter() {
      this.getList()
    },
    handleCurrentChange(val) {
      this.listQuery.page = val
      this.getList()
    },
    handleCurrentChange(val) {
      this.listQuery.page = val
      this.getList()
    },
    handleGenerateTemplate() {
      if (this.listQuery.projectId == undefined) {
        this.$message({
          message: '请选择需要生成模板的项目!',
          type: 'warning'
        })
        return
      }
      generateTemplate(this.listQuery.projectId).then((response) => {
        if (response.data.status == 1) {
          this.getList()
          this.$notify({
            title: '成功',
            message: '生成模板成功 ',
            type: 'success',
            duration: 2000
          })
        } else {
          this.$message({
            message: response.data.message,
            type: 'error'
          })
        }
      })
    },
    handleCreate() {
      this.temp = Object.assign({}, {})
      this.dialogStatus = 'create'
      this.dialogFormVisible = true
    },
    handleUpdate(row) {
      this.temp = Object.assign({}, row)
      this.dialogStatus = 'update'
      this.dialogFormVisible = true
    },
    handleDelete(row) {
      deleteTemplate(row.db_name).then((response) => {
        if (response.data.status == 1) {
          this.getList()
          this.$notify({
            title: '成功',
            message: '删除数据库成功',
            type: 'success',
            duration: 2000
          })
        } else {
          this.$message({
            message: response.data.message,
            type: 'error'
          })
        }
      })
    },
    submitForm() {
      console.log(this.temp)
      this.temp.project = undefined
      this.$refs.postForm.validate((valid) => {
        if (valid) {
          saveTemplate(this.temp)
            .then((response) => {
              // console.log(response.data);
              if (response.data.status === 1) {
                this.getList()
                this.$notify({
                  title: '成功',
                  message: '保存成功',
                  type: 'success',
                  duration: 2000
                })
                this.dialogFormVisible = false
              } else {
                this.$message({
                  message: response.data.message,
                  type: 'error'
                })
              }
            })
            .catch((err) => {
              this.fetchSuccess = false
              console.log(err)
            })
        }
      })
    }
  }
}
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