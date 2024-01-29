package com.howhich.fuchuang.demos.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.howhich.fuchuang.demos.entity.Base.PaperDetail;
import com.howhich.fuchuang.demos.entity.Base.Student;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface PaperDetailMapper extends BaseMapper<PaperDetail> {
    void savePhoto(@Param(value = "url") String url, @Param(value = "detailId")Long detailId);
}
