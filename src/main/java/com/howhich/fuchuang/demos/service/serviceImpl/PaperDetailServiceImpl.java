package com.howhich.fuchuang.demos.service.serviceImpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.howhich.fuchuang.demos.entity.Base.PaperDetail;
import com.howhich.fuchuang.demos.entity.Base.Student;
import com.howhich.fuchuang.demos.mapper.PaperDetailMapper;
import com.howhich.fuchuang.demos.mapper.StudentMapper;
import com.howhich.fuchuang.demos.service.PaperDetailService;
import com.howhich.fuchuang.demos.service.StudentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class PaperDetailServiceImpl extends ServiceImpl<PaperDetailMapper, PaperDetail> implements PaperDetailService {

}
