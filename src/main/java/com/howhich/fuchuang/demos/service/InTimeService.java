package com.howhich.fuchuang.demos.service;

import com.howhich.fuchuang.demos.constant.Result;
import com.howhich.fuchuang.demos.entity.req.InTimeReqVO;
import com.howhich.fuchuang.demos.entity.resp.PaperDetailRespVO;

public interface InTimeService {
    Result uploadInTimePhotos(InTimeReqVO reqVO);

    Result<PaperDetailRespVO> getInTimePhotosById(Long paperDetailId);

    Result getInTimeResult(Long groupId);
}
