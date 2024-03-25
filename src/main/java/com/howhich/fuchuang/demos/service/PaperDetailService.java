package com.howhich.fuchuang.demos.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.howhich.fuchuang.demos.constant.Result;
import com.howhich.fuchuang.demos.entity.Base.PaperDetail;
import com.howhich.fuchuang.demos.entity.resp.GetPaperDetailRespVO;
import com.howhich.fuchuang.demos.entity.resp.GetPaperVisualizationRespVO;
import com.howhich.fuchuang.demos.entity.resp.GetTotalJudgeRespVO;
import com.howhich.fuchuang.demos.entity.resp.UpdatePaperDetailReqVO;

import java.util.List;

public interface PaperDetailService extends IService<PaperDetail> {

    Result<GetTotalJudgeRespVO> getTotalPaperDetailById(Long id);

    Result<GetPaperDetailRespVO> getPaperDetailByIdAndNum(Long groupId);

    Result<GetTotalJudgeRespVO> getPaperTotal(Long groupId);

    Result<List<GetPaperDetailRespVO>> getPaperDetail(Long groupId);

    Result<GetPaperVisualizationRespVO> getPaperVisualization(Long groupId);

    Result updatePaperDetail(UpdatePaperDetailReqVO reqVO);

    Result exportPaperDetail(Long groupId);
}
