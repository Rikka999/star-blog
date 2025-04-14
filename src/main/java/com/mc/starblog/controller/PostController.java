package com.mc.starblog.controller;

import com.mc.starblog.converter.PostConverter;
import com.mc.starblog.dto.PostBaseInfoDTO;
import com.mc.starblog.service.PostService;
import com.mc.starblog.utils.Result;
import com.mc.starblog.vo.PostBaseInfoVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
@Tag(name = "文章管理", description = "文章相关操作")
public class PostController {

    private final PostService postService;

    @Operation(
            summary = "获取文章详情",
            description = "根据id获取文章详情",
            responses = {
                    @ApiResponse(responseCode = "200", description = "返回帖子数据"),
                    @ApiResponse(responseCode = "400", description = "帖子不存在")
            }
    )
    @GetMapping("/{id}")
    public Result<PostBaseInfoVO> getPostById(@PathVariable Long id) {
        return Result.success(PostConverter.toVo(postService.findById(id)));
    }

    @Operation(
            summary = "创建文章",
            description = "创建文章",
            responses = {
                    @ApiResponse(responseCode = "200", description = "返回帖子数据"),
                    @ApiResponse(responseCode = "400", description = "创建失败")
            }
    )
    @PostMapping
    public Result<PostBaseInfoVO> createPost(@RequestBody PostBaseInfoDTO postBaseInfoDTO) {
        return Result.success(PostConverter.toVo(postService.createPost(postBaseInfoDTO)));
    }

    @Operation(
            summary = "修改文章",
            description = "修改文章",
            responses = {
                    @ApiResponse(responseCode = "200", description = "返回帖子数据"),
                    @ApiResponse(responseCode = "400", description = "修改失败")
            }
    )
    @PutMapping("/{id}")
    public Result<PostBaseInfoVO> updatePost(@PathVariable Long id, @RequestBody PostBaseInfoDTO postBaseInfoDTO) {
        return Result.success(PostConverter.toVo(postService.updatePost(id, postBaseInfoDTO)));
    }

    @Operation(
            summary = "删除文章",
            description = "删除文章",
            responses = {
                    @ApiResponse(responseCode = "200", description = "删除成功"),
                    @ApiResponse(responseCode = "400", description = "删除失败")
            }
    )
    @DeleteMapping("/{id}")
    public Result<Void> deletePost(@PathVariable Long id) {
        postService.deletePost(id);
        return Result.success();
    }
}
