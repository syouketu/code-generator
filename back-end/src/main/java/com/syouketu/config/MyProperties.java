package com.syouketu.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * MyProperties
 *
 * @author xiaojie
 * @date 2017/3/8
 */
@Data
@ConfigurationProperties(prefix = "jxi", ignoreUnknownFields = false)
public class MyProperties {

    /**
     * SWAGGER参数
     */
    private final Swagger swagger = new Swagger();

    /**
     * 上传文件相关参数
     */
    private final Upload upload = new Upload();

    /**
     * 对象存储
     */
    private final Oss oss = new Oss();

    /**
     * SWAGGER接口文档参数
     */
    @Data
    public static class Swagger {
        private String title;
        private String description;
        private String version;
        private String termsOfServiceUrl;
        private String contactName;
        private String contactUrl;
        private String contactEmail;
        private String license;
        private String licenseUrl;
    }

    @Data
    public static class Upload {
        private String staticDomain;
        private String uploadPath;
    }

    @Data
    public static class Oss {
        /**
         * accessKey
         */
        private String accessKey;
        /**
         * secretKey
         */
        private String secretKey;
        /**
         * bucket
         */
        private String bucketName;
        /**
         * 文件访问域名前缀
         */
        private String fileDomain;
        /**
         * 临时上传文件存放文件夹
         */
        private String tmpPath;
    }
}
