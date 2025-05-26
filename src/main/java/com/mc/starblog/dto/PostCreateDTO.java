package com.mc.starblog.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

public class PostCreateDTO {

    @Schema(description = "所属用户Id")
    private Long userId;

    @Schema(description = "标题")
    private String title;

    @Schema(description = "正文")
    private String content;

    @Schema(description = "封面图片URL")
    private String coverImage;

    @Schema(description = "摘要")
    private String summary;

    @Schema(description = "状态 0草稿 1已发布")
    private Integer status;

    @Schema(description = "是否允许评论")
    private Boolean allowComment;

    @Schema(description = "可见性 0公开 1私密 2仅粉丝")
    private Integer visibility;

}
