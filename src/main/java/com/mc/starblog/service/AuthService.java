package com.mc.starblog.service;

import com.mc.starblog.Repository.RoleRepository;
import com.mc.starblog.Repository.UserRepository;
import com.mc.starblog.dto.LoginDTO;
import com.mc.starblog.dto.RegisterDTO;
import com.mc.starblog.entity.Role;
import com.mc.starblog.entity.User;
import com.mc.starblog.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    public User registerUser(RegisterDTO registerDTO) {
        if (userRepository.existsByUsername(registerDTO.getUsername())) {
            throw new BusinessException(400,"用户名已经存在");
        }
        Role role = roleRepository.findByRoleName("ROLE_USER")
                .orElseThrow(() -> new BusinessException(400,"角色列表为空"));
        User user = new User();
        user.setUsername(registerDTO.getUsername());
        user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
        user.setNickname(registerDTO.getNickname());
        user.getRoles().add(role);
        return userRepository.save(user);
    }

    public CustomUserDetails loginUser(LoginDTO request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    )
            );
            return (CustomUserDetails) authentication.getPrincipal();
        } catch (AuthenticationException e) {
            if (e instanceof BadCredentialsException) {
                throw new BusinessException(401, "用户名或密码错误");
            } else if (e instanceof LockedException) {
                throw new BusinessException(403, "账户已被锁定");
            } else {
                throw new BusinessException(401, "认证失败：" + e.getMessage());
            }
        }
    }

}
