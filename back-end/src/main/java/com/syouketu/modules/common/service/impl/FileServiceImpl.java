package com.syouketu.modules.common.service.impl;

import com.alibaba.fastjson.JSON;
import com.qiniu.http.Client;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import com.syouketu.config.MyProperties;
import com.syouketu.modules.common.service.IFileService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.File;

/**
 * 七牛云接口服务实现类
 *
 * @author: JXI
 * @description:
 * @date: Created on 11/18/2020
 */
@Service
public class FileServiceImpl implements IFileService {

    private String accessKey;
    private String secretKey;
    private String bucketName;
    private String fileDomain;

    private Client client;
    private final Configuration configuration;
    private final UploadManager uploadManager;
    private final BucketManager bucketManager;
    private final Auth auth;

    public FileServiceImpl(MyProperties myProperties) {
        accessKey = myProperties.getOss().getAccessKey();
        secretKey = myProperties.getOss().getSecretKey();
        bucketName = myProperties.getOss().getBucketName();
        fileDomain = myProperties.getOss().getFileDomain();
        auth = Auth.create(accessKey, secretKey);
        configuration = new Configuration(Region.huanan());
        bucketManager = new BucketManager(auth, configuration);
        uploadManager = new UploadManager(configuration);
    }

    private String getUpToken() {
        return auth.uploadToken(bucketName);
    }

    //覆盖上传模式的凭证
    private String getUpToken(String fileKey) {
        return auth.uploadToken(bucketName, fileKey);
    }

    // 封装文件完整URL地址
    @Transactional(rollbackFor = Exception.class)
    public String getResAccessUrl(@RequestParam String path) {
        String url = fileDomain + "/" + path;
        return url;
    }

    @Override
    public String uploadFile(String key, File file) throws Exception {
        Response response = uploadManager.put(file, key, getUpToken(key));
        // 解析上传成功的结果
        DefaultPutRet putRet = JSON.parseObject(response.bodyString(), DefaultPutRet.class);
        return getResAccessUrl(putRet.key);
    }
}
