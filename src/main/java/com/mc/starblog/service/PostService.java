package com.mc.starblog.service;


import com.mc.starblog.Repository.PostRepository;
import com.mc.starblog.Repository.UserRepository;
import com.mc.starblog.converter.PostConverter;
import com.mc.starblog.dto.PostBaseInfoDTO;
import com.mc.starblog.entity.Post;
import com.mc.starblog.entity.User;
import com.mc.starblog.exception.BusinessException;
import com.mc.starblog.utils.HtmlUtil;
import com.mc.starblog.utils.PageInfo;
import com.mc.starblog.utils.SummaryUtil;
import com.mc.starblog.vo.PostSimpleVO;
import com.mc.starblog.vo.UserSimpleVO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
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
        post.setUpdatedTime(LocalDateTime.now());
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
        if(!post.getTitle().equals(postBaseInfoDTO.getTitle()) || !post.getContent().equals(postBaseInfoDTO.getContent())){
            post.setUpdatedTime(LocalDateTime.now());
        }else {
            post.setUpdatedTime(post.getUpdatedTime());
        }
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

    public PageInfo<PostSimpleVO> findAllByPage(PageRequest pageRequest) {
        Page<Post> postPage = postRepository.findAll(pageRequest);
        List<PostSimpleVO> postsToSimpleVOs = PostConverter.postsToSimpleVOs(postPage.getContent());
        return new PageInfo<>(postsToSimpleVOs, postPage.getNumber(), postPage.getSize(), postPage.getTotalPages(), postPage.getTotalElements(), postPage.isLast());
    }

    public PageInfo<PostSimpleVO> searchPost(String keyword, PageRequest pageRequest){

        Page<Post> postPage;
        if (keyword == null || keyword.trim().isEmpty()) {
            postPage = postRepository.findAll(pageRequest);

        }else {
            postPage = postRepository.searchPosts(keyword, pageRequest);
        }
        List<PostSimpleVO> postVOs = PostConverter.postsToSimpleVOs(postPage.getContent());
        return new PageInfo<>(postVOs, postPage.getNumber(), postPage.getSize(),
                postPage.getTotalPages(), postPage.getTotalElements(), postPage.isLast());
    }

    public PageInfo<PostSimpleVO> findUserPostByUserId(Long userId, PageRequest pageRequest) {
        Page<Post> postPage = postRepository.findByUserIdAndStatus(userId, 1, pageRequest);
        List<PostSimpleVO> postsToSimpleVOs = PostConverter.postsToSimpleVOs(postPage.getContent());
        return new PageInfo<>(postsToSimpleVOs, postPage.getNumber(), postPage.getSize(), postPage.getTotalPages(), postPage.getTotalElements(), postPage.isLast());
    }

    public void increasePostViews(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new BusinessException(400, "帖子不存在！"));
        post.setViewCount(post.getViewCount() + 1);
        postRepository.save(post);
    }
}
