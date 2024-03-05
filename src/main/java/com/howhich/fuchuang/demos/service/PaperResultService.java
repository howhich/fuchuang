package com.howhich.fuchuang.demos.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.howhich.fuchuang.demos.constant.Result;
import com.howhich.fuchuang.demos.entity.Base.PaperResult;
import com.howhich.fuchuang.demos.entity.req.GetPaperResultByIdReqVO;
import com.howhich.fuchuang.demos.entity.req.GetStudentPaperResultReqVO;
import com.howhich.fuchuang.demos.entity.resp.GetPaperResultByIdRespVO;
import com.howhich.fuchuang.demos.entity.resp.GetStudentPaperResultRespVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PaperResultService extends IService<PaperResult> {
    Result<GetPaperResultByIdRespVO> page(@Param(value = "reqVO") GetPaperResultByIdReqVO reqVO);

    Result<GetStudentPaperResultRespVO> getStudentPaperResult(@Param(value = "reqVO") GetStudentPaperResultReqVO reqVO);

    void updateStatus(Long id);
}
