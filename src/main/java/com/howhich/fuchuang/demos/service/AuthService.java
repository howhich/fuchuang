package com.howhich.fuchuang.demos.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.howhich.fuchuang.demos.constant.Result;
import com.howhich.fuchuang.demos.entity.User;
import com.howhich.fuchuang.demos.entity.req.GetUsersReqVO;
import com.howhich.fuchuang.demos.entity.resp.GetUsersRespVO;

public interface AuthService extends IService<User> {
    Result<GetUsersRespVO> getUsers(GetUsersReqVO reqVO);
}
