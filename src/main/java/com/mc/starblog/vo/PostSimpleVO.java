package com.mc.starblog.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PostSimpleVO {

    @Schema(description = "文章ID")
    private Long id;

    @Schema(description = "文章标题")
    private String title;

    @Schema(description = "摘要")
    private String summary;

    @Schema(description = "封面图URL")
    private String coverImage;

    @Schema(description = "更新时间")
    private LocalDateTime updatedTime;

    @Schema(description = "点赞数")
    private Integer likeCount;

}
