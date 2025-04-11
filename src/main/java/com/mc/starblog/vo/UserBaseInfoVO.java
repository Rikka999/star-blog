package com.mc.starblog.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class UserBaseInfoVO {

    @Schema(description = "用户id")
    private Long id;

    @Schema(description = "用户名")
    private String username;

    @Schema(description = "用户性别 1男 2女 3其他 4保密")
    private Integer sex;

    @Schema(description = "用户邮箱")
    private String email;

    @Schema(description = "用户手机号码")
    private String maskedPhoneNumber;

    @Schema(description = "用户昵称")
    private String nickname;

    @Schema(description = "用户个人签名")
    private String slogan;

    @Schema(description = "用户头像url")
    private String profilePictureUrl;

    @Schema(description = "用户个人主页背景url")
    private String homepagePictureUrl;
}
