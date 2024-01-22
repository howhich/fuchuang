package com.howhich.fuchuang.demos.controller;

import cn.dev33.satoken.annotation.SaCheckRole;
import com.howhich.fuchuang.demos.constant.Result;
import com.howhich.fuchuang.demos.constant.RoleType;
import com.howhich.fuchuang.demos.entity.req.GetUsersReqVO;
import com.howhich.fuchuang.demos.entity.req.UsersInfoParam;
import com.howhich.fuchuang.demos.entity.resp.GetUsersRespVO;
import com.howhich.fuchuang.demos.entity.resp.User;
import com.howhich.fuchuang.demos.service.AuthService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth")
@Api(tags = "管理认证")
public class AuthController {
    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService){
        this.authService = authService;
    }

//    @PostMapping("/getUserList")
//    @ApiOperation(value = "管理员获取所有用户")
//    @SaCheckRole(value = RoleType.ADMIN.code)
//    public Result<GetUsersRespVO> getUsers(@RequestBody GetUsersReqVO reqVO){
//        return authService.getUsers(reqVO);
//    }
    @GetMapping("/getUsersInfo")
    @ApiOperation(value = "获取所有用户信息")
    public Result<GetUsersRespVO> getUsers(@RequestBody UsersInfoParam usersInfoParam){
        return authService.page(usersInfoParam);
    }

    @PostMapping("/deleteUser")
    @ApiOperation(value = "管理员删除用户(默认为批量删除 请传入List)")
    public Result deleteUsers( @RequestBody List<UsersInfoParam> usersInfoParamList){
        return authService.delete(usersInfoParamList);
    }

    @PostMapping("/addUser")
    @ApiOperation(value = "管理员添加用户")
    public Result addUser(@RequestBody User user){
        return authService.add(user);
    }

    @PostMapping("/updateUser")
    @ApiOperation(value = "管理员更改用户")
    public Result updateUser(@RequestBody User user){ return authService.edit(user);}

    @PostMapping("/resetUsers")
    @ApiOperation(value = "管理员批量重置用户密码")
    public Result resetUsers(@RequestBody List<UsersInfoParam> usersInfoParamList){
        return authService.resetUsers(usersInfoParamList);
    }

    @PostMapping("/updateSelf")
    @ApiOperation("用户更改自己信息")
    public Result updateSelf(@RequestBody User user){return authService.editSelf(user);}
}
