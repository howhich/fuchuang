package com.howhich.fuchuang.demos.entity.Base;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Clazz extends BaseDeleteEntity{
    @ApiModelProperty(notes = "id")
    private Long id;
    @ApiModelProperty(notes = "教师id")
    private Long teacherId;
    @ApiModelProperty(notes = "班级名称")
    private String className;
    @TableLogic(value = "0",delval = "1")
    @ApiModelProperty(value = "逻辑删除")
    @TableField(fill = FieldFill.INSERT)
    private Integer deleted;

}
