package com.howhich.fuchuang.demos.entity.resp;

import com.howhich.fuchuang.demos.entity.Base.RecordCondition;
import com.howhich.fuchuang.demos.entity.Base.TotalCondition;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class GetTotalConditionRespVO {

    private Map<Long, RecordCondition> totalConditions;
}
