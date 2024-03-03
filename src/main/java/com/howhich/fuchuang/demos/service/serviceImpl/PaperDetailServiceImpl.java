package com.howhich.fuchuang.demos.service.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.howhich.fuchuang.demos.constant.Result;
import com.howhich.fuchuang.demos.entity.Base.PaperDetail;
import com.howhich.fuchuang.demos.entity.resp.GetTotalJudgeRespVO;
import com.howhich.fuchuang.demos.mapper.PaperDetailMapper;
import com.howhich.fuchuang.demos.service.PaperDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PaperDetailServiceImpl extends ServiceImpl<PaperDetailMapper, PaperDetail> implements PaperDetailService {

    @Autowired
    private PaperDetailMapper paperDetailMapper;
    @Override
    public Result<GetTotalJudgeRespVO> getTotalPaperDetailById(Long id) {
        LambdaQueryWrapper<PaperDetail> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(PaperDetail::getPaperResultId,id);
        List<PaperDetail> paperDetails = paperDetailMapper.selectList(queryWrapper);

        List<String> urls = new ArrayList<>();
        paperDetails.forEach(paperDetail ->urls.add(paperDetail.getUrl()));

        GetTotalJudgeRespVO reqVO = new GetTotalJudgeRespVO();
        reqVO.setTotalPhotoUrls(urls);
        return Result.success(reqVO);
    }
}
