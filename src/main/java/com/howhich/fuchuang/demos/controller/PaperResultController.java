package com.howhich.fuchuang.demos.controller;

import com.howhich.fuchuang.demos.constant.Result;
import com.howhich.fuchuang.demos.entity.Base.PaperResult;
import com.howhich.fuchuang.demos.service.PaperResultService;
import com.howhich.fuchuang.demos.service.RecordsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/paperResult")
@Api(tags = "试卷评阅结果")
public class PaperResultController {
    @Resource
    private RecordsService recordsService;
    @Resource
    private PaperResultService paperResultService;

    @PostMapping("/importSinglePhoto")
    @ApiOperation(value = "导入单张图片")
    public Result importSinglePhotp(@RequestBody MultipartFile file) throws IOException {
        return recordsService.importSinglePhoto(file);
    }
    @PostMapping("/importBatchPhoto")
    @ApiOperation(value = "导入多张图片")
    public Result importBatchPhotp(@RequestBody List<MultipartFile> fileList) throws IOException {
        return recordsService.importBatchPhotp(fileList);
    }
    @GetMapping("/getPaperResultsByRecordId")
    @ApiOperation(value = "通过导入记录获取试卷评阅结果")
    public Result<List<PaperResult>> getPaperResultsByRecordId(@RequestParam Long recordId){
        return paperResultService.page(recordId);
    }

}
