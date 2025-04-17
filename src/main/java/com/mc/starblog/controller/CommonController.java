package com.mc.starblog.controller;

import com.mc.starblog.service.OssService;
import com.mc.starblog.utils.Result;
import com.mc.starblog.vo.OssPolicyVO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/common")
@RequiredArgsConstructor
public class CommonController {

    private final OssService ossService;

    @GetMapping("/oss/upload-token")
    public Result<OssPolicyVO> getOssUploadToken(@RequestParam String type) {
        OssPolicyVO ossPolicyVO = ossService.generatePolicy(type);
        return Result.success(ossPolicyVO);
    }
}
