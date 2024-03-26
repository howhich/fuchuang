package com.howhich.fuchuang.demos.entity.Base;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ExportEntity extends BaseDeleteEntity{
    @ApiModelProperty(notes = "学号")
    private String studentNum;
    @ApiModelProperty(notes = "姓名")
    private String name;
    @ApiModelProperty(notes = "分数")
    private Integer score;
    @ApiModelProperty(notes = "Base64")
    private Map<String,String> encode;

}
