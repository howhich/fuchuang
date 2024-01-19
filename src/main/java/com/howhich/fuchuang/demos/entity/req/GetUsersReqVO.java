package com.howhich.fuchuang.demos.entity.req;

import io.swagger.annotations.ApiModelProperty;

public class GetUsersReqVO {
    @ApiModelProperty(notes = "用户id")
    private Long id;
    @ApiModelProperty(notes = "账号")
    private String username;
    @ApiModelProperty(value = "角色",notes = "目前三种：USER  ADMIN")
    private String role;
    @ApiModelProperty(notes = "姓名")
    private String name;
    @ApiModelProperty(value = "YES",notes = "帐号状态 NO YES")
    private String status;
    private Integer page;
    private Integer pageSize;
}
