package com.howhich.fuchuang.demos.controller;

import com.howhich.fuchuang.demos.constant.Result;
import com.howhich.fuchuang.demos.entity.req.ImportRecordsReqVO;
import com.howhich.fuchuang.demos.entity.resp.ImportRecordsRespVO;
import com.howhich.fuchuang.demos.entity.resp.PaperDetailRespVO;
import com.howhich.fuchuang.demos.service.InTimeService;
import com.howhich.fuchuang.demos.service.RecordsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/inTime")
@Api(tags = "实时分析")
public class InTimeController {

    @Resource
    private InTimeService  inTimeService;
    @PostMapping("/uploadInTimePhotos")
    @ApiOperation(value = "批量导入实时分析照片")
    public Result<Long> uploadInTimePhotos(@RequestBody List<MultipartFile> fileList){
        return inTimeService.uploadInTimePhotos(fileList);
    }
    @GetMapping("/getInTimePhotos")
    @ApiOperation(value = "获取实时分析照片结果")
    public Result<PaperDetailRespVO> getInTimePhotos(@RequestParam Long paperId){
        return inTimeService.getInTimePhotosById(paperId);
    }
}
