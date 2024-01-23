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
public class Record extends BaseDeleteEntity{
    @ApiModelProperty(notes = "id")
    private Long id ;
    @ApiModelProperty(notes = "试卷名称")
    private String recordName;
    @ApiModelProperty(notes = "状态 JUDGING or FINISH")
    private String status;
    @ApiModelProperty(notes = "附件")
    private String url;
}
