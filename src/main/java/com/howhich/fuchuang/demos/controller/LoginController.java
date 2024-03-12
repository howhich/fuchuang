package com.howhich.fuchuang.demos.controller;

import com.howhich.fuchuang.demos.constant.Result;
import com.howhich.fuchuang.demos.entity.req.UserLoginReqVO;
import com.howhich.fuchuang.demos.entity.resp.UserLoginRespVO;
import com.howhich.fuchuang.demos.service.LoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/login")
@Api(tags = "登录login")
public class LoginController {
    @Autowired
    private LoginService loginService;

    @ApiOperation(value = "用户登录")
    @PostMapping("/userLogin")
    public Result<UserLoginRespVO> login(@RequestBody @Validated UserLoginReqVO reqVO, BindingResult result){
        if(result.hasErrors()){
            List<ObjectError> errors = result.getAllErrors();
            errors.forEach(objectError -> System.out.println(objectError.getObjectName() + "====" + objectError.getDefaultMessage()));
        }
        return loginService.userLogin(reqVO);
    }
    @ApiOperation(value = "用户登出")
    @PostMapping("/userLogout")
    public Result logout(){
        return loginService.userLogout();
    }

}
