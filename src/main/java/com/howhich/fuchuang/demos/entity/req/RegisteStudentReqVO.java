package com.howhich.fuchuang.demos.entity.req;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;

@Data
public class RegisteStudentReqVO {
    @ApiModelProperty(notes = "用户名")
    private String username;
    @ApiModelProperty(notes = "学生学号")
    private String StudentNum;
    @ApiModelProperty(notes = "学生姓名")
    private String name;
    @ApiModelProperty(notes = "初始密码")
    private String password;
}
