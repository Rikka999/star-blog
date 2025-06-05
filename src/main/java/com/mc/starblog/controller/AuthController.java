package com.mc.starblog.controller;

import com.mc.starblog.dto.LoginDTO;
import com.mc.starblog.dto.RegisterDTO;
import com.mc.starblog.entity.User;
import com.mc.starblog.service.AuthService;
import com.mc.starblog.service.CustomUserDetails;
import com.mc.starblog.utils.JwtUtil;
import com.mc.starblog.utils.Result;
import com.mc.starblog.vo.LoginVO;
import com.mc.starblog.vo.RegisterVO;
import com.nimbusds.jose.JOSEException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "认证", description = "注册和登陆")
public class AuthController {

    private final AuthService authService;
    private final JwtUtil jwtUtil;

    @PostMapping("/register")
    @Operation(
            summary = "注册接口",
            description = "用户注册",
            responses = {
                    @ApiResponse(responseCode = "200", description = "注册成功"),
                    @ApiResponse(responseCode = "400", description = "用户名已存在")
            }
    )
    public Result<RegisterVO> userRegister(@RequestBody RegisterDTO registerDTO) {
        User user = authService.registerUser(registerDTO);
        return Result.success(RegisterVO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .nickname(user.getNickname())
                .build());
    }

    @PostMapping("/login")
    @Operation(
            summary = "登陆接口",
            description = "用户登陆",
            responses = {
                    @ApiResponse(responseCode = "200", description = "登陆成功"),
                    @ApiResponse(responseCode = "400", description = "账号或密码错误")
            }
    )
    public Result<LoginVO> userLogin(@RequestBody LoginDTO loginDTO) throws JOSEException {
        // 1. 调用服务层进行认证
        CustomUserDetails customUserDetails = authService.loginUser(loginDTO);
        // 2. 生成 JWT Token
        String token = jwtUtil.generateToken(customUserDetails);
        return Result.success(LoginVO.builder()
                .id(customUserDetails.getId())
                .token(token)
                .username(customUserDetails.getUsername())
                .nickname(customUserDetails.getNickname())
                .profilePictureUrl(customUserDetails.getProfilePictureUrl())
                .slogan(customUserDetails.getSlogan())
                .roles(customUserDetails.getAuthorities().stream()
                        .map(auth -> auth.getAuthority().replace("ROLE_", "")) // 去除ROLE_前缀
                        .toList())
                .build());
    }

}
