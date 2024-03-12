package com.howhich.fuchuang.demos.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.howhich.fuchuang.demos.constant.Result;
import com.howhich.fuchuang.demos.entity.Base.Record;
import com.howhich.fuchuang.demos.entity.req.GetStudentRecordsReqVO;
import com.howhich.fuchuang.demos.entity.req.GetImportRecordsReqVO;
import com.howhich.fuchuang.demos.entity.req.ImportRecordsReqVO;
import com.howhich.fuchuang.demos.entity.resp.GetImportRecordsRespVO;
import com.howhich.fuchuang.demos.entity.resp.ImportBatchStudentsRespVO;
import com.howhich.fuchuang.demos.entity.resp.ImportRecordsRespVO;
import com.howhich.fuchuang.demos.entity.resp.StudentRecordsRespVO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface RecordsService extends IService<Record> {
    Result<GetImportRecordsRespVO> page(GetImportRecordsReqVO reqVO);

    Result<String> importSinglePhoto(MultipartFile file) throws IOException;

    Result importBatchPhoto(List<MultipartFile> fileList);

    Result importRecords(ImportRecordsReqVO reqVO);

    Result<StudentRecordsRespVO> getStudentRecords(GetStudentRecordsReqVO reqVO);

    Result<ImportBatchStudentsRespVO> importBatchStudents(MultipartFile file);
}
