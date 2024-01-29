package com.howhich.fuchuang.demos.entity.resp;

import com.howhich.fuchuang.demos.entity.Base.Clazz;
import com.howhich.fuchuang.demos.entity.Base.Student;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetAllStudentsByClassIdRespVO {
    private List<Student> studentList;
    private Integer total;
}
