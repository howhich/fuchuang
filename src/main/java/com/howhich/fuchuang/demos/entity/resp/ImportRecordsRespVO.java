package com.howhich.fuchuang.demos.entity.resp;

import com.howhich.fuchuang.demos.entity.Base.Record;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;

import java.util.List;

@Data
public class ImportRecordsRespVO {
    @ApiModelProperty(notes = "试卷id列表")
    private List<Long> ids;
    @ApiModelProperty(notes = "当前考试recordId")
    private Long recordId;
}
