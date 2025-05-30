package com.mc.starblog.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class CommentDTO {

    @Schema(description = "所属用户Id")
    private Long userId;

    @Schema(description = "所属文章Id")
    private Long postId;

    @Schema(description = "父级评论Id")
    private Long parentId;

    @Schema(description = "评论内容")
    private String content;

    @Schema(description = "评论者昵称")
    private String nickname;

    @Schema(description = "评论者头像链接")
    private String profilePictureUrl;
}
