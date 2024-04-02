package com.howhich.fuchuang.demos.service.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.howhich.fuchuang.demos.config.thread.AsyncExecutor;
import com.howhich.fuchuang.demos.constant.Result;
import com.howhich.fuchuang.demos.entity.Base.*;
import com.howhich.fuchuang.demos.entity.req.InTimeReqVO;
import com.howhich.fuchuang.demos.entity.resp.InTimeRespVO;
import com.howhich.fuchuang.demos.entity.resp.PaperDetailRespVO;
import com.howhich.fuchuang.demos.init.FakeEvent;
import com.howhich.fuchuang.demos.mapper.PaperDetailMapper;
import com.howhich.fuchuang.demos.mapper.PaperResultMapper;
import com.howhich.fuchuang.demos.mapper.RecordMapper;
import com.howhich.fuchuang.demos.mapper.StudentMapper;
import com.howhich.fuchuang.demos.service.*;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.context.support.WebApplicationObjectSupport;
import org.springframework.web.jsf.el.WebApplicationContextFacesELResolver;

import javax.annotation.Resource;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class InTimeServiceImpl extends WebApplicationObjectSupport implements InTimeService {
    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private RecordsService recordsService;
    @Resource
    private StudentMapper studentMapper;
    @Resource
    private PaperResultMapper paperResultMapper;
    @Resource
    private PaperDetailMapper paperDetailMapper;
    @Resource
    private RecordMapper recordMapper;
    @Value("${photo.BaseURL}")
    private String BaseURL;
//    @Autowired
//    private RedisTemplate redisTemplate;

    @Override
    public Result<InTimeRespVO> uploadInTimePhotos(InTimeReqVO reqVO) {
        Long id = 9L;
//        recordMapper.getDeletedOne(id);
        InTimeRespVO respVO = new InTimeRespVO();
        respVO.setGroupId(id);
        Record record = recordMapper.selectOne(new LambdaQueryWrapper<Record>()
                .eq(Record::getId, id));
        record.setRecordName("英语测试3");
        recordMapper.updateById(record);
        return Result.success(respVO);

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

        /*
        true
         */
//            List<Integer> hashCodes = new ArrayList<>();
//            Map<String,List<String>> map = new HashMap();
//            try{
//                List<String> fileNames = reqVO.getUrls();
//                Record record = new Record();
//                record.setRecordName("实时阅卷" + System.currentTimeMillis());
//                //TODO Mark一下
//                record.setStatus("DONE");
//
//                recordsService.save(record);
//                Long id = record.getId();
//                //todo 改了的
////                Long id = recordsService.getOne(new LambdaQueryWrapper<Record>()
////                        .orderByDesc(Record::getCreateTime)
////                        .last("limit 1")).getId();
////                record.setId(record.getId());
//                fileNames.forEach(fileName->{
////                "/www/wwwroot/picture/"
//                    int lastIndex = fileName.lastIndexOf("/");
//                    String originFileName = fileName;
//                    fileName = fileName.substring(lastIndex+1);
//                    PaperResult paperResult = new PaperResult();
//
//                    String[] strings = fileName.split("_");
//                    String pictureType = strings[0];
//                    String studentNum = strings[1];
//                    String pageNum = strings[2];
//
//
//                    String name = "empty";
//                    if(pictureType.equals("0")){
//                        //是卷子
//                        int hashCode = Math.abs((id + studentNum).hashCode());
//
//                        if(!hashCodes.contains(hashCode)){
//                            hashCodes.add(hashCode);
//                        }
//
//                        if(!map.containsKey(studentNum)){
//
//                            List<String> list = new ArrayList<>();
//                            list.add(originFileName);
//                            map.put(studentNum, list);
//
//                            name = studentMapper.selectOne(new LambdaQueryWrapper<Student>()
//                                            .eq(Student::getStudentNum, studentNum)
//                                            .last("limit 1"))
//                                    .getName();
//
//                            paperResult.setStatus("WAIT");
//                            paperResult.setStudentNum(studentNum);
//                            paperResult.setRecordId(id);
//                            paperResult.setResultGroupId(Long.valueOf(hashCode));
//                            paperResult.setName(name);
//                            paperResultMapper.insert(paperResult);
//
//                        }else{
//                            map.get(studentNum).add(originFileName);
//                        }
//                        PaperDetail detail = new PaperDetail();
//                        detail.setUrl(originFileName);
//                        detail.setGroupId((long) hashCode);
//                        detail.setType(0);
//                        paperDetailMapper.insert(detail);
//
//                    } //不是答题卡 是答案or题目
//                    else {
//                        PaperDetail detail = new PaperDetail();
//                        detail.setUrl(originFileName);
//                        detail.setGroupId(id);
//                        detail.setType(Integer.parseInt(pictureType));
//                        paperDetailMapper.insert(detail);
//                    }
//                });
//                record.setTotalNum(hashCodes.size());
//                recordsService.updateById(record);
//                return Result.success(record.getId());
//            }catch (Exception e){
//                return Result.fail("创建失败:"+e);
//            }
////        }
        
//        return Result.success("上传成功");
    }



    @Override
    public Result getInTimeResult(Long groupId) {
        ApplicationContext context = getApplicationContext();
        //todo FAKE
        if(ObjectUtils.isEmpty(redisTemplate.opsForValue().get("Counter" + groupId))){
            //缓存一分钟
            redisTemplate.opsForValue().set("Counter"+groupId,0, Duration.ofMinutes(1));
            return Result.fail("正在阅卷，请等待");
        }
//        String status = paperResultMapper.selectOne(new LambdaQueryWrapper<PaperResult>()
//                .eq(PaperResult::getResultGroupId, groupId).last("limit 1")).getStatus();
//        if(status.equals("WAIT")){
//            return Result.fail("正在阅卷，请等待");
//        }
//        recordMapper.selectOne(new LambdaQueryWrapper<Record>()
//                .eq(Record::getId, groupId));
//        Record record = recordMapper.selectMyRecord(groupId);
//        record.setStatus("DONE");
//        recordMapper.update(record, new LambdaQueryWrapper<Record>()
//                .eq(Record::getId, groupId));
        Record record = recordMapper.selectOne(new LambdaQueryWrapper<Record>()
                .eq(Record::getId, groupId));
        record.setStatus("DONE");
//        record.setRecordName("英语测试3");
        recordMapper.updateById(record);
        AsyncExecutor executor = AsyncExecutor.getExecutor();
        executor.schedule(new Thread(()->{
            context.publishEvent(new FakeEvent(""));
        }));

        return Result.success("评阅完成");
    }


}
