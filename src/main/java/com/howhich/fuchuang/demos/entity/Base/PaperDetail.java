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
public class PaperDetail extends BaseDeleteEntity{
    @ApiModelProperty(notes = "试卷详情id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    @ApiModelProperty(notes = "试卷组id")
    private Long groupId;
    @ApiModelProperty(notes = "题目得分")
    private float score;
    @ApiModelProperty(notes = "题目总分")
    private float totalScore;
    @ApiModelProperty(notes = "评阅")
    private String comment;
    @ApiModelProperty(notes = "题号")
    private int questionNum;
    @ApiModelProperty(notes = "图片地址")
    private String url;
    @ApiModelProperty(notes = "类型(0表示答题卡 1表示原卷 2表示参考答案 3表示原卷切割)")
    private int type;
}
