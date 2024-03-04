package com.howhich.fuchuang.demos.controller;

import com.howhich.fuchuang.demos.constant.Result;
import com.howhich.fuchuang.demos.entity.resp.GetTotalJudgeRespVO;
import com.howhich.fuchuang.demos.service.PaperDetailService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/paperDetail")
@Api(tags = "试卷detail")
public class PaperDetailController {
    @Autowired
    private PaperDetailService paperDetailService;
    @ApiOperation(value = "通过id获取整体评阅")
    @GetMapping("/getTotalPaperDetailById")
    public Result<GetTotalJudgeRespVO> getTotalPaperDetailById(@RequestParam Long id) {
        return paperDetailService.getTotalPaperDetailById(id);
    }
}
