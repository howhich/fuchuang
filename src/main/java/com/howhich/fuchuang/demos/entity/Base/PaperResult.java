package com.howhich.fuchuang.demos.entity.Base;

import io.swagger.annotations.Api;
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
public class PaperResult extends BaseDeleteEntity{
    @ApiModelProperty(notes = "id")
    private Long id;
    @ApiModelProperty(notes = "导入记录id")
    private Long recordId;
    @ApiModelProperty(notes = "状态")
    private String status;
    @ApiModelProperty(notes = "(定位试卷名称)")
    private String paperName;
    @ApiModelProperty(notes = "分组id(两张卷子图片属于某一个人)")
    private Long resultGroupId;
    @ApiModelProperty(notes = "学号")
    private String studentNum;

}
