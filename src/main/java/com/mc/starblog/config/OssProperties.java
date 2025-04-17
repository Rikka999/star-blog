package com.mc.starblog.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Data
@Configuration
@ConfigurationProperties(prefix = "aliyun.oss")
public class OssProperties {

    private String accessKeyId;
    private String accessKeySecret;
    private String bucketName;
    private String endpoint;
    private Long expireSeconds = 60L; // 签名有效期（秒）
    private Long maxSize = 10L * 1024 * 1024; // 最大上传大小 10MB

    private Map<String, String> dirs;

    public String getUploadDir(String type) {
        return dirs.get(type);
    }

}
