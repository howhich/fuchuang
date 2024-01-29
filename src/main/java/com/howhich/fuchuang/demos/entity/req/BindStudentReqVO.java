package com.howhich.fuchuang.demos.entity.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
public class BindStudentReqVO {
    @ApiModelProperty(notes = "班级ID")
    private Long classId;
    @ApiModelProperty(notes = "学号列表")
    private String studentNum;
}
