package com.howhich.fuchuang.demos.controller;

import cn.dev33.satoken.annotation.SaCheckRole;
import com.howhich.fuchuang.demos.constant.Result;
import com.howhich.fuchuang.demos.constant.RoleType;
import com.howhich.fuchuang.demos.entity.req.*;
import com.howhich.fuchuang.demos.entity.resp.*;
import com.howhich.fuchuang.demos.service.AuthService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auth")
@Api(tags = "管理auth")
public class AuthController {
    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService){
        this.authService = authService;
    }

//    @SaCheckRole(value = RoleType.TEACHER.code)
//    @PostMapping("/getAllClasses")
//    @ApiOperation(value = "老师获取当前所有班级")
//    public Result<GetAllClassRespVO> getAllClasses(@RequestBody GetAllClassReqVO reqVO){
//        long id = StpUtil.getLoginIdAsLong();
//        reqVO.setTeacherId(id);
//        return authService.getAllClasses(reqVO);
//    }
    @SaCheckRole(value = RoleType.TEACHER.code)
    @PostMapping("/getAllStudents")
    @ApiOperation(value = "老师获取自己所有学生")
    public Result<GetAllStudentsRespVO> getAllStudentsByClassId(@RequestBody GetAllStudentsReqVO reqVO){
        return authService.getAllStudentsByClassId(reqVO);
    }
    @SaCheckRole(value = RoleType.TEACHER.code)
    @PostMapping("/bindStudentById")
    @ApiOperation(value = "老师通过学生学号列表绑定")
    public Result bindStudentById(@RequestBody BindStudentReqVO reqVO){
        return authService.bindStudentById(reqVO);
    }
    @SaCheckRole(value = RoleType.TEACHER.code)
    @PostMapping("/registeStudent")
    @ApiOperation(value = "老师注册学生")
    public Result registeStudent(@RequestBody TeacherRegisteReqVO reqVO){
        return authService.registeStudent(reqVO);
    }
//    @SaCheckRole(value = RoleType.TEACHER.code)
//    @PostMapping("/getUsersInfo")
//    @ApiOperation(value = "管理员获取所有用户信息")
//    public Result<GetUsersRespVO> getUsers(@RequestBody UsersInfoReqVO usersInfoReqVO){
//        return authService.page(usersInfoReqVO);
//    }

    @PostMapping("/deleteUser")
    @SaCheckRole(value = RoleType.TEACHER.code)
    @ApiOperation(value = "老师删除用户(默认为批量删除 请传入List)")
    public Result deleteUsers( @RequestBody UsersDeleteReqVO usersInfoReqVOList){
        return authService.delete(usersInfoReqVOList);
    }


    @PostMapping("/registry")
    @ApiOperation(value = "老师注册")
//    @SaCheckRole(value = RoleType.TEACHER.code)
    public Result<TeacherRegistryRespVO> registryUser(@RequestBody RegistryUserReqVO reqVO){
        return authService.registry(reqVO);
    }


    @PostMapping("/resetUsers")
    @ApiOperation(value = "老师批量重置用户密码")
    @SaCheckRole(value = RoleType.TEACHER.code)
    public Result resetUsers(@RequestBody ResetReqVO ids){
        return authService.resetUsers(ids);
    }
    @SaCheckRole(value = RoleType.TEACHER.code)
    @GetMapping("/studentInfo")
    @ApiOperation(value = "老师获取学生信息")
    public Result<GetStudentInfoRespVO> getStudentInfo(@RequestParam Long id){
        return authService.getStudentInfo(id);
    }

    @SaCheckRole(value = RoleType.TEACHER.code)
    @PostMapping("/teacherEditStudent")
    @ApiOperation(value = "老师编辑学生信息")
    public Result registry(@RequestBody StudentEditReqVO reqVO){
        return authService.studentEdit(reqVO);
    }

    @SaCheckRole(value = RoleType.TEACHER.code)
    @GetMapping("/teacherInfo")
    @ApiOperation(value = "老师获取自身信息")
    public Result<GetTeacherInfoRespVO> getTeacherInfo(){
        return authService.getTeacherInfo();
    }

    @SaCheckRole(value = RoleType.TEACHER.code)
    @PostMapping("/teacherEdit")
    @ApiOperation(value = "老师编辑自身信息")
    public Result teacherEdit(@RequestBody TeacherEditReqVO reqVO){
        return authService.teacherEdit(reqVO);
    }


}
