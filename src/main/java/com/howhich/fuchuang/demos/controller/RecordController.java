package com.howhich.fuchuang.demos.controller;

import cn.dev33.satoken.annotation.SaCheckRole;
import com.howhich.fuchuang.demos.aspect.OperationAspect;
import com.howhich.fuchuang.demos.constant.Result;
import com.howhich.fuchuang.demos.constant.RoleType;
import com.howhich.fuchuang.demos.entity.req.GetImportRecordsReqVO;
import com.howhich.fuchuang.demos.entity.req.ImportRecordsReqVO;
import com.howhich.fuchuang.demos.entity.resp.GetImportRecordsRespVO;
import com.howhich.fuchuang.demos.entity.resp.ImportRecordsRespVO;
import com.howhich.fuchuang.demos.service.RecordsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/record")
@Api(tags = "考试record")
public class RecordController {
    @Resource
    private RecordsService recordsService;

    @PostMapping("/getImportRecords")
    @ApiOperation(value = "老师获取考试导入记录")
    @SaCheckRole(value = RoleType.TEACHER.code)
    public Result<GetImportRecordsRespVO> getImportRecords(@RequestBody GetImportRecordsReqVO reqVO){
        return recordsService.page(reqVO);
    }
    @PostMapping("/importRecords")
    @ApiOperation(value = "老师导入考试记录")
    @SaCheckRole(value = RoleType.TEACHER.code)
    @OperationAspect
    public Result importRecords(@RequestBody ImportRecordsReqVO reqVO){
        return recordsService.importRecords(reqVO);
    }

    @GetMapping("/exportRecords")
    @ApiOperation(value = "老师导出考试记录")
    @SaCheckRole(value = RoleType.TEACHER.code)
    public Result<String> exportRecords(@RequestParam Long recordId){
        return recordsService.exportRecords(recordId);
    }

    @GetMapping("/exportStudentSelfRecords")
    @ApiOperation(value = "学生导出考试记录")
    @SaCheckRole(value = RoleType.STUDENT.code)
    public Result<String> exportStudentSelfRecords(){

        return recordsService.exportSelfRecords();
    }
}
