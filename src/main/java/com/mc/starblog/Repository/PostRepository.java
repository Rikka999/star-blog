package com.mc.starblog.Repository;

import com.mc.starblog.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
    Page<Post> findByUserIdAndStatus(Long userId, Integer status, PageRequest pageRequest);
}
