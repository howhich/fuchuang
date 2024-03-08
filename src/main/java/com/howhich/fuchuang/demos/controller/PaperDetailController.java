package com.howhich.fuchuang.demos.controller;

import com.howhich.fuchuang.demos.constant.Result;
import com.howhich.fuchuang.demos.entity.req.GetPaperDetailReqVO;
import com.howhich.fuchuang.demos.entity.resp.GetPaperDetailRespVO;
import com.howhich.fuchuang.demos.entity.resp.GetPaperVisualizationRespVO;
import com.howhich.fuchuang.demos.entity.resp.GetTotalJudgeRespVO;
import com.howhich.fuchuang.demos.service.PaperDetailService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/paperDetail")
@Api(tags = "试卷detail")
public class PaperDetailController {
    @Autowired
    private PaperDetailService paperDetailService;
//    @ApiOperation(value = "通过id和学号获取整体评阅")
//    @GetMapping("/getTotalPaperDetailById")
//    public Result<GetTotalJudgeRespVO> getTotalPaperDetailById(@RequestParam Long id) {
//        return paperDetailService.getTotalPaperDetailById(id);
//    }
    @ApiOperation(value = "通过组ID获取整体评阅")
    @PostMapping("/getPaperTotal")
    public Result<List<String>> getPaperTotal(@RequestParam Long groupId) {
        return paperDetailService.getPaperTotal(groupId);
    }
    @ApiOperation(value = "通过组ID获取详细评阅")
    @PostMapping("/getPaperDetail")
    public Result<List<GetPaperDetailRespVO>> getPaperDetail(@RequestParam Long groupId) {
        return paperDetailService.getPaperDetail(groupId);
    }
    @ApiOperation(value = "通过组ID获取可视化分析")
    @PostMapping("/getPaperVisualization")
    public Result<GetPaperVisualizationRespVO> getPaperVisualization(@RequestParam Long groupId) {
        return paperDetailService.getPaperVisualization(groupId);
    }
}
