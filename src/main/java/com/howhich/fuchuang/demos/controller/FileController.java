package com.howhich.fuchuang.demos.controller;

import com.howhich.fuchuang.demos.constant.Result;
import com.howhich.fuchuang.demos.entity.req.GetPaperResultByIdReqVO;
import com.howhich.fuchuang.demos.entity.req.GetStudentPaperResultReqVO;
import com.howhich.fuchuang.demos.entity.resp.GetPaperResultByIdRespVO;
import com.howhich.fuchuang.demos.entity.resp.GetStudentPaperResultRespVO;
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
@RequestMapping("/import")
@Api(tags = "导入file")
public class FileController {
    @Resource
    private RecordsService recordsService;

    @PostMapping("/importFile")
    @ApiOperation(value = "导入图片")
    public Result<String> importSinglePhoto(MultipartFile file) throws IOException {
        return recordsService.importSinglePhoto(file);
    }
}
