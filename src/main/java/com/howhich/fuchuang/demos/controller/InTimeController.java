package com.howhich.fuchuang.demos.controller;

import com.howhich.fuchuang.demos.constant.Result;
import com.howhich.fuchuang.demos.entity.req.InTimeReqVO;
import com.howhich.fuchuang.demos.entity.resp.InTimeRespVO;
import com.howhich.fuchuang.demos.entity.resp.PaperDetailRespVO;
import com.howhich.fuchuang.demos.service.InTimeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/api/inTime")
@Api(tags = "实时inTime")
public class InTimeController {

    @Resource
    private InTimeService  inTimeService;
    @PostMapping("/uploadInTimePhotos")
    @ApiOperation(value = "创建实时导入考试记录(第二次请求返回GroupId)")
    public Result<InTimeRespVO> uploadInTimePhotos(@RequestBody InTimeReqVO reqVO){
        return inTimeService.uploadInTimePhotos(reqVO);
    }
//    @GetMapping("/getInTimePhotos")
//    @ApiOperation(value = "获取实时分析照片结果")
//    public Result<PaperDetailRespVO> getInTimePhotos(@RequestParam Long paperId){
//        return inTimeService.getInTimePhotosById(paperId);
//    }
}
