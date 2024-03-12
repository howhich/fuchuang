package com.howhich.fuchuang.demos.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.howhich.fuchuang.demos.entity.Base.Clazz;
import com.howhich.fuchuang.demos.entity.Base.Url;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UrlMapper extends BaseMapper<Url> {
}
