package com.mc.starblog.service;

import com.mc.starblog.Repository.CommentRepository;
import com.mc.starblog.dto.CommentDTO;
import com.mc.starblog.entity.Comment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

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

}
