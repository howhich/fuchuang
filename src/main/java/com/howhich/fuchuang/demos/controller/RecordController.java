package com.howhich.fuchuang.demos.controller;

import com.howhich.fuchuang.demos.constant.Result;
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
@Api(tags = "导入记录")
public class RecordController {
    @Resource
    private RecordsService recordsService;

    @PostMapping("/importRecords")
    @ApiOperation(value = "获取导入记录")
    public Result<ImportRecordsRespVO> getImportRecords(@RequestBody ImportRecordsReqVO reqVO){
        return recordsService.page(reqVO);
    }
}
