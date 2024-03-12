package com.howhich.fuchuang.demos.entity.resp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class ImportBatchStudentsRespVO {
    @ApiModelProperty(notes = "导入数据量")
    private Long total;
    @ApiModelProperty(notes = "导入耗时(毫秒)")
    private Long milSeconds;
}
