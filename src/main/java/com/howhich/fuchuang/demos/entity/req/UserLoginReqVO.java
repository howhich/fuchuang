package com.howhich.fuchuang.demos.entity.req;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;

@Data
public class UserLoginReqVO {
    @ApiModelProperty(notes = "用户名")
    private String username;
    @ApiModelProperty(notes = "密码")
    private String password;
    @ApiModelProperty(notes = "记住我模式？YES or NO")
    private String rememberMe;
    @ApiModelProperty(notes = "角色")
    private String role;

    public  @interface login{}
}
