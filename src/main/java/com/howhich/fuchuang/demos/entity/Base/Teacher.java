package com.howhich.fuchuang.demos.entity.Base;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.LinkedList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Teacher extends BaseEntity{
    @ApiModelProperty(notes = "用户ID")
    private Long id;
    @ApiModelProperty(notes = "学校")
    private String school;
    @ApiModelProperty(notes = "学科")
    private String subject;
    @ApiModelProperty(notes = "教师名称")
    private String name;
}
