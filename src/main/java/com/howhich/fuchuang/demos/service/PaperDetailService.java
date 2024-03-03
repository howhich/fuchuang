package com.howhich.fuchuang.demos.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.howhich.fuchuang.demos.constant.Result;
import com.howhich.fuchuang.demos.entity.Base.PaperDetail;
import com.howhich.fuchuang.demos.entity.resp.GetTotalJudgeRespVO;

public interface PaperDetailService extends IService<PaperDetail> {

    Result<GetTotalJudgeRespVO> getTotalPaperDetailById(Long id);
}
