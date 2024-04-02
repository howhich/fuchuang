package com.howhich.fuchuang.demos.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.howhich.fuchuang.demos.entity.Base.Record;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface RecordMapper extends BaseMapper<Record> {
    Record getDeletedOne(Long id);

    void setJAD(Long id);

    Record selectMyRecord(Long groupId);

    void updateMy(@Param(value = "id") Long id);
}
