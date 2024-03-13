package com.howhich.fuchuang.demos.controller;

import cn.dev33.satoken.annotation.SaCheckRole;
import com.howhich.fuchuang.demos.constant.Result;
import com.howhich.fuchuang.demos.constant.RoleType;
import com.howhich.fuchuang.demos.entity.req.GetImportRecordsReqVO;
import com.howhich.fuchuang.demos.entity.req.StudentEditReqVO;
import com.howhich.fuchuang.demos.entity.resp.GetImportRecordsRespVO;
import com.howhich.fuchuang.demos.entity.resp.GetStudentInfoRespVO;
import com.howhich.fuchuang.demos.entity.resp.GetStudentRecordsRespVO;
import com.howhich.fuchuang.demos.service.AuthService;
import com.howhich.fuchuang.demos.service.StudentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/student")
@Api(tags = "学生student")
public class StudentController {
    @Autowired
    private StudentService studentService;
    @Autowired
    private AuthService authService;
//    @PostMapping("/registy")
//    @ApiOperation(value = "学生自己注册")
//    public Result registry(@RequestBody StudentEditReqVO reqVO){
//        return studentService.registry(reqVO);
//    }
//    @SaCheckRole(value = RoleType.STUDENT.code)
//    @GetMapping("/studentInfo")
//    @ApiOperation(value = "学生获取自身信息")
//    public Result<GetStudentInfoRespVO> getStudentInfo(){
//        return authService.getStudentInfo();
//    }

    @SaCheckRole(value = RoleType.STUDENT.code)
    @PostMapping("/studentEdit")
    @ApiOperation(value = "学生编辑自身信息")
    public Result edit(@RequestBody StudentEditReqVO reqVO){
        return authService.studentEdit(reqVO);
    }

    @SaCheckRole(value = RoleType.STUDENT.code)
    @PostMapping("/getImportRecords")
    @ApiOperation(value = "学生获取考试导入记录")
    public Result<GetStudentRecordsRespVO> getImportRecords(@RequestBody GetImportRecordsReqVO reqVO){
        return studentService.getStudentRecord(reqVO);
    }

}
