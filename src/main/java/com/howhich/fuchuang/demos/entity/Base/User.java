package com.howhich.fuchuang.demos.entity.Base;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.howhich.fuchuang.demos.entity.Base.BaseDeleteEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User extends BaseDeleteEntity {
    @ApiModelProperty(notes = "id")
    @TableId(value = "id",type = IdType.AUTO)
    private Long id;
    @ApiModelProperty(notes = "用户名")
    private String username;
    @ApiModelProperty(notes = "密码")
    private String password;
    @ApiModelProperty(notes = "角色")
    private String role;
    @ApiModelProperty(value = "YES",notes = "账号状态")
    private String status;

}
