package com.mc.starblog.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class UserPasswordDTO {

    @Schema(description = "用户id")
    private Long id;

    @Schema(description = "用户密码")
    private String password;

}
