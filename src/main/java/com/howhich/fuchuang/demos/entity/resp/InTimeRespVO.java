package com.howhich.fuchuang.demos.entity.resp;

import com.howhich.fuchuang.demos.entity.Base.User;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class InTimeRespVO {
    @ApiModelProperty(value = "试卷组id")
    private Long groupId;
}
