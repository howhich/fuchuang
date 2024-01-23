package com.howhich.fuchuang.demos.service.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.howhich.fuchuang.demos.constant.Result;
import com.howhich.fuchuang.demos.entity.Base.Record;
import com.howhich.fuchuang.demos.entity.req.ImportRecordsReqVO;
import com.howhich.fuchuang.demos.entity.resp.ImportRecordsRespVO;
import com.howhich.fuchuang.demos.mapper.RecordMapper;
import com.howhich.fuchuang.demos.service.RecordsService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class RecordsServiceImpl extends ServiceImpl<RecordMapper, Record> implements RecordsService {
    @Override
    public Result<ImportRecordsRespVO> page(ImportRecordsReqVO reqVO) {
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
    public Result importSinglePhoto(MultipartFile file) throws IOException {
        FileInputStream fileInputStream = (FileInputStream) file.getInputStream();

        UUID uuid = UUID.randomUUID();
        String detaiURL = uuid.toString();
        detaiURL = detaiURL.replace("-","") + ".jpg";

        Record record = new Record();
        record.setUrl(detaiURL);
        record.setRecordName(file.getOriginalFilename());
        this.save(record);
// TODO 不需要存到数据库里面 数据库只存图片URI即可
        String basicURL = "src/main/resources/static/";
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(basicURL + detaiURL);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        byte[] buffer = new byte[1024];
        int len = 0;
        while((len = fileInputStream.read(buffer)) != -1){
            fileOutputStream.write(buffer, 0, len);
        }
        fileOutputStream.close();
        return Result.success("完成上传");
    }

    @Override
    public Result importBatchPhotp(List<MultipartFile> fileList) {
        String basicURL = "src/main/resources/static/";
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
                FileOutputStream fileOutputStream = new FileOutputStream(basicURL + detaiURL);
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
}
