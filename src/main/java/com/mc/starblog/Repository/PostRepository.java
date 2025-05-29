package com.mc.starblog.Repository;

import com.mc.starblog.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PostRepository extends JpaRepository<Post, Long> {
    Page<Post> findByUserIdAndStatus(Long userId, Integer status, PageRequest pageRequest);

    @Query("SELECT p FROM Post p WHERE p.title like concat('%',:keyword,'%') or p.content like concat('%',:keyword,'%')")
    Page<Post> searchPosts(@Param("keyword") String keyword, PageRequest pageRequest);

}
