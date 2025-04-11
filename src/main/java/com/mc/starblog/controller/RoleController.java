package com.mc.starblog.controller;

import com.mc.starblog.entity.Role;
import com.mc.starblog.service.RoleService;
import com.mc.starblog.utils.Result;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class RoleController {

    private final RoleService roleService;

    @GetMapping("/roles")
    public Result<List<Role>> getRoles() {
        return Result.success(roleService.findAll());
    }

}
