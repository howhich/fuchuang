package com.howhich.fuchuang.demos.entity.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class InTimeReqVO {
    @ApiModelProperty(notes = "文件urls")
    private List<String> urls;//文件名
//    @ApiModelProperty(notes = "考试名称")
//    private String recordName;
//    private String filePath;//文件路径

}
