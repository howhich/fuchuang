package com.howhich.fuchuang.demos.entity.Base;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaperResult {
    @ApiModelProperty(notes = "id")
    private Long id;
    @ApiModelProperty(notes = "导入记录id")
    private Long recordId;
    @ApiModelProperty(notes = "状态")
    private String status;
    @ApiModelProperty(notes = "(定位试卷名称)")
    private String paperName;
    @ApiModelProperty(notes = "创建时间")
    private String createTime;
    private String deleted;
}
