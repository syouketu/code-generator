<template>
  <div class="app-container calendar-list-container">
    <div class="filter-container">
      <el-button class="filter-item" @click="handleCreateImg" type="primary" icon="el-icon-edit">添加横幅广告图片</el-button>
    </div>

    <el-table :data="list" v-loading.body="imgListLoading" border fit highlight-current-row style="width: 100%">
      <el-table-column align="center" label="标题" prop="title">
      </el-table-column>

      <el-table-column align="center" label="链接" width="200" prop="link">
      </el-table-column>

      <el-table-column align="center" label="图片" width="125">
        <template slot-scope="scope">
          <img :src="scope.row.image_url" class="preview_image">
        </template>
      </el-table-column>

      <el-table-column align="center" label="排序" width="50" prop="sort_order">
      </el-table-column>

      <el-table-column align="center" label="操作" width="150">
        <template slot-scope="scope">
          <el-button size="small" type="info" @click="handleUpdateImg(scope.row)">编辑
          </el-button>
          <el-button size="small" type="danger" @click="handleDeleteImg(scope.row,'deleted')">删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <div v-show="!imgListLoading" class="pagination-container">
      <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page.sync="listQuery.page" :page-sizes="[15,30,45]" :page-size="listQuery.limit" layout="total, sizes, prev, pager, next, jumper" :total="total">
      </el-pagination>
    </div>

    <el-dialog :title="textMap[dialogStatus]" :visible.sync="dialogImgFormVisible" :append-to-body="true">
      <el-form class="small-space" :model="imgTemp" label-position="left" label-width="120px" ref="postForm" :rules="rules">
        <el-form-item label="标题:" prop="title">
          <el-input style="width: 280px" v-model="imgTemp.title"></el-input>
        </el-form-item>

        <el-form-item label="链接:">
          <el-input v-model="imgTemp.link"></el-input>
        </el-form-item>

        <el-form-item label="分类图标:" prop="img">
          <ImageManager ref="imageManager" @selectImage="selectImage" :imageUrl="imgTemp.image_url"></ImageManager>
        </el-form-item>

        <el-form-item label="排序:">
          <el-input v-model="imgTemp.sort_order"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogImgFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="submitForm">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { fetchBannerImgList, imgSave, imgDelete } from "api/banner";
import ImageManager from "components/ImageManager";
import moment from "moment";

export default {
  name: "bannerImage",
  props: {
    bannerId: ""
  },
  components: { ImageManager },
  data() {
    return {
      list: null,
      total: null,
      imgListLoading: true,
      listQuery: {
        page: 1,
        limit: 15,
        banner_id: undefined
      },
      imgTemp: {
        banner_id: undefined, // 横幅广告ID
        banner_image_id: undefined, // 横幅广告图片ID
        title: undefined, // 名称
        link: undefined, // 链接
        image: undefined, // 横幅广告图片
        sort_order: undefined, // 排序
        image_url: undefined // 横幅广告图片url
      },
      rules: {
        title: [
          { required: true, message: "请输入横幅广告图片标题", trigger: "blur" }
        ]
      },
      imgList: null,
      imgListLoading: false,
      dialogImgFormVisible: false,
      dialogStatus: "",
      textMap: {
        update: "编辑",
        create: "创建"
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    // 状态格式化
    formatStatus(row, column, status) {
      if (status != null) {
        const statusMap = {
          0: "停用",
          1: "启用"
        };
        return statusMap[status];
      } else {
        return "";
      }
    },
    getList() {
      this.imgListLoading = true;
      this.listQuery.banner_id = this.bannerId;
      console.log(this.listQuery);
      fetchBannerImgList(this.listQuery).then(response => {
        this.list = response.data.data.records;
        this.total = response.data.data.total;
        this.imgListLoading = false;
      });
    },
    selectImage(path, url) {
      this.imgTemp.image = path;
      this.imgTemp.image_url = url;
    },
    handleFilter() {
      this.getList();
    },
    handleSizeChange(val) {
      this.listQuery.limit = val;
      this.getList();
    },
    handleCurrentChange(val) {
      this.listQuery.page = val;
      this.getList();
    },
    handleCreateImg() {
      this.resetTemp();
      this.dialogStatus = "create";
      this.dialogImgFormVisible = true;
    },
    handleUpdateImg(row) {
      this.imgTemp = Object.assign({}, row);
      this.dialogStatus = "update";
      this.dialogImgFormVisible = true;
    },
    handleDeleteImg(row) {
      this.$confirm("请确认是否删除该数据?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(() => {
        imgDelete(row.banner_image_id)
          .then(response => {
            // console.log(response.data);
            if (response.data.status == 1) {
              this.getList();
              this.$notify({
                title: "成功",
                message: "删除成功",
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
            this.fetchSuccess = false;
            console.log(err);
          });
      });
    },
    submitForm() {
      this.$refs.postForm.validate(valid => {
        if (valid) {
          this.imgTemp.banner_id = this.bannerId;
          imgSave(this.imgTemp)
            .then(response => {
              if (response.data.status == 1) {
                this.getList();
                this.$notify({
                  title: "成功",
                  message: "保存成功",
                  type: "success",
                  duration: 2000
                });
                this.dialogImgFormVisible = false;
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
    },
    resetTemp() {
      this.imgTemp = {
        banner_id: undefined, // 横幅广告ID
        banner_image_id: undefined, // 横幅广告图片ID
        title: undefined, // 名称
        link: undefined, // 链接
        image: undefined, // 横幅广告图片
        sort_order: undefined, // 排序
        image_url: undefined // 横幅广告图片url
      };
    }
  }
};
</script>
<style scoped>
.preview_image {
  width: 40px;
  height: 40px;
  margin: 10px 0px 0px 0px;
}
</style>