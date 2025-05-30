package com.mc.starblog.converter;

import com.mc.starblog.entity.Comment;
import com.mc.starblog.vo.CommentVO;
import org.springframework.beans.BeanUtils;

public class CommentConverter {

    public static CommentVO toVo(Comment comment) {
        CommentVO commentVO = new CommentVO();
        BeanUtils.copyProperties(comment, commentVO);
        return commentVO;
     }
}
