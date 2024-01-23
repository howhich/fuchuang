package com.howhich.fuchuang.demos.entity.resp;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UserLoginRespVO {
    @ApiModelProperty(notes = "用户名")
    private String username;
    @ApiModelProperty(notes = "id")
    private Long id;
    @ApiModelProperty(notes = "角色")
    private String role;
//    @ApiModelProperty(notes = "密码")
//    private String password;
    @ApiModelProperty(notes = "token")
    private String Authorization;
}
