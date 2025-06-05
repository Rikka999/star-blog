package com.mc.starblog.controller;


import com.mc.starblog.converter.UserConverter;
import com.mc.starblog.dto.UserBaseInfoDTO;
import com.mc.starblog.dto.UserPasswordDTO;
import com.mc.starblog.service.UserService;
import com.mc.starblog.utils.Result;
import com.mc.starblog.vo.UserBaseInfoVO;
import com.nimbusds.jose.JOSEException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Tag(name = "用户管理", description = "用户相关相关操作")
public class UserController {

    private final UserService userService;

    @GetMapping("/users/{id}")
    @Operation(
            summary = "获取用户信息",
            description = "根据id获取用户详情",
            responses = {
                    @ApiResponse(responseCode = "200", description = "返回用户数据"),
                    @ApiResponse(responseCode = "400", description = "用户不存在")
            }
    )
    public Result<UserBaseInfoVO> getUserBaseInfoById(@PathVariable Long id) {
        return Result.success(UserConverter.toBaseInfoVo(userService.findById(id)));
    }

    @PutMapping("/users/{id}")
    @Operation(
            summary = "修改用户基本信息",
            description = "修改用户的基本信息",
            responses = {
                    @ApiResponse(responseCode = "200", description = "修改成功"),
                    @ApiResponse(responseCode = "400", description = "用户不存在")
            }
    )
    public Result<UserBaseInfoVO> updateUserBaseInfo(@PathVariable Long id,@RequestBody UserBaseInfoDTO userBaseInfoDTO) {
        return Result.success(UserConverter.toBaseInfoVo(userService.updateUserBaseInfo(id, userBaseInfoDTO)));
    }

    @PutMapping("/users/{id}/change_password")
    @Operation(
            summary = "修改用户密码",
            description = "修改用户密码",
            responses = {
                    @ApiResponse(responseCode = "200", description = "修改成功"),
                    @ApiResponse(responseCode = "400", description = "用户不存在")
            }
    )
    public Result<Void> updateUserPassword(@PathVariable Long id, @RequestBody UserPasswordDTO userPasswordDTO) throws JOSEException {
        userService.updateUserPassword(id, userPasswordDTO);
        return Result.success();
    }

    @DeleteMapping("/users/{id}")
    @Operation(
            summary = "删除用户",
            description = "根据id删除用户",
            responses = {
                    @ApiResponse(responseCode = "200", description = "删除成功"),
                    @ApiResponse(responseCode = "400", description = "用户不存在")
            }
    )
    public Result<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return Result.success();
    }

}
