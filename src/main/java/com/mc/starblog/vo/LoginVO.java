package com.mc.starblog.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class LoginVO {

    @Schema(description = "用户id")
    private Long id;

    @Schema(description = "token")
    private String token;

    @Schema(description = "用户名")
    private String username;

    @Schema(description = "用户昵称")
    private String nickname;

    @Schema(description = "用户角色列表")
    private List<String> roles;
}
