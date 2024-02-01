package com.howhich.fuchuang.demos.entity.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class GetAllClassReqVO {
    @ApiModelProperty(notes = "班级id")
    private Long id;
    @ApiModelProperty(notes = "老师id")
    private Long teacherId;
    @ApiModelProperty(notes = "班级名称")
    private String className;
    private Integer page;
    private Integer pageSize;
}
