package com.howhich.fuchuang.demos.service.serviceImpl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.howhich.fuchuang.demos.Utils.exception.AssertUtils;
import com.howhich.fuchuang.demos.Utils.exception.ExceptionsEnums;
import com.howhich.fuchuang.demos.constant.Result;
import com.howhich.fuchuang.demos.entity.Base.*;
import com.howhich.fuchuang.demos.entity.listener.ImportBatchStudentsListener;
import com.howhich.fuchuang.demos.entity.req.GetStudentRecordsReqVO;
import com.howhich.fuchuang.demos.entity.req.GetImportRecordsReqVO;
import com.howhich.fuchuang.demos.entity.req.ImportRecordsReqVO;
import com.howhich.fuchuang.demos.entity.resp.GetImportRecordsRespVO;
import com.howhich.fuchuang.demos.entity.resp.ImportBatchStudentsRespVO;
import com.howhich.fuchuang.demos.entity.resp.StudentRecordsRespVO;
import com.howhich.fuchuang.demos.mapper.PaperDetailMapper;
import com.howhich.fuchuang.demos.mapper.PaperResultMapper;
import com.howhich.fuchuang.demos.mapper.RecordMapper;
import com.howhich.fuchuang.demos.mapper.StudentMapper;
import com.howhich.fuchuang.demos.service.RecordsService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.*;

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
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private StudentMapper studentMapper;
    @Autowired
    private UrlService urlService;
    @Autowired
    private PaperDetailMapper paperDetailMapper;
    List<Long> ids = new ArrayList<>();

    @Override
    public Result<GetImportRecordsRespVO> page(GetImportRecordsReqVO reqVO) {
        LambdaQueryWrapper queryWrapper = new LambdaQueryWrapper<Record>()
                .orderByDesc(Record::getCreateTime);
        Page<Record> page = new Page<>(reqVO.getPage(),reqVO.getPageSize());
        page = this.page(page,queryWrapper);

        PageHelper.startPage(reqVO.getPage(),reqVO.getPageSize());
        page = this.page(page,queryWrapper);

        long count = this.count(queryWrapper);

        GetImportRecordsRespVO respVO = new GetImportRecordsRespVO();
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
//            record.setUrl(detaiURL);
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
        redisTemplate.opsForValue().set("man","kobe");
        return Result.success("完成上传,共计图片数量："+count);
    }

    @Override
    public Result importRecords(ImportRecordsReqVO reqVO) {
        List<Integer> hashCodes = new ArrayList<>();
        Map<String,List<String>> map = new HashMap();
        try{
            List<String> fileNames = reqVO.getUrls();
            Record record = new Record();
            record.setRecordName(reqVO.getRecordName());
            record.setStatus("JUDGING");

            this.save(record);
            Long id = this.getOne(new LambdaQueryWrapper<Record>()
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

//                        Url url = new Url();
//                        url.setUrl(originFileName);
//                        url.setGroupId((long) hashCode);
//                        url.setType(0);
//                        urlService.save(url);
                } //不是答题卡 是答案or题目
                else {
//                        Url url = new Url();
//                        url.setUrl(originFileName);
//                        url.setGroupId(id);
//                        url.setType(Integer.parseInt(pictureType));
//                        urlService.save(url);
                    PaperDetail detail = new PaperDetail();
                    detail.setUrl(originFileName);
                    detail.setGroupId(id);
                    detail.setType(Integer.parseInt(pictureType));
                    paperDetailMapper.insert(detail);

                }
            });
                record.setTotalNum(hashCodes.size());
                this.updateById(record);
            return Result.success("创建成功");
        }catch (Exception e){
            return Result.fail("创建失败:"+e);
        }
    }

    @Override
    public Result<StudentRecordsRespVO> getStudentRecords(GetStudentRecordsReqVO reqVO) {

        return Result.success();
    }

    @Override
//    @Transactional
    public Result<ImportBatchStudentsRespVO> importBatchStudents(MultipartFile file) {
        checkFile(file);

        ImportBatchStudentsListener listener = new ImportBatchStudentsListener();
        try {
            System.out.println("准备导入学生");
            EasyExcel.read(file.getInputStream(), StudentInfo.class, listener).sheet().doRead();

        } catch (Exception ie) {
            ie.printStackTrace();
            AssertUtils.throwException(ExceptionsEnums.File.IMPORT_FAIL);
        }
        ImportBatchStudentsRespVO respVO = new ImportBatchStudentsRespVO();
        respVO.setTotal(listener.getDataNum());
        respVO.setMilSeconds(listener.getTime());
        return Result.success(respVO);
    }

    private void checkFile(MultipartFile file) {
        AssertUtils.isFalse(ObjectUtils.isNotEmpty(file), ExceptionsEnums.File.EMPTY_FILE);
        String type = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
        AssertUtils.isFalse("xlsx".equals(type) || "xls".equals(type), ExceptionsEnums.File.TYPE_NOT_ALLOWED);

    }



}
