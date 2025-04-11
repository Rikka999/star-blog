package com.mc.starblog.utils;

import com.mc.starblog.service.CustomUserDetails;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Date;
import java.util.stream.Collectors;

@Component
public class JwtUtil {

    @Value("${jwt.secret-key}")
    private String secretKey;
    @Value("${jwt.expiration-time}")
    private long expirationTime; // 24小时

    // 生成 JWT
    public String generateToken(CustomUserDetails customUserDetails) throws JOSEException {
        JWTClaimsSet claims = new JWTClaimsSet.Builder()
                .subject(customUserDetails.getUsername())
                .claim("id", customUserDetails.getId())  // 存储用户ID
                .claim("username", customUserDetails.getUsername())
                .claim("nickname", customUserDetails.getNickname())
                .claim("roles", customUserDetails.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toList()))
                .expirationTime(new Date(System.currentTimeMillis() + expirationTime)) // 使用 expirationTime
                .issueTime(new Date())  //发行时间
                .build();
        // 使用 HMAC 签名（传入密钥）
        JWSSigner signer = new MACSigner(secretKey); // 传入 secretKey
        SignedJWT signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256), claims);
        signedJWT.sign(signer);

        return signedJWT.serialize();
    }

    // 验证 JWT
    public boolean validateToken(String token, CustomUserDetails customUserDetails) throws JOSEException, ParseException {
        SignedJWT signedJWT = SignedJWT.parse(token);
        JWSVerifier verifier = new MACVerifier(secretKey);
        boolean isValid = signedJWT.verify(verifier);
        boolean isExpired = signedJWT.getJWTClaimsSet().getExpirationTime().before(new Date());
        // 校验 Token 中的用户 ID 和用户名
        String tokenUsername = signedJWT.getJWTClaimsSet().getSubject();
        Long tokenId = (Long) signedJWT.getJWTClaimsSet().getClaim("id");
        return isValid && !isExpired && tokenUsername.equals(customUserDetails.getUsername()) && tokenId.equals(customUserDetails.getId());
    }

    // 从 Token 中提取用户名
    public String extractUsername(String token) throws ParseException {
        SignedJWT signedJWT = SignedJWT.parse(token);
        return signedJWT.getJWTClaimsSet().getSubject();
    }
}
