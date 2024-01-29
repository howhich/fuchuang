package com.howhich.fuchuang.demos.entity.resp;

import com.howhich.fuchuang.demos.entity.Base.Question;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaperDetailRespVO {
    @ApiModelProperty(value = "题目列表")
    private List<Question> questions = new ArrayList<>();
    @ApiModelProperty(notes = "试卷详情id")
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
}
