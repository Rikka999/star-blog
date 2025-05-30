package com.mc.starblog.controller;

import com.mc.starblog.converter.CommentConverter;
import com.mc.starblog.dto.CommentDTO;
import com.mc.starblog.service.CommentService;
import com.mc.starblog.utils.PageInfo;
import com.mc.starblog.utils.Result;
import com.mc.starblog.vo.CommentVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Tag(name = "评论", description = "评论相关操作")
public class CommentController {

    private final CommentService commentService;

    @Operation(
            summary = "创建评论",
            description = "创建评论",
            responses = {
                    @ApiResponse(responseCode = "200", description = "返回评论数据"),
                    @ApiResponse(responseCode = "400", description = "创建失败")
            }
    )
    @PostMapping("/posts/{postId}/comments")
    public Result<CommentVO> createComment(@PathVariable Long postId, @RequestBody CommentDTO commentDTO){
        commentDTO.setPostId(postId);
        return Result.success(CommentConverter.toVo(commentService.createComment(commentDTO)));
    }

    @Operation(
            summary = "获取评论列表树",
            description = "通过postid获取评论列表",
            responses = {
                    @ApiResponse(responseCode = "200", description = "返回评论列表"),
                    @ApiResponse(responseCode = "400", description = "获取失败")
            }
    )
    @GetMapping("/posts/{postId}/comments")
    public Result<PageInfo<CommentVO>> getComments(@PathVariable Long postId, @RequestParam(defaultValue = "0") Integer page,
                                                   @RequestParam(defaultValue = "10") Integer pageSize){
        PageRequest pageRequest = PageRequest.of(page, pageSize, Sort.by(Sort.Direction.DESC, "createdTime"));
        return Result.success(commentService.getCommentsByPostId(postId, pageRequest));
    }
}
