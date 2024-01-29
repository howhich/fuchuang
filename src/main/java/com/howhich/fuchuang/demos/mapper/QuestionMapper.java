package com.howhich.fuchuang.demos.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.howhich.fuchuang.demos.entity.Base.PaperDetail;
import com.howhich.fuchuang.demos.entity.Base.Question;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface QuestionMapper extends BaseMapper<Question> {
}
