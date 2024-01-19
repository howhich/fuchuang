package com.howhich.fuchuang.demos.service.serviceImpl;

import cn.dev33.satoken.stp.StpUtil;
import com.howhich.fuchuang.demos.Utils.exception.AssertUtils;
import com.howhich.fuchuang.demos.Utils.exception.ExceptionsEnums;
import com.howhich.fuchuang.demos.constant.Result;
import com.howhich.fuchuang.demos.constant.UserStatus;
import com.howhich.fuchuang.demos.entity.User;
import com.howhich.fuchuang.demos.entity.req.UserLoginReqVO;
import com.howhich.fuchuang.demos.entity.resp.UserLoginRespVO;
import com.howhich.fuchuang.demos.mapper.LoginMapper;
import com.howhich.fuchuang.demos.service.LoginService;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private LoginMapper loginMapper;
    @Autowired
    private RedisTemplate redisTemplate;
    @Override
    public Result<UserLoginRespVO> userLogin(UserLoginReqVO reqVO) {

        //判断是否有空值
        AssertUtils.isFalse(StringUtils.isNotEmpty(reqVO.getPassword()) &&
                StringUtils.isNotEmpty(reqVO.getPassword()),ExceptionsEnums.Login.INFO_EMPTY);


        String password = reqVO.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());

        User user = loginMapper.getUserByUserName(reqVO.getUsername());

        //判断是否存在账号
        AssertUtils.isFalse(ObjectUtils.isNotEmpty(user), ExceptionsEnums.UserEX.NO_LOGIN);

        //核对密码
        AssertUtils.isFalse(password.equals(user.getPassword()),ExceptionsEnums.Login.USER_ERROR);

        //检查账号状态？
        AssertUtils.isFalse(UserStatus.YES.code.equals(user.getStatus()),ExceptionsEnums.Login.USER_CLOSE);

        //redis储存用户信息
        redisTemplate.opsForValue().set("role",user.getRole());

        //封装返回类
        UserLoginRespVO respVO = new UserLoginRespVO();
        respVO.setUsername(user.getUsername());
        respVO.setId(user.getId());
        StpUtil.login(user.getId());
        respVO.setAuthorization(StpUtil.getTokenValue());
        return Result.success(respVO);
    }

    @Override
    public Result userLogout() {
        StpUtil.logout(StpUtil.getLoginId());
        return Result.success("退出成功");
    }

    @Override
    public String getRoleById(Long loginId) {
        String role = loginMapper.getRoleById(loginId);
        return role;
    }
}
