<template>
    <div class="upload-container">
        <el-upload class="image-uploader" drag :multiple="false" :show-file-list="false" :action="uploadUrl" :on-success="handleImageScucess" :before-upload="beforeUpload">
            <i class="el-icon-upload"></i>
            <div class="el-upload__text">将文件拖到此处，或
                <em>点击上传</em>
            </div>
        </el-upload>
    </div>
</template>

<script>
export default {
    name: 'singleImageUpload',
    props: {
        path: String
    },
    computed: {
        uploadUrl() {
            return api_url + '/common/fileupload/uploadFile?type=image'
        },
    },
    data() {
        return {
            // tempUrl: '',
            // dataObj: { token: '', key: '' }
        };
    },
    methods: {
        rmImage() {
            this.emitInput(null);
        },
        emitInput(data) {
            // this.$emit('path', data.path);
            this.$emit('imageData', data);
        },
        handleImageScucess(response) {
            //console.log(response);;
            this.emitInput(response.data)
        },
        beforeUpload(file) {
            const isJPG = file.type === 'image/jpeg';
            const isPNG = file.type === 'image/png';
            if (!isJPG && !isPNG) {
                this.$message.error('上传头像图片只能是 JPG或PNG 格式!');
                return false;
            }
            return true;
        }
    }
};
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
@import "src/styles/mixin.scss";
.upload-container {
    width: 100%;
    position: relative;
    @include clearfix;
    .image-uploader {
        float: left;
    }
}
</style>
