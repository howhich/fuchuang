package com.howhich.fuchuang.demos.entity.resp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class GetScoreRespVO {
    @ApiModelProperty(notes = "评阅")
    private List<NumAndScore> numAndScoreList;
}
