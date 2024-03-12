package com.howhich.fuchuang.demos.entity.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UsersDeleteReqVO {
    @ApiModelProperty(notes = "用户id")
    private Long id;
}
