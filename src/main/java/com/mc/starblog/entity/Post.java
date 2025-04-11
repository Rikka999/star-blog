package com.mc.starblog.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Posts")
@Data
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
}
