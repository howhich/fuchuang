package com.howhich.fuchuang.demos.service;

import com.howhich.fuchuang.demos.constant.Result;
import com.howhich.fuchuang.demos.entity.req.UserLoginReqVO;
import com.howhich.fuchuang.demos.entity.resp.UserLoginRespVO;
import org.springframework.stereotype.Service;

public interface LoginService {
    Result<UserLoginRespVO> userLogin(UserLoginReqVO reqVO);

    Result userLogout();

    String getRoleById(Long loginId);
}
