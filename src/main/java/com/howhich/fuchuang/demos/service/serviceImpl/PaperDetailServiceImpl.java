package com.howhich.fuchuang.demos.service.serviceImpl;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.howhich.fuchuang.demos.Utils.FileUtils;
import com.howhich.fuchuang.demos.Utils.WordUtils;
import com.howhich.fuchuang.demos.constant.Result;
import com.howhich.fuchuang.demos.entity.Base.*;
import com.howhich.fuchuang.demos.entity.resp.*;
import com.howhich.fuchuang.demos.mapper.PaperDetailMapper;
import com.howhich.fuchuang.demos.mapper.PaperResultMapper;
import com.howhich.fuchuang.demos.mapper.RecordMapper;
import com.howhich.fuchuang.demos.service.PaperDetailService;
import com.howhich.fuchuang.demos.service.RecordsService;
import freemarker.template.TemplateException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class PaperDetailServiceImpl extends ServiceImpl<PaperDetailMapper, PaperDetail> implements PaperDetailService {

    @Autowired
    private PaperDetailMapper paperDetailMapper;
    @Autowired
    private PaperResultMapper paperResultMapper;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private RecordMapper recordMapper;
    @Value("${file.fileurl}")
    private String fileurl;
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
//        if(ObjectUtils.isEmpty(redisTemplate.opsForValue().get("totalCounter" + groupId))){
//            redisTemplate.opsForValue().set("totalCounter"+groupId,0,60, TimeUnit.SECONDS);
//            return Result.fail("正在阅卷，请等待");
//        }

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

//        //TODO 假设在阅卷
//        if(ObjectUtils.isEmpty(redisTemplate.opsForValue().get("detailCounter" + groupId))){
//            redisTemplate.opsForValue().set("detailCounter"+groupId,0);
//            return Result.fail("正在阅卷，请等待");
//        }
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

    @Override
    public Result<String> exportPaperDetail(Long groupId) {
        //todo 试卷详情

        Map<String,Object> dataMap = new HashMap<>();

        List<Map<String,Object>> ExportEntity = new ArrayList<>();

        Map<String,Object> et1 = new HashMap<>();

        List<PaperDetail> paperDetails = paperDetailMapper.selectList(new LambdaQueryWrapper<PaperDetail>()
                .eq(PaperDetail::getGroupId, groupId).eq(PaperDetail::getType, 3));

        paperDetails.forEach(paperDetail -> {
            Map<String,Object> et = new HashMap<>();
            et.put("questionNum",paperDetail.getQuestionNum());
            et.put("score",paperDetail.getScore());
            et.put("comment",paperDetail.getComment());
            et.put("image", FileUtils.getImgFileToBase64(paperDetail.getUrl()));
            ExportEntity.add(et);
        });
        dataMap.put("ExportEntity",ExportEntity);

        WordUtils wordUtils = new WordUtils();
        String wordName = fileurl + System.currentTimeMillis() +"test.doc";
        try {
            wordUtils.exportWord(dataMap,"temp.xml",wordName,fileurl);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (TemplateException e) {
            throw new RuntimeException(e);
        }

        return Result.success(wordName);
    }

    @Override
    public Result<GetScoreRespVO> getScoreRespVO(Long groupId) {
        List<PaperDetail> paperDetails = paperDetailMapper.selectList(new LambdaQueryWrapper<PaperDetail>()
                .eq(PaperDetail::getGroupId, groupId));
        GetScoreRespVO respVO = new GetScoreRespVO();
        List<NumAndScore> numAndScores = new ArrayList<>();
        paperDetails.forEach(paperDetail -> {
            NumAndScore numAndScore = new NumAndScore();
            BeanUtils.copyProperties(paperDetail,numAndScore);
            numAndScores.add(numAndScore);
        });
        respVO.setNumAndScoreList(numAndScores);
        return Result.success(respVO);
    }

    @Override
    public Result<GetErrorRateRespVO> getErrorRate(Long groupId) {

        List<PaperDetail> paperDetails = paperDetailMapper.selectList(new LambdaQueryWrapper<PaperDetail>()
                .eq(PaperDetail::getGroupId, groupId)
                .eq(PaperDetail::getType, 3));
        AtomicReference<Integer> _0 = new AtomicReference<>(0);
        AtomicReference<Integer> _20 = new AtomicReference<>(0);
        AtomicReference<Integer> _40 = new AtomicReference<>(0);
        AtomicReference<Integer> _60 = new AtomicReference<>(0);
        AtomicReference<Integer> _80 = new AtomicReference<>(0);
        AtomicReference<Integer> _99 = new AtomicReference<>(0);
        AtomicReference<Integer> _100 = new AtomicReference<>(0);

        paperDetails.forEach(paperDetail -> {
            if(paperDetail.getScore()/paperDetail.getTotalScore() < 0.001){
                _0.updateAndGet(v -> v + 1);
            }else if(paperDetail.getScore()/paperDetail.getTotalScore() > 0.001 ||
                    paperDetail.getScore()/paperDetail.getTotalScore() <= 0.2){
                _20.updateAndGet(v -> v + 1);
            }else if(paperDetail.getScore()/paperDetail.getTotalScore() > 0.2 ||
                    paperDetail.getScore()/paperDetail.getTotalScore() <= 0.4){
                _40.updateAndGet(v -> v + 1);
            }else if(paperDetail.getScore()/paperDetail.getTotalScore() > 0.4 ||
                    paperDetail.getScore()/paperDetail.getTotalScore() <= 0.6){
                _60.updateAndGet(v -> v + 1);
            }else if(paperDetail.getScore()/paperDetail.getTotalScore() > 0.6 ||
                    paperDetail.getScore()/paperDetail.getTotalScore() <= 0.8){
                _80.updateAndGet(v -> v + 1);
            }else if(paperDetail.getScore()/paperDetail.getTotalScore() > 0.8 ||
                    paperDetail.getScore()/paperDetail.getTotalScore() < 1){
                _99.updateAndGet(v -> v + 1);
            }else if(paperDetail.getScore() == paperDetail.getTotalScore()  ){
                _100.updateAndGet(v -> v + 1);
            }
        });

        GetErrorRateRespVO respVO = new GetErrorRateRespVO();

        respVO.set_0(_0.get());
        respVO.set_20(_20.get());
        respVO.set_40(_40.get());
        respVO.set_60(_60.get());
        respVO.set_80(_80.get());
        respVO.set_99(_99.get());
        respVO.set_100(_100.get());

        return Result.success(respVO);
    }

    @Override
    public Result<GetTotalConditionRespVO> getTotalCondition() {
        long loginId = StpUtil.getLoginIdAsLong();
        List<Record> records = recordMapper.selectList(new LambdaQueryWrapper<Record>()
                .eq(Record::getCreateUser, loginId));
        List<Long> recordIds = new ArrayList<>();

        records.forEach(record -> {
            recordIds.add(record.getId());
    });

        List<Map<Long, RecordCondition>> mapList = new ArrayList<>();
        Map<Long, RecordCondition> map = new HashMap<>();

        //处理每一个考试
        recordIds.forEach(recordId -> {
            Map<Integer,Float> questionAndScore = new HashMap<>();
            Map<Integer,Float> questionAndMax = new HashMap<>();
            Map<Integer,Integer> failNum = new HashMap<>();

            RecordCondition recordCondition = new RecordCondition();
            String recordName = recordMapper.selectOne(new LambdaQueryWrapper<Record>()
                    .eq(Record::getId, recordId)).getRecordName();
            recordCondition.setRecordName(recordName);
            // 1 是 题目 数量
            List<Integer> questionNums = new ArrayList<>();
            Integer count = Math.toIntExact(paperDetailMapper.selectCount(new LambdaQueryWrapper<PaperDetail>()
                    .eq(PaperDetail::getGroupId, recordId).eq(PaperDetail::getType, 1)));
            // 获得所有题号
            List<PaperDetail> paperDetails = paperDetailMapper.selectList(new LambdaQueryWrapper<PaperDetail>()
                    .eq(PaperDetail::getGroupId, recordId).eq(PaperDetail::getType, 1));
            paperDetails.forEach(paperDetail -> {
                if(!questionAndScore.containsKey(paperDetail.getQuestionNum())){
                    questionAndScore.put(paperDetail.getQuestionNum(),0.0F);
                    questionAndMax.put(paperDetail.getQuestionNum(),0.0F);
                    failNum.put(paperDetail.getQuestionNum(),0);
                    questionNums.add(paperDetail.getQuestionNum());
                }
            });

            recordCondition.setTotal(count);
        // 每个人 的 结果 获取groupID
            List<PaperResult> paperResults = paperResultMapper.selectList(new LambdaQueryWrapper<PaperResult>()
                    .eq(PaperResult::getRecordId, recordId));
            ArrayList groupIds = new ArrayList();

            List<Integer> failNums = new ArrayList<>();

            paperResults.forEach(paperResult -> {
                if(!groupIds.contains(paperResult.getResultGroupId())){
                    groupIds.add(paperResult.getResultGroupId());
                }
            });

            groupIds.forEach(groupId -> {
                List<PaperDetail> personalDetails = paperDetailMapper.selectList(new LambdaQueryWrapper<PaperDetail>()
                        .eq(PaperDetail::getGroupId, groupId).eq(PaperDetail::getType, 3));
                personalDetails.forEach(paperDetail -> {
                    int QN = paperDetail.getQuestionNum();
                    questionAndScore.put(QN,questionAndScore.get(QN) + paperDetail.getScore());
                    questionAndMax.put(QN, Math.max(paperDetail.getScore(),questionAndMax.get(QN)));
                    if(paperDetail.getScore()!=paperDetail.getTotalScore()){
                        failNum.put(QN,failNum.get(QN)+1);
                    }
                });
            });
            List<Float> avgs = new ArrayList<>();
            List<Float> maxNums = new ArrayList<>();

            for(Map.Entry<Integer,Float> entry: questionAndScore.entrySet()){
                entry.setValue(entry.getValue()/groupIds.size());
            }
            avgs.addAll(questionAndScore.values());
            maxNums.addAll(questionAndMax.values());
            failNums.addAll(failNum.values());

            recordCondition.setAverages(avgs);
            recordCondition.setQuestionNums(questionNums);
            recordCondition.setMaxNums(maxNums);
            recordCondition.setFailNum(failNums);
//            recordConditions.add(recordCondition);
            map.put(recordId,recordCondition);
//            mapList.add(map);
        });
        GetTotalConditionRespVO respVO = new GetTotalConditionRespVO();

        respVO.setTotalConditions(map);

        return Result.success(respVO);
    }

}
