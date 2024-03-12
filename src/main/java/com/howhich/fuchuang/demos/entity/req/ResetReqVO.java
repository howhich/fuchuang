package com.howhich.fuchuang.demos.entity.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class ResetReqVO {
    @ApiModelProperty(notes = "id列表")
    private List<Long> ids;
}
