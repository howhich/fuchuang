package com.howhich.fuchuang.demos.service.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.howhich.fuchuang.demos.constant.Result;
import com.howhich.fuchuang.demos.entity.Base.Paper;
import com.howhich.fuchuang.demos.entity.Base.PaperDetail;
import com.howhich.fuchuang.demos.entity.Base.PaperResult;
import com.howhich.fuchuang.demos.entity.resp.GetPaperDetailRespVO;
import com.howhich.fuchuang.demos.entity.resp.GetPaperVisualizationRespVO;
import com.howhich.fuchuang.demos.entity.resp.GetTotalJudgeRespVO;
import com.howhich.fuchuang.demos.entity.resp.UpdatePaperDetailReqVO;
import com.howhich.fuchuang.demos.mapper.PaperDetailMapper;
import com.howhich.fuchuang.demos.mapper.PaperResultMapper;
import com.howhich.fuchuang.demos.service.PaperDetailService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PaperDetailServiceImpl extends ServiceImpl<PaperDetailMapper, PaperDetail> implements PaperDetailService {

    @Autowired
    private PaperDetailMapper paperDetailMapper;
    @Autowired
    private PaperResultMapper paperResultMapper;
    @Autowired
    private RedisTemplate redisTemplate;
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
    public Result<GetTotalJudgeRespVO> getPaperTotal(Long groupId) {
        //TODO 假设在阅卷
        if(ObjectUtils.isEmpty(redisTemplate.opsForValue().get("totalCounter" + groupId))){
            redisTemplate.opsForValue().set("totalCounter"+groupId,0);
            return Result.fail("正在阅卷，请等待");
        }

        GetTotalJudgeRespVO respVO = new GetTotalJudgeRespVO();
//        0表示答题卡 1表示原卷 2表示参考答案
        List<String> answerCardUrls = new ArrayList<>();
        LambdaQueryWrapper<PaperDetail> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(PaperDetail::getGroupId,groupId)
                .eq(PaperDetail::getType,0)
                .orderByAsc(PaperDetail::getQuestionNum);
        List<PaperDetail> paperDetails = paperDetailMapper.selectList(queryWrapper);
        paperDetails.forEach(paperDetail -> {
            answerCardUrls.add(paperDetail.getUrl());
        });

        PaperResult paperResult = paperResultMapper.selectOne(new LambdaQueryWrapper<PaperResult>()
                .eq(PaperResult::getResultGroupId, groupId).last("limit 1"));
        Long recordId = paperResult.getRecordId();


        List<String> originalPaperUrls = new ArrayList<>();
        List<PaperDetail> originalPapers = paperDetailMapper.selectList(new LambdaQueryWrapper<PaperDetail>()
                .eq(PaperDetail::getGroupId, recordId)
                .eq(PaperDetail::getType, 1)
                .orderByAsc(PaperDetail::getQuestionNum));
        originalPapers.forEach(originalPaper-> {
            originalPaperUrls.add(originalPaper.getUrl());
        });

        List<String> answerUrls = new ArrayList<>();
        List<PaperDetail> answerPapers = paperDetailMapper.selectList(new LambdaQueryWrapper<PaperDetail>()
                .eq(PaperDetail::getGroupId, recordId)
                .eq(PaperDetail::getType, 1)
                .orderByAsc(PaperDetail::getQuestionNum));
        answerPapers.forEach(originalPaper-> {
            answerUrls.add(originalPaper.getUrl());
        });

        respVO.setAnswerCardUrls(answerCardUrls);
        respVO.setAnswerUrls(answerUrls);
        respVO.setOriginalPhotoUrls(originalPaperUrls);
        return Result.success(respVO);
    }

    @Override
    public Result<List<GetPaperDetailRespVO>> getPaperDetail(Long groupId) {

        //TODO 假设在阅卷
        if(ObjectUtils.isEmpty(redisTemplate.opsForValue().get("detailCounter" + groupId))){
            redisTemplate.opsForValue().set("detailCounter"+groupId,0);
            return Result.fail("正在阅卷，请等待");
        }
        LambdaQueryWrapper<PaperDetail> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(PaperDetail::getGroupId,groupId).eq(PaperDetail::getType,3)
                .orderByAsc(PaperDetail::getQuestionNum);
        List<PaperDetail> paperDetails = paperDetailMapper.selectList(queryWrapper);

        List<GetPaperDetailRespVO> respVOS = new ArrayList<>();
        paperDetails.forEach(paperDetail ->{
            GetPaperDetailRespVO respVO = new GetPaperDetailRespVO();
            respVO.setQuestionNum(paperDetail.getQuestionNum());
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

    @Override
    public Result updatePaperDetail(UpdatePaperDetailReqVO reqVO) {
        PaperDetail paperDetail = new PaperDetail();
        paperDetail.setType(3);
        BeanUtils.copyProperties(reqVO,paperDetail);
        LambdaQueryWrapper<PaperDetail> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PaperDetail::getGroupId,reqVO.getGroupId())
                        .eq(PaperDetail::getQuestionNum,reqVO.getQuestionNum())
                        .eq(PaperDetail::getType,3);
        paperDetailMapper.update(paperDetail,queryWrapper);
//        reqVO.getComment()
        return Result.success("更新成功");
    }
}
