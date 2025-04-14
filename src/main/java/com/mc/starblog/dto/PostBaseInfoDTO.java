package com.mc.starblog.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PostBaseInfoDTO {

    @Schema(description = "ID")
    private Long id;

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

    @Schema(description = "是否置顶")
    private Boolean isTop;

    @Schema(description = "是否精选")
    private Boolean isFeatured;

    @Schema(description = "创建时间")
    private LocalDateTime createdTime;

    @Schema(description = "更新时间")
    private LocalDateTime updatedTime;

    @Schema(description = "评论数")
    private Integer commentCount = 0;

    @Schema(description = "浏览数")
    private Integer viewCount = 0;

    @Schema(description = "点赞数")
    private Integer likeCount = 0;
}
