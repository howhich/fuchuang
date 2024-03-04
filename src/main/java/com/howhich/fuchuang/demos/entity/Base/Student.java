package com.howhich.fuchuang.demos.entity.Base;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
public class Student extends BaseDeleteEntity{
    @ApiModelProperty(notes = "用户ID")
    @TableId(value = "id",type = IdType.AUTO)
    private Long id;
    @ApiModelProperty(notes = "老师ID")
    private Long teacherId;
    @ApiModelProperty(notes = "学号")
    private String studentNum;
    @ApiModelProperty(notes = "学生姓名")
    private String name;
    @ApiModelProperty(notes = "是否可以更改")
    private int changeable;



}
