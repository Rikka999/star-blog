package com.mc.starblog.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
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

    @Column(name = "username", length = 50, nullable = false)
    private String username;

    @Column(name = "password", length = 60, nullable = false)
    @JsonIgnore
    private String password;

    @Column(name = "sex")
    private Integer sex;

    @Column(name = "email", length = 50, unique = true)
    private String email;

    @Column(name = "phone_number", length = 50)
    private String phoneNumber;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_roles",                          // 中间表的名称
            joinColumns = @JoinColumn(name = "user_id"),  // 外键列：指向用户表
            inverseJoinColumns = @JoinColumn(name = "role_id") // 外键列：指向角色表
    )
    private List<Role> roles = new ArrayList<>();

    @Column(name = "nickname", length = 100)
    private String nickname;

    @Column(name = "slogan", length = 100)
    private String slogan;

    @Column(name = "profile_picture_url", length = 2083)
    private String profilePictureUrl;

    @Column(name = "homepage_picture_url", length = 2083)
    private String homepagePictureUrl;

    @CreationTimestamp
    @Column(name = "created_time", updatable = false)
    private LocalDateTime createdTime;

    @UpdateTimestamp
    @Column(name = "updated_time")
    private LocalDateTime updatedTime;

    @Column(name = "deleted_time")
    private LocalDateTime deletedTime;

    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted = false;

    @Override
    public int hashCode() {
        return (id != null) ? id.hashCode() : 0;
    }
}
