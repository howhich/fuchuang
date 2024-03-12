package com.howhich.fuchuang.demos.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.howhich.fuchuang.demos.entity.Base.Record;
import com.howhich.fuchuang.demos.entity.Base.Student;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface StudentMapper extends BaseMapper<Student> {

    void importStudnet(@Param("fileName") String fileName);
}
