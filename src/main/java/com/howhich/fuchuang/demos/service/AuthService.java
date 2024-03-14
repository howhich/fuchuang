package com.howhich.fuchuang.demos.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.howhich.fuchuang.demos.constant.Result;
import com.howhich.fuchuang.demos.entity.req.*;
import com.howhich.fuchuang.demos.entity.resp.*;
import com.howhich.fuchuang.demos.entity.Base.User;
import com.howhich.fuchuang.demos.entity.req.GetAllStudentsReqVO;

import java.util.List;

public interface AuthService extends IService<User> {

    Result<GetUsersRespVO> page(UsersInfoReqVO usersInfoReqVO);

    Result delete(UsersDeleteReqVO usersInfoReqVOList);

    Result add(User user);

    Result resetUsers(ResetReqVO ids);

    Result<TeacherRegistryRespVO> registry(RegistryUserReqVO reqVO);

    Result<GetAllClassRespVO> getAllClasses(GetAllClassReqVO reqVO);

    Result<GetAllStudentsRespVO> getAllStudentsByClassId(GetAllStudentsReqVO reqVO);

    Result bindStudentById(BindStudentReqVO reqVO);

    Result registeStudent(TeacherRegisteReqVO reqVO);

    Result studentEdit(StudentEditReqVO reqVO);

    Result teacherEdit(TeacherEditReqVO reqVO);

    Result<GetStudentInfoRespVO> getStudentInfo(Long id);

    Result<GetTeacherInfoRespVO> getTeacherInfo();

    Result<GetStudentInfoRespVO> getStudentSelfInfo();

    Result studentSelfEdit(StudentSelfEditReqVO reqVO);
}
