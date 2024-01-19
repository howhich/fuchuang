package com.howhich.fuchuang.demos.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.howhich.fuchuang.demos.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UsersInfoMapper extends BaseMapper<User> {
}
