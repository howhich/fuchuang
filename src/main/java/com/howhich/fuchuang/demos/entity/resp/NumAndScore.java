package com.howhich.fuchuang.demos.entity.resp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class NumAndScore {
    @ApiModelProperty(notes = "题号")
    private Integer questionNum;
    @ApiModelProperty(notes = "得分")
    private Float score;
}
