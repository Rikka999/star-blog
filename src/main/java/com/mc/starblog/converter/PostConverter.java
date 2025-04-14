package com.mc.starblog.converter;

import com.mc.starblog.entity.Post;
import com.mc.starblog.vo.PostBaseInfoVO;
import org.springframework.beans.BeanUtils;

public class PostConverter {

    public static PostBaseInfoVO toVo(Post post) {
        PostBaseInfoVO postBaseInfoVO = new PostBaseInfoVO();
        BeanUtils.copyProperties(post, postBaseInfoVO);
        return postBaseInfoVO;
    }
}
