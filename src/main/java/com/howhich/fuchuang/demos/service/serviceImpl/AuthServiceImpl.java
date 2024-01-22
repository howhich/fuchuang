package com.howhich.fuchuang.demos.service.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.howhich.fuchuang.demos.constant.Result;
import com.howhich.fuchuang.demos.constant.UserStatus;
import com.howhich.fuchuang.demos.entity.req.GetUsersReqVO;
import com.howhich.fuchuang.demos.entity.req.UsersInfoParam;
import com.howhich.fuchuang.demos.entity.resp.GetUsersRespVO;
import com.howhich.fuchuang.demos.entity.resp.User;
import com.howhich.fuchuang.demos.mapper.AuthMapper;
import com.howhich.fuchuang.demos.mapper.UsersInfoMapper;
import com.howhich.fuchuang.demos.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class AuthServiceImpl extends ServiceImpl<UsersInfoMapper, User> implements AuthService {

    @Autowired
    private AuthMapper authMapper;
    @Override
    public Result<GetUsersRespVO> getUsers(GetUsersReqVO reqVO) {
        return null;
    }

    @Override
    public Result<GetUsersRespVO> page(UsersInfoParam usersInfoParam) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        Page<User> page = new Page(usersInfoParam.getPage(),usersInfoParam.getPageSize());
        page = this.page(page,queryWrapper);
        User usersInfo = new User();
        System.out.println(page);
        GetUsersRespVO respVO = new GetUsersRespVO();
        respVO.setList(page.getRecords());
        respVO.setTotal(page.getTotal());
        return Result.success(respVO);
    }

    @Override
    public Result delete(List<UsersInfoParam> usersInfoParamList) {
        List<Long> ids = new ArrayList<>();
        usersInfoParamList.forEach(usersInfoParam -> {
            ids.add(usersInfoParam.getId());
        });
        this.removeByIds(ids);
        return Result.success("删除成功");
    }

    @Override
    public Result add(User user) {
        user.setStatus(UserStatus.YES.code);
        String password = DigestUtils.md5DigestAsHex("123456".getBytes());
        user.setPassword(password);
        this.save(user);
        return Result.success("添加成功");
    }

    @Override
    public Result edit(User user) {
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(User::getId,user.getId());
        user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
        this.update(user, lambdaQueryWrapper);
        return Result.success("修改成功");
    }

    @Override
    public Result resetUsers(List<UsersInfoParam> usersInfoParamList) {
        List<Long> ids = new ArrayList<>();
        usersInfoParamList.forEach(usersInfoParam -> {
            ids.add(usersInfoParam.getId());
        });
        List<User> users = new ArrayList<>();
        ids.forEach(id -> {
            User user = this.getById(id);
            user.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));
            users.add(user);
        });
        this.updateBatchById(users);
        return Result.success("重置成功，一共重置"+ users.size()+"条");
    }

    @Override
    public Result editSelf(User user) {
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(User::getId,user.getId());
        user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
        this.update(user, lambdaQueryWrapper);
        return Result.success("修改成功");
    }


}
