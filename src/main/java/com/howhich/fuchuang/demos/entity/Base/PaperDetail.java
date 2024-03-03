package com.howhich.fuchuang.demos.entity.Base;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PaperDetail {
    @ApiModelProperty(notes = "试卷详情id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    @ApiModelProperty(notes = "试卷记录id")
    private Long paperResultId;
    @ApiModelProperty(notes = "试卷总分")
    private float totalGrade;
    @ApiModelProperty(notes = "实际得分")
    private float indeedGrade;
    @ApiModelProperty(notes = "试卷主人")
    private String paperOwner;
    @ApiModelProperty(notes = "创建时间")
    private String createTime;
    @ApiModelProperty(notes = "图片地址")
    private String url;
}
