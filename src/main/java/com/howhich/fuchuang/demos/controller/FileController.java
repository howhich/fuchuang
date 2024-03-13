package com.howhich.fuchuang.demos.controller;

import cn.dev33.satoken.annotation.SaCheckRole;
import com.howhich.fuchuang.demos.constant.Result;
import com.howhich.fuchuang.demos.constant.RoleType;
import com.howhich.fuchuang.demos.entity.req.GetPaperResultByIdReqVO;
import com.howhich.fuchuang.demos.entity.req.GetStudentPaperResultReqVO;
import com.howhich.fuchuang.demos.entity.resp.GetPaperResultByIdRespVO;
import com.howhich.fuchuang.demos.entity.resp.GetStudentPaperResultRespVO;
import com.howhich.fuchuang.demos.entity.resp.ImportBatchStudentsRespVO;
import com.howhich.fuchuang.demos.service.PaperResultService;
import com.howhich.fuchuang.demos.service.RecordsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;

@RestController
@RequestMapping("/api/import")
@Api(tags = "导入file")
public class FileController {
    @Resource
    private RecordsService recordsService;

    @PostMapping("/importFile")
    @ApiOperation(value = "导入图片")
    @SaCheckRole(value = RoleType.TEACHER.code)
    public Result<String> importSinglePhoto(MultipartFile file) throws IOException {
        return recordsService.importSinglePhoto(file);
    }
    @PostMapping("/importBatchStudents")
    @ApiOperation(value = "批量导入学生")
    @SaCheckRole(value = RoleType.TEACHER.code)
    public Result<ImportBatchStudentsRespVO> importBatchStudents(MultipartFile file) throws IOException {
        return recordsService.importBatchStudents(file);
    }
}
