package com.howhich.fuchuang.demos.service.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.howhich.fuchuang.demos.constant.Result;
import com.howhich.fuchuang.demos.entity.Base.*;
import com.howhich.fuchuang.demos.entity.req.InTimeReqVO;
import com.howhich.fuchuang.demos.entity.resp.PaperDetailRespVO;
import com.howhich.fuchuang.demos.mapper.PaperDetailMapper;
import com.howhich.fuchuang.demos.mapper.PaperResultMapper;
import com.howhich.fuchuang.demos.mapper.StudentMapper;
import com.howhich.fuchuang.demos.service.*;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class InTimeServiceImpl implements InTimeService {
    @Autowired
    private RecordsService recordsService;
    @Resource
    private StudentMapper studentMapper;
    @Resource
    private PaperResultMapper paperResultMapper;
    @Resource
    private PaperDetailMapper paperDetailMapper;
    @Resource
    private QuestionService questionService;
    @Value("${photo.BaseURL}")
    private String BaseURL;
    @Autowired
    private RedisTemplate redisTemplate;
    @Override
    public Result uploadInTimePhotos(InTimeReqVO reqVO) {
        //暂时做一个假逻辑
//        if(ObjectUtils.isEmpty(redisTemplate.opsForValue().get("counter"))){
//            redisTemplate.opsForValue().set("counter", 0);
//        }
//        redisTemplate.opsForValue().increment("counter");
//        String counter = ""+redisTemplate.opsForValue().get("counter");
//        if(counter.equals("1")){
//            return Result.fail("正在处理中，请稍后");
//        }
//        if(counter.equals("2")){
            List<Integer> hashCodes = new ArrayList<>();
            Map<String,List<String>> map = new HashMap();
            try{
                List<String> fileNames = reqVO.getUrls();
                Record record = new Record();
                record.setRecordName("实时阅卷");
                //TODO Mark一下
                record.setStatus("DONE");

                recordsService.save(record);
                Long id = recordsService.getOne(new LambdaQueryWrapper<Record>()
                        .orderByDesc(Record::getCreateTime)
                        .last("limit 1")).getId();
                record.setId(id);
                fileNames.forEach(fileName->{
//                "/www/wwwroot/picture/"
                    int lastIndex = fileName.lastIndexOf("/");
                    String originFileName = fileName;
                    fileName = fileName.substring(lastIndex+1);
                    PaperResult paperResult = new PaperResult();

                    String[] strings = fileName.split("_");
                    String pictureType = strings[0];
                    String studentNum = strings[1];
                    String pageNum = strings[2];


                    String name = "empty";
                    if(pictureType.equals("0")){
                        //是卷子
                        int hashCode = Math.abs((id + studentNum).hashCode());

                        if(!hashCodes.contains(hashCode)){
                            hashCodes.add(hashCode);
                        }

                        if(!map.containsKey(studentNum)){

                            List<String> list = new ArrayList<>();
                            list.add(originFileName);
                            map.put(studentNum, list);

                            name = studentMapper.selectOne(new LambdaQueryWrapper<Student>()
                                            .eq(Student::getStudentNum, studentNum)
                                            .last("limit 1"))
                                    .getName();

                            paperResult.setStatus("WAIT");
                            paperResult.setStudentNum(studentNum);
                            paperResult.setRecordId(id);
                            paperResult.setResultGroupId(Long.valueOf(hashCode));
                            paperResult.setName(name);
                            paperResultMapper.insert(paperResult);

                        }else{
                            map.get(studentNum).add(originFileName);
                        }
                        PaperDetail detail = new PaperDetail();
                        detail.setUrl(originFileName);
                        detail.setGroupId((long) hashCode);
                        detail.setType(0);
                        paperDetailMapper.insert(detail);

                    } //不是答题卡 是答案or题目
                    else {
                        PaperDetail detail = new PaperDetail();
                        detail.setUrl(originFileName);
                        detail.setGroupId(id);
                        detail.setType(Integer.parseInt(pictureType));
                        paperDetailMapper.insert(detail);

                    }
                });
                record.setTotalNum(hashCodes.size());
                recordsService.updateById(record);
                return Result.success("创建成功");
            }catch (Exception e){
                return Result.fail("创建失败:"+e);
            }
//        }
        
//        return Result.success("上传成功");
    }

    @Override
    public Result<PaperDetailRespVO> getInTimePhotosById(Long paperDetailId) {


        List<Question> list = questionService.list(new LambdaQueryWrapper<Question>()
                .eq(Question::getPaperDetailId, paperDetailId));
        int indeed = 0;
        int total = 0;
        for (Question question : list) {
            indeed += question.getIndeedGrade() * 10;
            total += question.getTotalGrade() * 10;
        }
        indeed /= 10;
        total /= 10;
        PaperDetailRespVO paperDetailRespVO = PaperDetailRespVO
                .builder()
                .questions(list)
                .indeedGrade(indeed)
                .totalGrade(total)
                .build();

        return Result.success(paperDetailRespVO);
    }


}
