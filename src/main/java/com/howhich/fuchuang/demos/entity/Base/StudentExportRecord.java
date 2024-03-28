package com.howhich.fuchuang.demos.entity.Base;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudentExportRecord {

    @ApiModelProperty(notes = "考试名称")
    private String recordName;
    @ApiModelProperty(notes = "分数")
    private Integer score;

}
