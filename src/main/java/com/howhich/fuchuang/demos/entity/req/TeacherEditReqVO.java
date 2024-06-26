package com.howhich.fuchuang.demos.entity.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TeacherEditReqVO {
//    @ApiModelProperty(notes = "用户名")
//    private String username;
    @ApiModelProperty(notes = "老师姓名")
    private String name;
    @ApiModelProperty(notes = "新密码")
    private String password;
    @ApiModelProperty(notes = "旧密码")
    private String oldPass;
}
