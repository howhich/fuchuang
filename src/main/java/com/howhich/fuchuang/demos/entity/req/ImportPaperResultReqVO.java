package com.howhich.fuchuang.demos.entity.req;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ImportPaperResultReqVO {
    private Long recordId;
    private MultipartFile file;
}
