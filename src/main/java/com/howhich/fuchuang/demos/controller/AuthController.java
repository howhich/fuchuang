package com.howhich.fuchuang.demos.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.howhich.fuchuang.demos.constant.Result;
import com.howhich.fuchuang.demos.entity.Base.Clazz;
import com.howhich.fuchuang.demos.entity.Base.Student;
import com.howhich.fuchuang.demos.entity.req.*;
import com.howhich.fuchuang.demos.entity.resp.GetAllClassRespVO;
import com.howhich.fuchuang.demos.entity.resp.GetAllStudentsByClassIdRespVO;
import com.howhich.fuchuang.demos.entity.resp.GetUsersRespVO;
import com.howhich.fuchuang.demos.entity.Base.User;
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
//    @ApiOperation(value = "老师获取所有用户")
//    @SaCheckRole(value = RoleType.ADMIN.code)
//    public Result<GetUsersRespVO> getUsers(@RequestBody GetUsersReqVO reqVO){
//        return authService.getUsers(reqVO);
//    }
    @GetMapping("/getAllClasses")
    @ApiOperation(value = "老师获取当前所有班级")
    public Result<GetAllClassRespVO> getAllClasses(@RequestBody GetAllClassReqVO reqVO){
        long id = StpUtil.getLoginIdAsLong();
        reqVO.setTeacherId(id);
        return authService.getAllClasses(reqVO);
    }
    @PostMapping("/getAllStudentsByClassId")
    @ApiOperation(value = "通过班级ID获取所有学生")
    public Result<GetAllStudentsByClassIdRespVO> getAllStudentsByClassId(@RequestParam Long classId){
        return authService.getAllStudentsByClassId(classId);
    }
    @PostMapping("/bindStudentById")
    @ApiOperation(value = "通过学生Id绑定")
    public Result bindStudentById(@RequestBody BindStudentReqVO reqVO){
        return authService.bindStudentById(reqVO);
    }



    @PostMapping("/registeStudent")
    @ApiOperation(value = "注册学生")
    public Result registeStudent(@RequestBody RegisteStudentReqVO reqVO){
        return authService.registeStudent(reqVO);
    }

    @GetMapping("/getUsersInfo")
    @ApiOperation(value = "获取所有用户信息")
    public Result<GetUsersRespVO> getUsers(@RequestBody UsersInfoParam usersInfoParam){
        return authService.page(usersInfoParam);
    }

    @PostMapping("/deleteUser")
    @ApiOperation(value = "老师删除用户(默认为批量删除 请传入List)")
    public Result deleteUsers( @RequestBody List<UsersInfoParam> usersInfoParamList){
        return authService.delete(usersInfoParamList);
    }

    @PostMapping("/addUser")
    @ApiOperation(value = "老师添加用户")
    public Result addUser(@RequestBody User user){
        return authService.add(user);
    }

    @PostMapping("/registry")
    @ApiOperation(value = "注册")
    public Result registryUser(@RequestBody User user){
        return authService.registry(user);
    }

//    @PostMapping("/updateUser")
//    @ApiOperation(value = "老师更改用户")
//    public Result updateUser(@RequestBody User user){ return authService.edit(user);}

    @PostMapping("/resetUsers")
    @ApiOperation(value = "老师批量重置用户密码")
    public Result resetUsers(@RequestBody List<UsersInfoParam> usersInfoParamList){
        return authService.resetUsers(usersInfoParamList);
    }

//    @PostMapping("/updateSelf")
//    @ApiOperation("用户更改自己信息")
//    public Result updateSelf(@RequestBody User user){return authService.editSelf(user);}

    @PostMapping("/studentRegistry")
    @ApiOperation(value = "学生自己注册")
    public Result registry(@RequestBody RegisteStudentReqVO reqVO){
        return authService.registy(reqVO);
    }

    @PostMapping("/studentEdit")
    @ApiOperation(value = "学生编辑自身信息")
    public Result edit(@RequestBody EditStudentReqVO reqVO){
        return authService.edit(reqVO);
    }
}
