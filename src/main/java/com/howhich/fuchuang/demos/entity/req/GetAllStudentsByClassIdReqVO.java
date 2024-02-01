package com.howhich.fuchuang.demos.entity.req;

import com.howhich.fuchuang.demos.entity.Base.Student;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetAllStudentsByClassIdReqVO {
    @ApiModelProperty(notes = "id")
    private String id;
    private Integer page;
    private Integer pageSize;
}
