package com.howhich.fuchuang.demos.entity.resp;

import com.howhich.fuchuang.demos.entity.Base.StudentRecord;
import lombok.Data;

import java.util.List;

@Data
public class GetStudentRecordsRespVO {
    private List<StudentRecord> list;
    private Long total;
}
