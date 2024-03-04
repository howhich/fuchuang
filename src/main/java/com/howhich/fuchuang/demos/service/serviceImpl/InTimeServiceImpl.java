package com.howhich.fuchuang.demos.service.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.howhich.fuchuang.demos.Utils.exception.DateUtil;
import com.howhich.fuchuang.demos.Utils.exception.TimeUtil;
import com.howhich.fuchuang.demos.constant.Result;
import com.howhich.fuchuang.demos.constant.StatusType;
import com.howhich.fuchuang.demos.entity.Base.PaperDetail;
import com.howhich.fuchuang.demos.entity.Base.PaperResult;
import com.howhich.fuchuang.demos.entity.Base.Question;
import com.howhich.fuchuang.demos.entity.Base.Record;
import com.howhich.fuchuang.demos.entity.resp.ImportRecordsRespVO;
import com.howhich.fuchuang.demos.entity.resp.PaperDetailRespVO;
import com.howhich.fuchuang.demos.mapper.PaperDetailMapper;
import com.howhich.fuchuang.demos.service.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class InTimeServiceImpl implements InTimeService {
    @Resource
    private PaperDetailService paperDetailService;
    @Resource
    private PaperResultService paperResultService;
    @Resource
    private RecordsService recordsService;
    @Resource
    private PaperDetailMapper paperDetailMapper;
    @Resource
    private QuestionService questionService;
    @Value("${photo.BaseURL}")
    private String BaseURL;
    @Override
    public Result uploadInTimePhotos(List<MultipartFile> fileList) {
        Record record = new Record();
        UUID uuidForRecord = UUID.randomUUID();

        record.setRecordName("即时评阅:" + uuidForRecord);
        record.setStatus(StatusType.JUDGING.code);
        String recordURL1 = BaseURL + (uuidForRecord);
        String recordURL = recordURL1.replace("-","");

        File parentFile = new File(recordURL);
        parentFile.mkdir();

        record.setUrl(recordURL);
        recordsService.save(record);
        Record oneRecord = recordsService.getOne(new LambdaQueryWrapper<Record>()
                .orderByDesc(Record::getCreateTime).last("limit 1"));
        Long id = oneRecord.getId();

        //对PaperResult处理

        PaperResult paperResult = new PaperResult();
        paperResult.setRecordId(id);
        paperResult.setPaperName("实时评阅:"+uuidForRecord);
//        paperResult.setCreateTime(DateUtil.getTimeWithSec());
        paperResultService.save(paperResult);
        PaperResult result = paperResultService.getOne(new LambdaQueryWrapper<PaperResult>()
                .orderByDesc(PaperResult::getCreateTime).last("limit 1"));
        Long resultId = result.getId();


        //对PaperDetail处理
        PaperDetail paperDetail = new PaperDetail();
        paperDetail.setPaperResultId(resultId);
        paperDetailService.save(paperDetail);
        PaperDetail detail = paperDetailService.getOne(new LambdaQueryWrapper<PaperDetail>()
                .orderByDesc(PaperDetail::getCreateTime).last("limit 1"));
        Long detailId = detail.getId();

        fileList.forEach(file -> {
//            UUID uuid = UUID.randomUUID();
            String filename = file.getOriginalFilename();
            String URL = recordURL + "\\" + filename;
            paperDetailMapper.savePhoto(URL,detailId);
            try {
                FileInputStream fileInputStream =(FileInputStream) file.getInputStream();
                FileOutputStream fileOutputStream = new FileOutputStream(URL);
                byte[] buffer = new byte[1024];
                int len = 0;
                while ((len = fileInputStream.read(buffer))!=-1){
                    fileOutputStream.write(buffer,0,len);
                }
                fileInputStream.close();
                fileOutputStream.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        return Result.success("处理完成");
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
