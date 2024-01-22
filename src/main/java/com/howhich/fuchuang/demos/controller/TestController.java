package com.howhich.fuchuang.demos.controller;

import cn.dev33.satoken.annotation.SaCheckRole;
import cn.dev33.satoken.stp.StpUtil;
import com.howhich.fuchuang.demos.constant.Result;
import com.howhich.fuchuang.demos.constant.RoleType;
import com.howhich.fuchuang.demos.entity.req.UsersInfoParam;
import com.howhich.fuchuang.demos.entity.resp.GetUsersRespVO;
import com.howhich.fuchuang.demos.entity.resp.User;
import com.howhich.fuchuang.demos.service.AuthService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/test")
@Api(tags = "测试接口")
@Slf4j
//@SaCheckLogin
public class TestController {
    @Resource
    private AuthService authService;

    @GetMapping("/hello")
    @ApiOperation("token测试")
    public String test1(){
        log.info("auth:"+StpUtil.getTokenValue());
        return (StpUtil.getLoginIdAsString());
    }
    @SaCheckRole(RoleType.ADMIN.code)
    @GetMapping("/auth")
    @ApiOperation("权限测试")
    public String test2(){
        return StpUtil.getRoleList().get(0);
    }

    @GetMapping("/getRole")
    @ApiOperation("获取角色")
    public String getRole(){
        return StpUtil.getRoleList().toString();
    }

    @GetMapping("/getUsers")
    @ApiOperation("获取所有用户信息")
    public Result<GetUsersRespVO> getUsers(UsersInfoParam usersInfoParam){
        return authService.page(usersInfoParam);
    }
}
