package com.mc.starblog.converter;

import com.mc.starblog.entity.User;
import com.mc.starblog.vo.UserBaseInfoVO;
import com.mc.starblog.vo.UserSimpleVO;
import org.springframework.beans.BeanUtils;

public class UserConverter {

    public static UserBaseInfoVO toBaseInfoVo(User user) {
        UserBaseInfoVO userBaseInfoVO = new UserBaseInfoVO();
        BeanUtils.copyProperties(user, userBaseInfoVO);
        userBaseInfoVO.setMaskedPhoneNumber(maskPhone(user.getPhoneNumber()));
        return userBaseInfoVO;
    }

    private static String maskPhone(String phone) {
        if(phone == null || phone.length() != 11){
            return phone;
        }
        return phone.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
    }

    public static UserSimpleVO toSimpleVo(User user) {
        UserSimpleVO userSimpleVO = new UserSimpleVO();
        BeanUtils.copyProperties(user, userSimpleVO);
        return userSimpleVO;
    }
}
