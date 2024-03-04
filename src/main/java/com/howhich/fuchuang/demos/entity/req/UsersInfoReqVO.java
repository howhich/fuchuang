package com.howhich.fuchuang.demos.entity.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UsersInfoReqVO {
    @ApiModelProperty(notes = "用户id")
    private Long id;
    @ApiModelProperty(notes = "账号")
    private String username;
    @ApiModelProperty(value = "角色",notes = "目前2种：USER  TEACHER")
    private String role;
    @ApiModelProperty(notes = "姓名")
    private String name;
    @ApiModelProperty(value = "YES",notes = "帐号状态 NO YES")
    private String status;
    private Integer page;
    private Integer pageSize;
}
