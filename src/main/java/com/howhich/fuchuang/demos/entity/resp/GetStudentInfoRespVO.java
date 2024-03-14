package com.howhich.fuchuang.demos.entity.resp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetStudentInfoRespVO {
    @ApiModelProperty(notes = "学生学号")
    private String StudentNum;
    @ApiModelProperty(notes = "学生姓名")
    private String name;
    @ApiModelProperty(notes = "初始密码")
    private String password;
//    @ApiModelProperty(notes = "是否可更改（1可以 0不可以）")
//    private Integer changeable;
}
