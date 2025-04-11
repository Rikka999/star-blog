package com.mc.starblog.dto;

import com.mc.starblog.entity.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
public class UserRolesDTO {

    @Schema(description = "用户id")
    private Integer id;

    @Schema(description = "角色列表")
    private List<Role> roles;
}
