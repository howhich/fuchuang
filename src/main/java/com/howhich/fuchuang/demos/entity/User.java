package com.howhich.fuchuang.demos.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class User {
    @ApiModelProperty(notes = "id")
    private Long id;
    @ApiModelProperty(notes = "用户名")
    private String username;
    @ApiModelProperty(notes = "密码")
    private String password;
    @ApiModelProperty(notes = "电话")
    private String phone;
    @ApiModelProperty(notes = "角色")
    private String role;
    @ApiModelProperty(notes = "姓名")
    private String name;
    @ApiModelProperty(notes = "年龄")
    private Integer age;
    @ApiModelProperty(notes = "性别")
    private String sex;
    @ApiModelProperty(value = "YES",notes = "账号状态")
    private String status;
    @ApiModelProperty(notes = "创建时间")
    private String createTime;
    @ApiModelProperty(notes = "创建人")
    private String createUser;
    @ApiModelProperty(notes = "更新时间")
    private String updateTime;
    @ApiModelProperty(notes = "更新人")
    private String updateUser;
}