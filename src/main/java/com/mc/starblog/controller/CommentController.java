package com.mc.starblog.controller;

import com.mc.starblog.converter.CommentConverter;
import com.mc.starblog.dto.CommentDTO;
import com.mc.starblog.service.CommentService;
import com.mc.starblog.utils.Result;
import com.mc.starblog.vo.CommentVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public Result<CommentVO> createComment(@RequestBody CommentDTO commentDTO){
        return Result.success(CommentConverter.toVo(commentService.createComment(commentDTO)));
    }
}
