package com.howhich.fuchuang.demos.service.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.howhich.fuchuang.demos.constant.Result;
import com.howhich.fuchuang.demos.entity.User;
import com.howhich.fuchuang.demos.entity.req.GetUsersReqVO;
import com.howhich.fuchuang.demos.entity.resp.GetUsersRespVO;
import com.howhich.fuchuang.demos.mapper.AuthMapper;
import com.howhich.fuchuang.demos.mapper.UsersInfoMapper;
import com.howhich.fuchuang.demos.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AuthServiceImpl extends ServiceImpl<UsersInfoMapper, User> implements AuthService {

    @Autowired
    private AuthMapper authMapper;
    @Override
    public Result<GetUsersRespVO> getUsers(GetUsersReqVO reqVO) {
        GetUsersRespVO respVO = new GetUsersRespVO();
        List<User> list = new ArrayList<>();
        LambdaQueryWrapper lambdaQueryWrapper = new LambdaQueryWrapper<User>();
        Page page = this.page(new Page<>(), lambdaQueryWrapper);
//        list = authMapper.getUsersList(reqVO);
        return null;
    }
}
