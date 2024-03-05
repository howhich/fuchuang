package com.howhich.fuchuang.demos.entity.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class ImportRecordsReqVO {
    @ApiModelProperty(notes = "文件名")
    private List<String> fileNames;//文件名
    @ApiModelProperty(notes = "考试名称")
    private String recordName;
//    private String filePath;//文件路径

}
