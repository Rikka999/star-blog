package com.mc.starblog.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class CommentVO {
    @Schema(description = "评论id")
    private Long id;

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

    @Schema(description = "创建时间")
    private LocalDateTime createdTime;

    @Schema(description = "父评论者昵称，仅用于子评论")
    private String replyToNickname;

    @Schema(description = "子评论列表，仅用于一级评论")
    private List<CommentVO> children;

}
