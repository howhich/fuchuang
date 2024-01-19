package com.howhich.fuchuang.demos.entity.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UserLoginReqVO {
    @ApiModelProperty(notes = "用户名")
    private String username;
    @ApiModelProperty(notes = "密码")
    private String password;
    public  @interface login{}
}
