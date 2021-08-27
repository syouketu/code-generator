package com.syouketu.modules.common.service;

import java.io.File;

/**
 * 七牛云文件服务
 *
 * @author: JXI
 * @description:
 * @date: Created on 11/18/2020
 */
public interface IFileService {

    /**
     * 上传图片
     *
     * @param key
     * @param localFile
     * @return
     */
    String uploadFile(String key, File localFile) throws Exception;
}
