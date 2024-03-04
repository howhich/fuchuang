package com.howhich.fuchuang.demos.entity.req;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ImportPaperResultReqVO {
    @ApiModelProperty(notes = "考试id")
    private Long recordId;
    @ApiModelProperty(notes = "文件")
    private MultipartFile file;
    @ApiModelProperty(notes = "组卷id(一个人的卷子有两三页 这几页属于一个组)")
    private Long resultGroupId;
}
