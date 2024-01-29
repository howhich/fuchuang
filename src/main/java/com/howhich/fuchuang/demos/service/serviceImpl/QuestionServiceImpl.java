package com.howhich.fuchuang.demos.service.serviceImpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.howhich.fuchuang.demos.entity.Base.PaperDetail;
import com.howhich.fuchuang.demos.entity.Base.Question;
import com.howhich.fuchuang.demos.mapper.PaperDetailMapper;
import com.howhich.fuchuang.demos.mapper.QuestionMapper;
import com.howhich.fuchuang.demos.service.PaperDetailService;
import com.howhich.fuchuang.demos.service.QuestionService;
import org.springframework.stereotype.Service;

@Service
public class QuestionServiceImpl extends ServiceImpl<QuestionMapper, Question> implements QuestionService {

}
