package com.howhich.fuchuang.demos.service.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.howhich.fuchuang.demos.constant.Result;
import com.howhich.fuchuang.demos.entity.Base.PaperDetail;
import com.howhich.fuchuang.demos.entity.Base.PaperResult;
import com.howhich.fuchuang.demos.entity.Base.Record;
import com.howhich.fuchuang.demos.entity.req.ImportPaperResultReqVO;
import com.howhich.fuchuang.demos.entity.req.GetImportRecordsReqVO;
import com.howhich.fuchuang.demos.entity.req.ImportRecordsReqVO;
import com.howhich.fuchuang.demos.entity.resp.ImportRecordsRespVO;
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

        long count = this.count();

        ImportRecordsRespVO respVO = new ImportRecordsRespVO();
        respVO.setList(page.getRecords());
        respVO.setTotal(count);
        return Result.success(respVO);
    }

    @Override
    public Result<String> importSinglePhoto(ImportPaperResultReqVO reqVO) throws IOException {

        MultipartFile file =  reqVO.getFile();
        FileInputStream fileInputStream = (FileInputStream) file.getInputStream();

        String  detailURL = String.valueOf(reqVO.getRecordId());
        detailURL = detailURL.replace("-","") + ".jpg";


        String filename = file.getOriginalFilename();

        PaperResult paperResult = new PaperResult();
        paperResult.setRecordId(reqVO.getRecordId());
        paperResult.setPaperName(filename);
//        paperResult.setStatus();

        paperResultMapper.insert(paperResult);
//      返回URL
        String basicURL = paperurl;
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(basicURL + detailURL);
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
        String fileName = reqVO.getFileName();
        Record record = new Record();
        record.setRecordName(fileName);
        record.setStatus("JUDGING");
        this.save(record);
        return Result.success("创建考试成功");
    }
}
