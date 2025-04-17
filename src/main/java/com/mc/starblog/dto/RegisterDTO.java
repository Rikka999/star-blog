package com.mc.starblog.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class RegisterDTO {

    @Schema(description = "用户名")
    private String username;

    @Schema(description = "用户密码")
    private String password;

    @Schema(description = "用户昵称")
    private String nickname;

    @Schema(description = "用户性别 1男 2女 3其他 4保密")
    private Integer sex;

    @Schema(description = "用户邮箱")
    private String email;

    @Schema(description = "用户手机号码")
    private String phoneNumber;

    @Schema(description = "用户头像url")
    private String profilePictureUrl;
}
