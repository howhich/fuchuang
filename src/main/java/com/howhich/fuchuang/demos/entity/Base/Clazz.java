package com.howhich.fuchuang.demos.entity.Base;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Clazz {
    @ApiModelProperty(notes = "id")
    private Long id;
    @ApiModelProperty(notes = "教师id")
    private Long teacherId;
    @ApiModelProperty(notes = "班级名称")
    private String className;
}
