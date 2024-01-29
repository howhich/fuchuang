package com.howhich.fuchuang.demos.mapper;

import com.howhich.fuchuang.demos.constant.Result;
import com.howhich.fuchuang.demos.entity.Base.Clazz;
import com.howhich.fuchuang.demos.entity.Base.Student;
import com.howhich.fuchuang.demos.entity.req.BindStudentReqVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AuthMapper {
    List<Clazz> getAllClasse(Long id);

    List<Student> getAllStudentsByClassId(Long classId);

    void bindStudentById(@Param("reqVO") BindStudentReqVO reqVO);

    Student getStudentByStudentNum(Long studentNum);
}
