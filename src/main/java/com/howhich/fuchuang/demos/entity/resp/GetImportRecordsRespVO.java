package com.howhich.fuchuang.demos.entity.resp;

import com.howhich.fuchuang.demos.entity.Base.Record;
import lombok.Data;

import java.util.List;

@Data
public class GetImportRecordsRespVO {
    private List<Record> list;
    private Long total;
}
