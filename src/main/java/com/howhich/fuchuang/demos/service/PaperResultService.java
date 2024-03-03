package com.howhich.fuchuang.demos.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.howhich.fuchuang.demos.constant.Result;
import com.howhich.fuchuang.demos.entity.Base.PaperResult;
import com.howhich.fuchuang.demos.entity.req.GetPaperResultByIdReqVO;
import com.howhich.fuchuang.demos.entity.resp.GetPaperResultByIdRespVO;

import java.util.List;

public interface PaperResultService extends IService<PaperResult> {
    Result<GetPaperResultByIdRespVO> page(GetPaperResultByIdReqVO reqVO);
}
