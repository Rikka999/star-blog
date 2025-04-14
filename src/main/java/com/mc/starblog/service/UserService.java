package com.mc.starblog.service;

import com.mc.starblog.Repository.UserRepository;
import com.mc.starblog.dto.UserBaseInfoDTO;
import com.mc.starblog.entity.Post;
import com.mc.starblog.entity.User;
import com.mc.starblog.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User findById(Long id) {
        return userRepository.findByIdWithRoles(id).orElseThrow(() -> new BusinessException(400,"没有此用户"));
    }

    public User updateUserBaseInfo(Long userId, UserBaseInfoDTO userBaseInfoDTO) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(400, "用户不存在"));
        user.setUsername(userBaseInfoDTO.getUsername());
        user.setSex(userBaseInfoDTO.getSex());
        user.setEmail(userBaseInfoDTO.getEmail());
        user.setPhoneNumber(userBaseInfoDTO.getPhoneNumber());
        user.setNickname(userBaseInfoDTO.getNickname());
        user.setSlogan(userBaseInfoDTO.getSlogan());
        user.setProfilePictureUrl(userBaseInfoDTO.getProfilePictureUrl());
        user.setHomepagePictureUrl(userBaseInfoDTO.getHomepagePictureUrl());
        return userRepository.save(user);
    }

    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(400, "用户不存在"));
        userRepository.delete(user);
    }

}
