package com.howhich.fuchuang.demos.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckRole;
import cn.dev33.satoken.stp.StpUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
@Api(tags = "测试接口")
@Slf4j
//@SaCheckLogin
public class TestController {
    @GetMapping("/hello")
    @ApiOperation("token测试")
    public String test1(){
        log.info("auth:"+StpUtil.getTokenValue());
        return (StpUtil.getLoginIdAsString());
    }
    @SaCheckRole("admin")
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
}
