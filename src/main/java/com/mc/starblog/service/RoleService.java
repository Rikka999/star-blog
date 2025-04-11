package com.mc.starblog.service;

import com.mc.starblog.Repository.RoleRepository;
import com.mc.starblog.entity.Role;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    public List<Role> findAll() {
        return roleRepository.findAll();
    }

}
