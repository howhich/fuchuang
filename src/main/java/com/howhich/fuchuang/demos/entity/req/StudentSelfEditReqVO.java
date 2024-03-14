package com.howhich.fuchuang.demos.entity.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class StudentSelfEditReqVO {
//    @ApiModelProperty(notes = "用户名")
//    private String username;
    @ApiModelProperty(notes = "学生学号")
    private String StudentNum;
    @ApiModelProperty(notes = "学生姓名")
    private String name;
    @ApiModelProperty(notes = "新密码")
    private String password;
    @ApiModelProperty
    private String oldPass;
//    @ApiModelProperty(notes = "id")
//    private Integer id;
}
