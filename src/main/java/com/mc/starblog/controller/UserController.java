package com.mc.starblog.controller;


import com.mc.starblog.converter.PostConverter;
import com.mc.starblog.converter.UserConverter;
import com.mc.starblog.dto.UserBaseInfoDTO;
import com.mc.starblog.entity.Post;
import com.mc.starblog.service.PostService;
import com.mc.starblog.service.UserService;
import com.mc.starblog.utils.PageInfo;
import com.mc.starblog.utils.Result;
import com.mc.starblog.vo.PostSimpleVO;
import com.mc.starblog.vo.UserBaseInfoVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Tag(name = "用户管理", description = "用户相关相关操作")
public class UserController {

    private final UserService userService;
    private final PostService postService;

    @GetMapping("/{id}")
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

    @PutMapping("/{id}")
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

    @DeleteMapping("/{id}")
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

    @GetMapping("/{id}/posts")
    @Operation(
            summary = "获取用户发布的文章列表摘要",
            description = "根据用户id获取用户发布的文章列表摘要",
            responses = {
                    @ApiResponse(responseCode = "200", description = "返回用户发布的文章"),
                    @ApiResponse(responseCode = "400", description = "用户不存在")
            }
    )
    public Result<PageInfo<PostSimpleVO>> getUserPostsSimple(
        @PathVariable Long id,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size
    ) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "updatedTime"));
        return Result.success(postService.findUserPostByUserId(id, pageRequest));
    }
}
