<template>
    <div>
        <el-upload class="doc-uploader" :action="uploadUrl" :before-upload="beforeUpload" :on-success="handleUploadSuccess" :on-preview="handlePreview" :on-remove="handleRemove" :file-list="files">
            <el-button size="small" type="primary">点击上传</el-button>
            <!-- <div slot="tip" class="el-upload__tip">只能上传doc/docx/pdf文件</div> -->
        </el-upload>
    </div>
</template>

<script>
export default {
    name: 'docUpload',
    props: {
        attaches: Array
    },
    computed: {
        uploadUrl() {
            return api_url + '/common/fileupload/uploadFile?type=doc'
        },
        files() {
            return this.attaches
        }
    },
    data() {
        return {
            // tempUrl: '',
            // dataObj: { token: '', key: '' }
        };
    },
    methods: {
        emitInput(data) {
            // this.$emit('path', data.path);
            this.$emit('docData', data);
        },
        handleRemove(file, fileList) {
            for (var i = 0; i < fileList.length; i++) {
                if (fileList[i].url == file.url) {
                    fileList.splice(i, 1);
                }
            }
            this.emitInput(fileList);
        },
        handlePreview(file) {
            console.log(file);
        },
        handleUploadSuccess(response, file, fileList) {
            console.log(file);
            file.url = response.data.path;
            this.emitInput(fileList)
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
            // return true;
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
