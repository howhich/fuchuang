package com.howhich.fuchuang.demos.entity.Base;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class BaseDeleteEntity extends BaseEntity{
    @TableLogic(value = "1",delval = "0")
    @ApiModelProperty(value = "逻辑删除")
    @TableField(fill = FieldFill.INSERT)
    private Integer deleted = 1;
}
