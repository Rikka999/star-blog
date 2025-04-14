package com.mc.starblog.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Data
@SQLDelete(sql = "UPDATE users SET is_deleted = true, deleted_time = CURRENT_TIMESTAMP WHERE id = ?")
@Where(clause = "is_deleted = false")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", length = 50, nullable = false, columnDefinition = "VARCHAR(50) COMMENT '用户名'")
    private String username;

    @Column(name = "password", length = 60, nullable = false, columnDefinition = "VARCHAR(60) COMMENT '密码'")
    @JsonIgnore
    private String password;

    @Column(name = "sex", columnDefinition = "TINYINT DEFAULT 4 COMMENT '性别：1男 2女 3其他 4保密'")
    private Integer sex;

    @Column(name = "email", length = 50, unique = true, columnDefinition = "VARCHAR(50) COMMENT '邮箱'")
    private String email;

    @Column(name = "phone_number", length = 50, columnDefinition = "VARCHAR(50) COMMENT '手机号码'")
    private String phoneNumber;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private List<Role> roles = new ArrayList<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Post> posts = new ArrayList<>();

    @Column(name = "nickname", length = 100, columnDefinition = "VARCHAR(100) COMMENT '昵称'")
    private String nickname;

    @Column(name = "slogan", length = 100, columnDefinition = "VARCHAR(100) COMMENT '签名'")
    private String slogan;

    @Column(name = "profile_picture_url", length = 2083, columnDefinition = "VARCHAR(2083) COMMENT '头像url'")
    private String profilePictureUrl;

    @Column(name = "homepage_picture_url", length = 2083, columnDefinition = "VARCHAR(2083) COMMENT '主页背景url'")
    private String homepagePictureUrl;

    @CreationTimestamp
    @Column(name = "created_time", updatable = false, columnDefinition = "DATETIME COMMENT '创建时间'")
    private LocalDateTime createdTime;

    @UpdateTimestamp
    @Column(name = "updated_time", columnDefinition = "DATETIME COMMENT '更新时间'")
    private LocalDateTime updatedTime;

    @Column(name = "deleted_time", columnDefinition = "DATETIME COMMENT '删除时间'")
    private LocalDateTime deletedTime;

    @Column(name = "is_deleted", nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE COMMENT '逻辑删除标记'")
    private Boolean isDeleted = false;

}
