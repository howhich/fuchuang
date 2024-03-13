package com.howhich.fuchuang.demos.entity.Base;

import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
//@TableName("talent_info")
public class StudentInfo {
    @ApiModelProperty(notes = "姓名")
    @ExcelProperty(value = "姓名", index = 0)
    private String name;

    @ApiModelProperty(notes = "学号")
    @ExcelProperty(value = "学号", index = 1)
    private String studentNum;
}
