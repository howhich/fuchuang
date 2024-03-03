package com.howhich.fuchuang.demos.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.howhich.fuchuang.demos.constant.Result;
import com.howhich.fuchuang.demos.entity.Base.Record;
import com.howhich.fuchuang.demos.entity.req.ImportPaperResultReqVO;
import com.howhich.fuchuang.demos.entity.req.GetImportRecordsReqVO;
import com.howhich.fuchuang.demos.entity.req.ImportRecordsReqVO;
import com.howhich.fuchuang.demos.entity.resp.ImportRecordsRespVO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface RecordsService extends IService<Record> {
    Result<ImportRecordsRespVO> page(GetImportRecordsReqVO reqVO);

    Result<String> importSinglePhoto(ImportPaperResultReqVO reqVO) throws IOException;

    Result importBatchPhoto(List<MultipartFile> fileList);

    Result importRecords(ImportRecordsReqVO reqVO);
}
