package com.howhich.fuchuang.demos.controller;

import com.howhich.fuchuang.demos.constant.Result;
import com.howhich.fuchuang.demos.entity.req.StudentEditReqVO;
import com.howhich.fuchuang.demos.service.StudentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/student")
@Api(tags = "学生管理")
public class StudentController {
    @Autowired
    private StudentService studentService;
    @PostMapping("/registy")
    @ApiOperation(value = "学生自己注册")
    public Result registry(@RequestBody StudentEditReqVO reqVO){

        return Result.success();
    }
}
