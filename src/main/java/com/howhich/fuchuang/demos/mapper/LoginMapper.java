package com.howhich.fuchuang.demos.mapper;

import com.howhich.fuchuang.demos.entity.Base.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LoginMapper {

    User getUserByUserName(String username);

    String getRoleById(Long loginId);
}
