package com.mc.starblog.service;

import com.mc.starblog.Repository.RoleRepository;
import com.mc.starblog.entity.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    public List<Role> findAll() {
        return roleRepository.findAll();
    }

}
