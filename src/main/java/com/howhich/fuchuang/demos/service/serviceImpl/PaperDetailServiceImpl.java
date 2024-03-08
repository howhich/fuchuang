package com.howhich.fuchuang.demos.service.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.howhich.fuchuang.demos.constant.Result;
import com.howhich.fuchuang.demos.entity.Base.PaperDetail;
import com.howhich.fuchuang.demos.entity.resp.GetPaperDetailRespVO;
import com.howhich.fuchuang.demos.entity.resp.GetPaperVisualizationRespVO;
import com.howhich.fuchuang.demos.entity.resp.GetTotalJudgeRespVO;
import com.howhich.fuchuang.demos.mapper.PaperDetailMapper;
import com.howhich.fuchuang.demos.mapper.PaperResultMapper;
import com.howhich.fuchuang.demos.service.PaperDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PaperDetailServiceImpl extends ServiceImpl<PaperDetailMapper, PaperDetail> implements PaperDetailService {

    @Autowired
    private PaperDetailMapper paperDetailMapper;
    @Autowired
    private PaperResultMapper paperResultMapper;
    @Override
    public Result<GetTotalJudgeRespVO> getTotalPaperDetailById(Long id) {
//        LambdaQueryWrapper<PaperDetail> queryWrapper = new LambdaQueryWrapper();
//        queryWrapper.eq(PaperDetail::getPaperResultId,id);
//        List<PaperDetail> paperDetails = paperDetailMapper.selectList(queryWrapper);
//
//        List<String> urls = new ArrayList<>();
//        paperDetails.forEach(paperDetail ->urls.add(paperDetail.getUrl()));
//
//        GetTotalJudgeRespVO reqVO = new GetTotalJudgeRespVO();
//        reqVO.setTotalPhotoUrls(urls);
        return Result.success();
    }

    @Override
    public Result<GetPaperDetailRespVO> getPaperDetailByIdAndNum(Long groupId) {

//        LambdaQueryWrapper<PaperResult> resultLambdaQueryWrapper = new LambdaQueryWrapper<>();
//        resultLambdaQueryWrapper.eq(PaperResult::getRecordId,recordId)
//                .eq(PaperResult::getStudentNum,studentNum);
//        Long resultId = paperResultMapper.selectOne(resultLambdaQueryWrapper).getId();
//
//
//        LambdaQueryWrapper<PaperDetail> lambdaQueryWrapper = new LambdaQueryWrapper<>();
//        lambdaQueryWrapper.eq(PaperDetail::getPaperResultId,resultId)
//                .eq(PaperDetail::getQuestionNum,questionNum);
//        PaperDetail paperDetail = paperDetailMapper.selectOne(lambdaQueryWrapper);
//
//
//        GetPaperDetailRespVO respVO = new GetPaperDetailRespVO();
//        respVO.setComment(paperDetail.getComment());
        return null;
    }

    @Override
    public Result<List<String>> getPaperTotal(Long groupId) {
        List<String> urls = new ArrayList<>();
        LambdaQueryWrapper<PaperDetail> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(PaperDetail::getGroupId,groupId).eq(PaperDetail::getType,1);
        List<PaperDetail> paperDetails = paperDetailMapper.selectList(queryWrapper);
        paperDetails.forEach(paperDetail -> {
            urls.add(paperDetail.getUrl());
        });
        return Result.success(urls);
    }

    @Override
    public Result<List<GetPaperDetailRespVO>> getPaperDetail(Long groupId) {

        LambdaQueryWrapper<PaperDetail> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(PaperDetail::getGroupId,groupId).eq(PaperDetail::getType,3);
        List<PaperDetail> paperDetails = paperDetailMapper.selectList(queryWrapper);

        List<GetPaperDetailRespVO> respVOS = new ArrayList<>();
        paperDetails.forEach(paperDetail ->{
            GetPaperDetailRespVO respVO = new GetPaperDetailRespVO();
            respVO.setComment(paperDetail.getComment());
            respVO.setUrl(paperDetail.getUrl());
            respVO.setScore(paperDetail.getScore());
            respVOS.add(respVO);
        });

        return Result.success(respVOS);
    }

    @Override
    public Result<GetPaperVisualizationRespVO> getPaperVisualization(Long groupId) {
        return null;
    }
}
