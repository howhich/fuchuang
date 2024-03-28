package com.howhich.fuchuang.demos.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckRole;
import com.howhich.fuchuang.demos.constant.Result;
import com.howhich.fuchuang.demos.constant.RoleType;
import com.howhich.fuchuang.demos.entity.resp.*;
import com.howhich.fuchuang.demos.service.PaperDetailService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/paperDetail")
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
    @GetMapping("/getPaperTotal")
    @SaCheckLogin
    public Result<GetTotalJudgeRespVO> getPaperTotal(@RequestParam Long groupId) {
        return paperDetailService.getPaperTotal(groupId);
    }
    @ApiOperation(value = "通过组ID获取详细评阅")
    @GetMapping("/getPaperDetail")
    @SaCheckLogin
    public Result<List<GetPaperDetailRespVO>> getPaperDetail(@RequestParam Long groupId) {
        return paperDetailService.getPaperDetail(groupId);
    }
    @ApiOperation(value = "通过组ID获取可视化分析")
    @SaCheckLogin
    @GetMapping("/getPaperVisualization")
    public Result<GetPaperVisualizationRespVO> getPaperVisualization(@RequestParam Long groupId) {
        return paperDetailService.getPaperVisualization(groupId);
    }
    @ApiOperation(value = "老师更改详细评阅")
    @PostMapping("/updatePaperDetail")
    @SaCheckRole(value = RoleType.TEACHER.code)
    public Result updatePaperDetail(@RequestBody UpdatePaperDetailReqVO reqVO){
        return paperDetailService.updatePaperDetail(reqVO);
    }
    @ApiOperation(value = "学生导出试卷详情")
    @PostMapping("/exportPaperDetail")
    @SaCheckLogin
    public Result<String> exportPaperDetail(@RequestParam Long groupId){
        return paperDetailService.exportPaperDetail(groupId);
    }
    @ApiOperation(value = "通过组ID获取得分情况")
    @GetMapping("/getScoreCondition")
    @SaCheckLogin
    public Result<GetScoreRespVO> getScoreRespVO(@RequestParam Long groupId){
        return paperDetailService.getScoreRespVO(groupId);
    }

    @ApiOperation(value = "通过组ID获取错题率")
    @GetMapping("/getErrorRate")
    @SaCheckLogin
    public Result<GetErrorRateRespVO> getErrorRate(@RequestParam Long groupId){
        return paperDetailService.getErrorRate(groupId);
    }

    @ApiOperation(value = "老师获取全班情况")
    @PostMapping("/getTotalCondition")
    @SaCheckLogin
    public Result<GetTotalConditionRespVO> getTotalCondition(){
        return paperDetailService.getTotalCondition();
    }
}
