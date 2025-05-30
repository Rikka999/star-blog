package com.mc.starblog.service;

import com.mc.starblog.Repository.CommentRepository;
import com.mc.starblog.converter.CommentConverter;
import com.mc.starblog.dto.CommentDTO;
import com.mc.starblog.entity.Comment;
import com.mc.starblog.utils.PageInfo;
import com.mc.starblog.vo.CommentVO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    public Comment createComment(CommentDTO commentDTO){
        Comment comment = new Comment();
        comment.setUserId(commentDTO.getUserId());
        comment.setPostId(commentDTO.getPostId());
        comment.setParentId(commentDTO.getParentId());
        comment.setContent(commentDTO.getContent());
        comment.setNickname(commentDTO.getNickname());
        comment.setProfilePictureUrl(commentDTO.getProfilePictureUrl());
        return commentRepository.save(comment);
    }

    public PageInfo<CommentVO> getCommentsByPostId( Long postId, PageRequest pageRequest) {
        List<Comment> allComments = commentRepository.findByPostId(postId);
        // 拿到所有评论，构建 Map<id, Comment>
        Map<Long, Comment> commentMap = allComments.stream().collect(Collectors.toMap(Comment::getId, c -> c));
        // 转换为 VO
        Map<Long, CommentVO> voMap = allComments.stream()
                .map(CommentConverter::toVo)
                .collect(Collectors.toMap(CommentVO::getId, vo -> vo));
        // 构建两层结构
        List<CommentVO> roots = new ArrayList<>();

        for (CommentVO vo : voMap.values()) {
            if (vo.getParentId() == null) {
                vo.setChildren(new ArrayList<>());
                roots.add(vo);
            } else {
                // 找父评论并添加 children
                Comment parentComment = commentMap.get(vo.getParentId());
                if (parentComment != null) {
                    String parentNickname = parentComment.getNickname();
                    vo.setReplyToNickname(parentNickname);

                    CommentVO parentVo = voMap.get(vo.getParentId());
                    if (parentVo != null) {
                        if (parentVo.getChildren() == null) parentVo.setChildren(new ArrayList<>());
                        parentVo.getChildren().add(vo);
                        parentVo.getChildren().sort(Comparator.comparing(CommentVO::getCreatedTime));
                    }
                }
            }
        }
        // 分页
        int start = pageRequest.getPageNumber() * pageRequest.getPageSize();
        int end = Math.min(start + pageRequest.getPageSize(), roots.size());
        List<CommentVO> pageContent = start >= roots.size() ? List.of() : roots.subList(start, end);

        return new PageInfo<>(
                pageContent,
                pageRequest.getPageNumber(),
                pageRequest.getPageSize(),
                (roots.size() + pageRequest.getPageSize() - 1) / pageRequest.getPageSize(),
                roots.size(),
                end >= roots.size()
        );
    }

}
