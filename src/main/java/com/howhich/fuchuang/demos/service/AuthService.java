package com.howhich.fuchuang.demos.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.howhich.fuchuang.demos.constant.Result;
import com.howhich.fuchuang.demos.entity.req.UsersInfoParam;
import com.howhich.fuchuang.demos.entity.resp.GetUsersRespVO;
import com.howhich.fuchuang.demos.entity.Base.User;

import java.util.List;

public interface AuthService extends IService<User> {

    Result<GetUsersRespVO> page(UsersInfoParam usersInfoParam);

    Result delete(List<UsersInfoParam> usersInfoParamList);

    Result add(User user);


    Result edit(User user);

    Result resetUsers(List<UsersInfoParam> usersInfoParamList);

    Result editSelf(User user);

    Result registry(User user);
}
