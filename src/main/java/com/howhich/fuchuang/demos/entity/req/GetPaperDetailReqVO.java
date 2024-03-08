package com.howhich.fuchuang.demos.entity.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class GetPaperDetailReqVO {
    @ApiModelProperty(notes = "考试recordId")
    private String recordId;
    @ApiModelProperty(notes = "学号")
    private String studentNum;
    @ApiModelProperty(notes = "题号")
    private Integer questionNum;
}
