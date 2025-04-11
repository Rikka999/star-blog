package com.mc.starblog.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class LoginDTO {

    @Schema(description = "用户名")
    private String username;

    @Schema(description = "用户密码")
    private String password;
}
