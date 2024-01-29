package com.howhich.fuchuang.demos.service.serviceImpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.howhich.fuchuang.demos.entity.Base.Clazz;
import com.howhich.fuchuang.demos.entity.Base.Student;
import com.howhich.fuchuang.demos.mapper.ClassMapper;
import com.howhich.fuchuang.demos.mapper.StudentMapper;
import com.howhich.fuchuang.demos.service.ClassService;
import com.howhich.fuchuang.demos.service.StudentService;
import org.springframework.stereotype.Service;

@Service
public class ClassServiceImpl extends ServiceImpl<ClassMapper, Clazz> implements ClassService {

}
