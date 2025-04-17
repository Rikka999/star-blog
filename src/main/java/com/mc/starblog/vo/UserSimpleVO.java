package com.mc.starblog.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class UserSimpleVO {

    @Schema(description = "用户ID")
    private Long id;

    @Schema(description = "用户名")
    private String nickname;

    @Schema(description = "用户头像")
    private String profilePictureUrl;
}
