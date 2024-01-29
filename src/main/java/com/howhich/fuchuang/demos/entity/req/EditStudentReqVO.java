package com.howhich.fuchuang.demos.entity.req;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EditStudentReqVO {
//    @ApiModelProperty(notes = "用户名")
//    private String username;
//    @ApiModelProperty(notes = "学生学号")
//    private String StudentNum;
    @ApiModelProperty(notes = "学生姓名")
    private String name;
    @ApiModelProperty(notes = "密码")
    private String password;
}
