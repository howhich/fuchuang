package com.howhich.fuchuang.demos.entity.resp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UpdatePaperDetailReqVO {
    @ApiModelProperty(notes = "评阅")
    private String comment;
    @ApiModelProperty(notes = "分数")
    private float score;
    @ApiModelProperty(notes = "题号")
    private int questionNum;
    @ApiModelProperty(notes = "组id")
    private Long groupId;
}
