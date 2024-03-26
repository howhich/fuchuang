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
public class ExportRecord extends BaseDeleteEntity{

    @ApiModelProperty(notes = "学号")
    private String studentNum;
    @ApiModelProperty(notes = "姓名")
    private String name;
    @ApiModelProperty(notes = "分数")
    private Integer score;

}
