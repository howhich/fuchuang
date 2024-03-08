package com.howhich.fuchuang.demos.entity.resp;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class GetPaperDetailRespVO {
    @ApiModelProperty(notes = "评阅")
    private String comment;
    @ApiModelProperty(notes = "分数")
    private float score;
    @ApiModelProperty(notes = "url")
    private String url;

}
