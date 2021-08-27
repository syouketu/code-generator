<template>
  <div class="brand-container">
    <div class="filter-container">
      <el-button
        class="filter-item"
        type="primary"
        size="small"
        icon="el-icon-plus"
        @click="handleCreate"
      >添加</el-button>
    </div>

    <el-table
      v-loading.body="listLoading"
      :data="list"
      border
      fit
      highlight-current-row
      size="small"
    >
      <el-table-column align="center" label="ID" width="80" prop="id" />
      <el-table-column label="项目名称" align="center" prop="name" width="150"/>
      <el-table-column label="父包名" align="center" prop="parentPackage" />
      <el-table-column label="父包模块名" align="center" prop="moduleName" />
      <el-table-column label="Entity包名" align="center" prop="entity" />
      <el-table-column label="Service包名" align="center" prop="service" />
      <el-table-column
        label="Service Impl包名"
        align="center"
        prop="serviceImpl"
      />
      <el-table-column label="Mapper包名" align="center" prop="mapper" />
      <el-table-column label="Mapper XML包名" align="center" prop="mapperXml" />
      <el-table-column
        label="Controller包名"
        align="center"
        prop="controller"
      />
      <el-table-column label="生成前缀" align="center" prop="withPrefix">
        <template slot-scope="scope">
          <span>{{ scope.row.withPrefix === 0 ? "不生成" : "生成" }}</span>
        </template>
      </el-table-column>
      <el-table-column min-width="90" align="center" label="修改时间">
        <template slot-scope="scope">
          <span>{{ scope.row.dateModify | dateFilter }}</span>
        </template>
      </el-table-column>
      <el-table-column align="center" label="操作" width="250">
        <template slot-scope="scope">
          <el-button
            size="small"
            type="primary"
            @click="handleUpdate(scope.row)"
          >编辑</el-button>
          <el-button
            size="small"
            type="primary"
            @click="handleDelete(scope.row)"
          >删除</el-button>
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
        ref="postForm"
        class="small-space"
        :model="temp"
        label-position="left"
        label-width="150px"
        :rules="rules"
        size="small"
      >
        <el-form-item label="项目名称:" prop="name">
          <el-input v-model="temp.name" />
        </el-form-item>
        <el-form-item label="父包名:" prop="parentPackage">
          <el-input
            v-model="temp.parentPackage"
            placeholder="父包名。如果为空，将下面子包名必须写全部， 否则就只需写子包名"
          />
        </el-form-item>
        <el-form-item label="父包模块名:" prop="moduleName">
          <el-input v-model="temp.moduleName" placeholder="默认模块名" />
        </el-form-item>
        <el-form-item label="Entity包名:" prop="entity">
          <el-input v-model="temp.entity" />
        </el-form-item>
        <el-form-item label="Service包名:" prop="service">
          <el-input v-model="temp.service" />
        </el-form-item>
        <el-form-item label="Service Impl包名:" prop="serviceImpl">
          <el-input v-model="temp.serviceImpl" />
        </el-form-item>
        <el-form-item label="Mapper包名:" prop="mapper">
          <el-input v-model="temp.mapper" />
        </el-form-item>
        <el-form-item label="Mapper XML包名:" prop="mapperXml">
          <el-input v-model="temp.mapperXml" />
        </el-form-item>
        <el-form-item label="Controller包名:" prop="controller">
          <el-input v-model="temp.controller" />
        </el-form-item>
        <el-form-item label="生成前缀:" prop="controller">
          <el-switch
            v-model="temp.withPrefix"
            :active-value="1"
            :inactive-value="0"
          />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button
          size="small"
          @click="dialogFormVisible = false"
        >取 消</el-button>
        <el-button
          type="primary"
          size="small"
          @click="submitForm"
        >确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { fetchProjectPage, saveProject, deleteProject } from 'api/project'
import moment from 'moment'

export default {
  name: 'Project',
  components: {},
  filters: {},
  data() {
    return {
      list: null,
      total: null,
      listLoading: true,
      listQuery: {
        page: 1,
        limit: 10
      },
      temp: {
        id: undefined,
        name: undefined,
        parentPackage: 'com.syouketu.modules',
        moduleName: 'common',
        entity: 'entity',
        service: 'service',
        serviceImpl: 'service.impl',
        mapper: 'mapper',
        mapperXml: 'mapper.xml',
        controller: 'api',
        withPrefix: 1
      },
      dialogFormVisible: false,
      dialogStatus: '',
      textMap: {
        update: '编辑',
        create: '创建'
      },
      rules: {}
    }
  },
  created() {
    this.getList()
  },
  methods: {
    getList() {
      this.listLoading = true
      fetchProjectPage(this.listQuery).then((res) => {
        this.list = res.data.data.records
        this.total = res.data.data.total
        this.listLoading = false
      })
    },
    resetForm() {
      this.temp = {
        id: undefined,
        name: undefined,
        parentPackage: 'com.syouketu.modules',
        moduleName: 'common',
        entity: 'entity',
        service: 'service',
        serviceImpl: 'service.impl',
        mapper: 'mapper',
        mapperXml: 'mapper.xml',
        controller: 'api',
        withPrefix: 1
      }
    },
    handleCreate() {
      this.resetForm()
      this.dialogStatus = 'create'
      this.dialogFormVisible = true
    },
    handleUpdate(row) {
      this.temp = Object.assign({}, row)
      this.dialogStatus = 'update'
      this.dialogFormVisible = true
    },
    handleDelete(row) {
      deleteProject(row.id).then((response) => {
        if (response.data.status === 1) {
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
      this.$refs.postForm.validate((valid) => {
        if (valid) {
          saveProject(this.temp)
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
