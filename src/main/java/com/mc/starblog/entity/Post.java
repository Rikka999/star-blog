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
@Table(
        name = "posts",
        indexes = {
                @Index(name = "idx_post_title", columnList = "title")
        }
)
@Data
@SQLDelete(sql = "UPDATE posts SET is_deleted = true, deleted_time = CURRENT_TIMESTAMP WHERE id = ?")
@Where(clause = "is_deleted = false")
@ToString(exclude = "user")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "BIGINT COMMENT '主键ID'")
    private Long id;

    @Column(name = "user_id", nullable = false, columnDefinition = "BIGINT COMMENT '发布者用户ID'")
    private Long userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    @JsonIgnore
    private User user;

    @Column(name = "title", nullable = false, length = 255, columnDefinition = "VARCHAR(255) COMMENT '标题'")
    private String title;

    @Lob
    @Column(name = "content", columnDefinition = "TEXT COMMENT '正文内容'", nullable = false)
    private String content;

    @Column(name = "cover_image", length = 500, columnDefinition = "VARCHAR(500) COMMENT '封面图片链接'")
    private String coverImage;

    @Column(name = "summary", length = 500, columnDefinition = "VARCHAR(500) COMMENT '摘要'")
    private String summary;

    @Column(name = "tags", length = 255, columnDefinition = "VARCHAR(255) COMMENT '标签，用逗号分隔'")
    private String tags;

    @Column(name = "category", length = 100, columnDefinition = "VARCHAR(100) COMMENT '分类'")
    private String category;

    @Column(name = "status", columnDefinition = "TINYINT DEFAULT 1 COMMENT '状态：0-草稿，1-已发布'")
    private Integer status = 1;

    @Column(name = "view_count", columnDefinition = "INT DEFAULT 0 COMMENT '浏览数'")
    private Integer viewCount = 0;

    @Column(name = "like_count", nullable = false, columnDefinition = "INT DEFAULT 0 COMMENT '点赞数'")
    private Integer likeCount = 0;

    @Column(name = "allow_comment", columnDefinition = "BOOLEAN DEFAULT TRUE COMMENT '是否允许评论'")
    private Boolean allowComment = true;

    @Column(name = "comment_count", columnDefinition = "INT DEFAULT 0 COMMENT '评论数'")
    private Integer commentCount = 0;

    @Column(name = "is_top", columnDefinition = "BOOLEAN DEFAULT FALSE COMMENT '是否置顶'")
    private Boolean isTop = false;

    @Column(name = "is_featured", columnDefinition = "BOOLEAN DEFAULT FALSE COMMENT '是否精选/推荐'")
    private Boolean isFeatured = false;

    @Column(name = "visibility", columnDefinition = "TINYINT DEFAULT 0 COMMENT '可见性：0-公开，1-私密，2-仅粉丝'")
    private Integer visibility = 0;

    @CreationTimestamp
    @Column(name = "created_time", updatable = false, columnDefinition = "DATETIME COMMENT '创建时间'")
    private LocalDateTime createdTime;

//    @UpdateTimestamp
    @Column(name = "updated_time", columnDefinition = "DATETIME COMMENT '更新时间'")
    private LocalDateTime updatedTime;

    @Column(name = "deleted_time")
    private LocalDateTime deletedTime;

    @Column(name = "is_deleted", columnDefinition = "BOOLEAN DEFAULT FALSE COMMENT '逻辑删除标记'")
    private Boolean isDeleted = false;
}
