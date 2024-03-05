package com.howhich.fuchuang.demos.service.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.howhich.fuchuang.demos.constant.Result;
import com.howhich.fuchuang.demos.entity.Base.PaperDetail;
import com.howhich.fuchuang.demos.entity.Base.PaperResult;
import com.howhich.fuchuang.demos.entity.Base.Record;
import com.howhich.fuchuang.demos.entity.req.GetStudentRecordsReqVO;
import com.howhich.fuchuang.demos.entity.req.ImportPaperResultReqVO;
import com.howhich.fuchuang.demos.entity.req.GetImportRecordsReqVO;
import com.howhich.fuchuang.demos.entity.req.ImportRecordsReqVO;
import com.howhich.fuchuang.demos.entity.resp.ImportRecordsRespVO;
import com.howhich.fuchuang.demos.entity.resp.StudentRecordsRespVO;
import com.howhich.fuchuang.demos.mapper.PaperResultMapper;
import com.howhich.fuchuang.demos.mapper.RecordMapper;
import com.howhich.fuchuang.demos.service.RecordsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class RecordsServiceImpl extends ServiceImpl<RecordMapper, Record> implements RecordsService {
    @Value("${photo.BaseURL}")
    private String BaseURL;
    @Value("${file.separator}")
    private String separator;
    @Value("${file.paperurl}")
    private String paperurl;
    @Value("${file.pictureurl}")
    private String pictureurl;
    @Autowired
    private RecordMapper recordMapper;
    @Autowired
    private PaperResultMapper paperResultMapper;
    @Override
    public Result<ImportRecordsRespVO> page(GetImportRecordsReqVO reqVO) {
        LambdaQueryWrapper queryWrapper = new LambdaQueryWrapper<Record>();
        Page<Record> page = new Page<>(reqVO.getPage(),reqVO.getPageSize());
        page = this.page(page,queryWrapper);

        PageHelper.startPage(reqVO.getPage(),reqVO.getPageSize());
        page = this.page(page,queryWrapper);

        long count = this.count(queryWrapper);

        ImportRecordsRespVO respVO = new ImportRecordsRespVO();
        respVO.setList(page.getRecords());
        respVO.setTotal(count);
        return Result.success(respVO);
    }

    @Override
    public Result<String> importSinglePhoto(MultipartFile file) throws IOException {

        FileInputStream fileInputStream = (FileInputStream) file.getInputStream();

//        String name = file.getName();//file
        String filename = file.getOriginalFilename();//qq截图2024.png

        String[] split = filename.split("\\.");
        String first = split[0];
        String last = split[split.length-1];

        String[] s = filename.split("_");
        String pictureType = s[0];

        if(pictureType == "1"){
            String studentNum = s[1];
        } else if (pictureType == "2") {
            String pageNum = s[s.length-1];
        } else {
            String pageNum = s[s.length-1];
        }

//      返回URL
        String uuid = UUID.randomUUID().toString().replace("-", "");
        String basicURL = pictureurl + first + "_" + uuid +"."+last ;
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(basicURL);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        byte[] buffer = new byte[1024];
        int len = 0;
        while((len = fileInputStream.read(buffer)) != -1){
            fileOutputStream.write(buffer, 0, len);
        }
        fileOutputStream.close();
        return Result.success(basicURL);
    }

    @Override
    public Result importBatchPhoto(List<MultipartFile> fileList) {
        String dirName = "Judge" + UUID.randomUUID().toString().replace("-","");
        String basicURL = BaseURL + dirName;
        File dir = new File(basicURL);
        dir.mkdir();
        List<Record> records = new ArrayList<>();
        fileList.forEach(file -> {
            FileInputStream fileInputStream;
            try {
                fileInputStream = (FileInputStream)file.getInputStream();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            UUID uuid = UUID.randomUUID();
            String detaiURL = uuid.toString();
            detaiURL = detaiURL.replace("-","") + ".jpg";

            Record record = new Record();
            record.setUrl(detaiURL);
            record.setRecordName(file.getOriginalFilename());
            records.add(record);

            try {
                FileOutputStream fileOutputStream = new FileOutputStream(basicURL + separator+ detaiURL);
                byte[] buffer = new byte[1024];
                int len = 0;
                while ((len = fileInputStream.read(buffer))!=-1){
                    fileOutputStream.write(buffer,0,len);
                }
                fileInputStream.close();
                fileOutputStream.close();
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        this.saveBatch(records);
        int count = records.size();
        return Result.success("完成上传,共计图片数量："+count);
    }

    @Override
    public Result importRecords(ImportRecordsReqVO reqVO) {
        List<String> fileNames = reqVO.getFileNames();
        Record record = new Record();
        record.setRecordName(reqVO.getRecordName());
        record.setStatus("JUDGING");
        this.save(record);
        Long id = this.getOne(new LambdaQueryWrapper<Record>()
                .orderByDesc(Record::getCreateTime)
                .last("limit 1")).getId();
        fileNames.forEach(fileName->{
            PaperResult paperResult = new PaperResult();

            String[] strings = fileName.split("_");
            String pictureType = strings[0];
            String studentNum = strings[1];
            String pageNum = strings[2];

            paperResult.setStatus("WAIT");
            paperResult.setStudentNum(studentNum);
            paperResult.setRecordId(id);
            paperResult.setPaperName(fileName);
            paperResultMapper.insert(paperResult);
        });

        return Result.success("创建考试成功,id:" + id);
    }

    @Override
    public Result<StudentRecordsRespVO> getStudentRecords(GetStudentRecordsReqVO reqVO) {

        return Result.success();
    }
}
