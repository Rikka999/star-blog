package com.mc.starblog.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.time.LocalDateTime;

@Entity
@Table(name = "comments", indexes = {
        @Index(name = "idx_comment_post", columnList = "post_id"),
        @Index(name = "idx_comment_user", columnList = "user_id"),
        @Index(name = "idx_comment_parent", columnList = "parent_id"),
        @Index(name = "idx_comment_created", columnList = "created_time"),
        @Index(name = "idx_comment_post_parent_time", columnList = "post_id, parent_id, created_time")
})

@Data
@SQLDelete(sql = "UPDATE comments SET is_deleted = true, deleted_time = CURRENT_TIMESTAMP WHERE id = ?")
@Where(clause = "is_deleted = false")
@ToString
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "BIGINT COMMENT '主键ID'")
    private Long id;

    @Column(name = "user_id", nullable = false, columnDefinition = "BIGINT COMMENT '发布者用户ID'")
    private Long userId;

    @Column(name = "post_id", nullable = false, columnDefinition = "BIGINT COMMENT '所属文章ID'")
    private Long postId;

    @Column(name = "parent_id", columnDefinition = "BIGINT COMMENT '父级评论ID'")
    private Long parentId;

    @Column(name = "content", nullable = false, columnDefinition = "TEXT COMMENT '评论内容'")
    private String content;

    @Column(name = "like_count", nullable = false, columnDefinition = "INT DEFAULT 0 COMMENT '点赞数'")
    private Integer likeCount = 0;

    @Column(name = "nickname", length = 100, columnDefinition = "VARCHAR(100) COMMENT '评论者昵称'")
    private String nickname;

    @Column(name = "profile_picture_url", length = 2083, columnDefinition = "VARCHAR(2083) COMMENT '头像url'")
    private String profilePictureUrl;

    @CreationTimestamp
    @Column(name = "created_time", updatable = false, columnDefinition = "DATETIME COMMENT '创建时间'")
    private LocalDateTime createdTime;

    @Column(name = "deleted_time", columnDefinition = "DATETIME COMMENT '删除时间'")
    private LocalDateTime deletedTime;

    @Column(name = "is_deleted", nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE COMMENT '逻辑删除标记'")
    private Boolean isDeleted = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    @JsonIgnore
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", insertable = false, updatable = false)
    @JsonIgnore
    private Post post;

}
