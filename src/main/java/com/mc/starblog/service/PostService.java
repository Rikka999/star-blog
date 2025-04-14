package com.mc.starblog.service;


import com.mc.starblog.Repository.PostRepository;
import com.mc.starblog.dto.PostBaseInfoDTO;
import com.mc.starblog.entity.Post;
import com.mc.starblog.exception.BusinessException;
import com.mc.starblog.utils.SummaryUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    public Post createPost(PostBaseInfoDTO postBaseInfoDTO) {
        Post post = new Post();
        post.setUserId(postBaseInfoDTO.getUserId());
        post.setTitle(postBaseInfoDTO.getTitle());
        post.setContent(postBaseInfoDTO.getContent());
        post.setCoverImage(postBaseInfoDTO.getCoverImage());
        post.setSummary(SummaryUtil.generateSummary(postBaseInfoDTO.getSummary(),postBaseInfoDTO.getContent(),100));
        post.setStatus(postBaseInfoDTO.getStatus());
        post.setAllowComment(postBaseInfoDTO.getAllowComment());
        post.setVisibility(postBaseInfoDTO.getVisibility());
        return postRepository.save(post);
    }

    public void deletePost(Long id) {
         Post post = postRepository.findById(id)
                         .orElseThrow(() -> new BusinessException(400, "帖子不存在！"));
        postRepository.delete(post);
    }

    public Post updatePost(Long id, PostBaseInfoDTO postBaseInfoDTO) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new BusinessException(400, "帖子不存在！"));
        post.setTitle(postBaseInfoDTO.getTitle());
        post.setContent(postBaseInfoDTO.getContent());
        post.setCoverImage(postBaseInfoDTO.getCoverImage());
        post.setSummary(SummaryUtil.generateSummary(postBaseInfoDTO.getSummary(),postBaseInfoDTO.getContent(),100));
        post.setStatus(postBaseInfoDTO.getStatus());
        post.setAllowComment(postBaseInfoDTO.getAllowComment());
        post.setVisibility(postBaseInfoDTO.getVisibility());
        return postRepository.save(post);
    }

    public Post findById(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new BusinessException(400, "帖子不存在！"));
    }



    public Post findByUserId(Long userId) {
        return postRepository.findByUserId(userId);
    }
}
