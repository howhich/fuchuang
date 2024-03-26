package com.howhich.fuchuang.demos.entity.Base;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RecordCondition {
    private String recordName;
    private List<Integer> questionNums;
    private List<Float> averages;
    private List<Float> maxNums;
    private Integer total;
}
