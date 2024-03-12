package com.howhich.fuchuang.demos.controller;

import cn.dev33.satoken.annotation.SaCheckRole;
import com.howhich.fuchuang.demos.constant.Result;
import com.howhich.fuchuang.demos.constant.RoleType;
import com.howhich.fuchuang.demos.entity.Base.PaperResult;
import com.howhich.fuchuang.demos.entity.req.GetPaperResultByIdReqVO;
import com.howhich.fuchuang.demos.entity.req.GetStudentPaperResultReqVO;
import com.howhich.fuchuang.demos.entity.req.ImportPaperResultReqVO;
import com.howhich.fuchuang.demos.entity.resp.GetPaperResultByIdRespVO;
import com.howhich.fuchuang.demos.entity.resp.GetStudentPaperResultRespVO;
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
@RequestMapping("/api/paperResult")
@Api(tags = "试卷paper")
public class PaperResultController {
    @Resource
    private RecordsService recordsService;
    @Resource
    private PaperResultService paperResultService;

//    @PostMapping("/importPaper")
//    @ApiOperation(value = "导入单张图片")
//    public Result<String> importSinglePhoto(MultipartFile file) throws IOException {
//        return recordsService.importSinglePhoto(file);
//    }
//    @PostMapping("/importBatchPhoto")
//    @ApiOperation(value = "导入多张图片")
//    public Result importBatchPhoto(@RequestBody List<MultipartFile> fileList) throws IOException {
//        return recordsService.importBatchPhoto(fileList);
//    }
    @PostMapping("/getPaperResultsByRecordId")
    @SaCheckRole(value = RoleType.TEACHER.code)
    @ApiOperation(value = "老师通过考试记录id获取试卷评阅结果")
    public Result<GetPaperResultByIdRespVO> getPaperResultsByRecordId(@RequestBody GetPaperResultByIdReqVO reqVO){
        return paperResultService.page(reqVO);
    }

    @SaCheckRole(value = RoleType.STUDENT.code)
    @PostMapping("/getStudentPaperResult")
    @ApiOperation(value = "学生获取自身试卷评阅结果")
    public Result<GetStudentPaperResultRespVO> getStudentPaperResult(@RequestBody GetStudentPaperResultReqVO reqVO){
        return paperResultService.getStudentPaperResult(reqVO);
    }
}
