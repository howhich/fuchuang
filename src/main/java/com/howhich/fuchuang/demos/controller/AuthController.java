package com.howhich.fuchuang.demos.controller;

import cn.dev33.satoken.annotation.SaCheckRole;
import com.howhich.fuchuang.demos.constant.Result;
import com.howhich.fuchuang.demos.constant.RoleType;
import com.howhich.fuchuang.demos.entity.req.GetUsersReqVO;
import com.howhich.fuchuang.demos.entity.resp.GetUsersRespVO;
import com.howhich.fuchuang.demos.service.AuthService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@Api(tags = "管理认证")
public class AuthController {
    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService){
        this.authService = authService;
    }

    @PostMapping("/getUserList")
    @ApiOperation(value = "管理员获取所有用户")
    @SaCheckRole(value = RoleType.ADMIN.code)
    public Result<GetUsersRespVO> getUsers(@RequestBody GetUsersReqVO reqVO){
        return authService.getUsers(reqVO);
    }
}
