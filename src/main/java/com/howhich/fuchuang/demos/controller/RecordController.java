package com.howhich.fuchuang.demos.controller;

import com.howhich.fuchuang.demos.constant.Result;
import com.howhich.fuchuang.demos.entity.req.GetImportRecordsReqVO;
import com.howhich.fuchuang.demos.entity.req.ImportRecordsReqVO;
import com.howhich.fuchuang.demos.entity.resp.ImportRecordsRespVO;
import com.howhich.fuchuang.demos.service.RecordsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/record")
@Api(tags = "考试record")
public class RecordController {
    @Resource
    private RecordsService recordsService;

    @PostMapping("/getImportRecords")
    @ApiOperation(value = "获取考试导入记录")
    public Result<ImportRecordsRespVO> getImportRecords(@RequestBody GetImportRecordsReqVO reqVO){
        return recordsService.page(reqVO);
    }
    @PostMapping("/importRecords")
    @ApiOperation(value = "导入考试记录")
    public Result importRecords(@RequestBody ImportRecordsReqVO reqVO){
        return recordsService.importRecords(reqVO);
    }
}
