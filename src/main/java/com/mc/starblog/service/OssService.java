package com.mc.starblog.service;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.MatchMode;
import com.aliyun.oss.model.PolicyConditions;
import com.mc.starblog.config.OssProperties;
import com.mc.starblog.exception.BusinessException;
import com.mc.starblog.vo.OssPolicyVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class OssService {

    private final OssProperties ossProperties;

    public OssPolicyVO generatePolicy(String type) {
        String endpoint = ossProperties.getEndpoint();
        String accessId = ossProperties.getAccessKeyId();
        String accessKey = ossProperties.getAccessKeySecret();
        String bucket = ossProperties.getBucketName();
        String dir = ossProperties.getUploadDir(type);

        long expireTime = ossProperties.getExpireSeconds() * 1000;
        long maxSize = ossProperties.getMaxSize();

        String host = "https://" + bucket + "." + endpoint;

        try {
            Date expiration = new Date(System.currentTimeMillis() + expireTime);
            PolicyConditions conditions = new PolicyConditions();
            conditions.addConditionItem(PolicyConditions.COND_CONTENT_LENGTH_RANGE, 0, maxSize);
            conditions.addConditionItem(MatchMode.StartWith, PolicyConditions.COND_KEY, dir);

            OSS ossClient = new OSSClientBuilder().build(endpoint, accessId, accessKey);

            String policy = ossClient.generatePostPolicy(expiration, conditions);
            String encodedPolicy = Base64.getEncoder().encodeToString(policy.getBytes(StandardCharsets.UTF_8));
            String signature = ossClient.calculatePostSignature(policy);

            ossClient.shutdown();

            return new OssPolicyVO(
                    accessId,
                    encodedPolicy,
                    signature,
                    dir,
                    host,
                    String.valueOf(expiration.getTime() / 1000)
            );

        } catch (Exception e) {
            throw new BusinessException(400,"获取OSS签名失败");
        }
    }
}
