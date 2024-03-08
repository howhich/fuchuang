package com.howhich.fuchuang.demos.entity.resp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class GetPaperVisualizationRespVO {
    @ApiModelProperty(notes = "评阅")
    private String comment;
    @ApiModelProperty(notes = "分数")
    private Integer score;
    @ApiModelProperty(notes = "urls")
    private List<String> urls;

}
