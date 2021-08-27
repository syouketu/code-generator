<template>
  <div style="width:360px">
    <el-upload ref="singleFileUpload" class="doc-uploader" drag :action="uploadUrl" :before-upload="beforeUpload" :on-remove="handleRemove" :on-success="handleUploadSuccess">
      <i class="el-icon-upload"></i>
      <div class="el-upload__text" v-if="fileUrl">
        {{fileUrl}}  <em>重新上传</em>
      </div>
      <div class="el-upload__text" v-else>将文件拖到此处，或
        <em>点击上传</em>
      </div>
    </el-upload>
  </div>
</template>

<script>
export default {
  name: "singleDocUpload",
  props: {
    fileUrl: ""
  },
  computed: {
    uploadUrl() {
      return api_url + "/common/fileupload/uploadFile?type=doc";
    },
  },
  data() {
    return {
      // tempUrl: '',
      // dataObj: { token: '', key: '' }
    };
  },
  methods: {
    emitInput(data) {
      // console.log(data);
      this.$emit("docData", data);
    },
    handleRemove(file, fileList) {
      for (var i = 0; i < fileList.length; i++) {
        if (fileList[i].url == file.url) {
          fileList.splice(i, 1);
        }
      }
      this.emitInput("");
    },
    handleUploadSuccess(response, file, fileList) {
      for (var i = 0; i < fileList.length - 1; i++) {
        fileList.splice(i, 1);
      }
      // console.log(fileList);
      file.url = response.data.path;
      this.emitInput(file.url);
    },
    beforeUpload(file) {
      // console.log(file);
      // const isDOC = file.type === 'application/msword';
      // const isPDF = file.type === 'application/pdf';
      // const isDOCX = file.type === 'application/vnd.openxmlformats-officedocument.wordprocessingml.document';
      // if (!isDOC && !isDOCX && !isPDF) {
      //     this.$message.error('上传附件只能是 DOC或PDF 格式!');
      //     return false;
      // }
      return true;
    }
  }
};
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
@import "src/styles/mixin.scss";
.upload-container {
  position: relative;
  @include clearfix;
  .doc-uploader {
    width: 100%;
    float: left;
  }
}
</style>
