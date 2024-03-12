package com.howhich.fuchuang.demos.service.serviceImpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.howhich.fuchuang.demos.entity.Base.Teacher;
import com.howhich.fuchuang.demos.entity.Base.Url;
import com.howhich.fuchuang.demos.mapper.TeacherMapper;
import com.howhich.fuchuang.demos.mapper.UrlMapper;
import com.howhich.fuchuang.demos.service.TeacherService;
import com.howhich.fuchuang.demos.service.UrlService;
import org.springframework.stereotype.Service;

@Service
public class UrlServiceImpl extends ServiceImpl<UrlMapper, Url> implements UrlService {

}
