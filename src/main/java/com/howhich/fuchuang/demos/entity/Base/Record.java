package com.howhich.fuchuang.demos.entity.Base;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Record extends BaseDeleteEntity{
    @ApiModelProperty(notes = "考试id")
    @TableId(value="id",type = IdType.AUTO )
    private Long id ;
    @ApiModelProperty(notes = "考试名称")
    private String recordName;
    @ApiModelProperty(notes = "状态 JUDGING or DONE")
    private String status;
    @ApiModelProperty(notes = "总参考人数")
    private int totalNum;
}
