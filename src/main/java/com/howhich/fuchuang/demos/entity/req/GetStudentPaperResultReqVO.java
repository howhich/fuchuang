package com.howhich.fuchuang.demos.entity.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class GetStudentPaperResultReqVO {
    @ApiModelProperty(notes = "id")
    private String id;
    private Integer page;
    private Integer pageSize;
}
