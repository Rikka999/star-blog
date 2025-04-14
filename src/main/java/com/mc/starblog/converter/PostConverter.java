package com.mc.starblog.converter;

import com.mc.starblog.entity.Post;
import com.mc.starblog.vo.PostBaseInfoVO;
import com.mc.starblog.vo.PostSimpleVO;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

public class PostConverter {

    public static PostBaseInfoVO toBaseInfoVo(Post post) {
        PostBaseInfoVO postBaseInfoVO = new PostBaseInfoVO();
        BeanUtils.copyProperties(post, postBaseInfoVO);
        return postBaseInfoVO;
    }

    public static PostSimpleVO toSimpleVo(Post post) {
        PostSimpleVO postSimpleVO = new PostSimpleVO();
        BeanUtils.copyProperties(post, postSimpleVO);
        return postSimpleVO;
    }

    public static List<PostSimpleVO> postsToSimpleVOs(List<Post> posts) {
        return posts.stream()
                .map(PostConverter::toSimpleVo)
                .collect(Collectors.toList());
    }
}
