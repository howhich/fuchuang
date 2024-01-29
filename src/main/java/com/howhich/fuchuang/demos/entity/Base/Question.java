package com.howhich.fuchuang.demos.entity.Base;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Question {
    @ApiModelProperty(notes = "题目id")
    private Long id;
    @ApiModelProperty(notes = "试卷id")
    private Long paperDetailId;
    @ApiModelProperty(notes = "本题实际得分")
    private Double indeedGrade;
    @ApiModelProperty(notes = "本题总分")
    private Double totalGrade;
    @ApiModelProperty(notes = "评阅")
    private String judging;
//    @ApiModelProperty(notes = "URL")
//    private String url;
}
