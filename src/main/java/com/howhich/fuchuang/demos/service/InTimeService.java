package com.howhich.fuchuang.demos.service;

import com.howhich.fuchuang.demos.constant.Result;
import com.howhich.fuchuang.demos.entity.resp.ImportRecordsRespVO;
import com.howhich.fuchuang.demos.entity.resp.PaperDetailRespVO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface InTimeService {
    Result uploadInTimePhotos(List<MultipartFile> fileList);

    Result<PaperDetailRespVO> getInTimePhotosById(Long paperDetailId);
}
