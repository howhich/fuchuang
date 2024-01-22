package com.howhich.fuchuang.demos.entity.resp;

import com.howhich.fuchuang.demos.entity.Base.BaseDeleteEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class User extends BaseDeleteEntity {
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


}
