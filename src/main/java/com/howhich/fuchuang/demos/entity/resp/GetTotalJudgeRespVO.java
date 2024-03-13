package com.howhich.fuchuang.demos.entity.resp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class GetTotalJudgeRespVO {
    @ApiModelProperty(value = "原卷urls")
    private List<String> originalPhotoUrls;
    @ApiModelProperty(value = "答案urls")
    private List<String> answerUrls;
    @ApiModelProperty(value = "答题卡urls")
    private List<String> answerCardUrls;
}
