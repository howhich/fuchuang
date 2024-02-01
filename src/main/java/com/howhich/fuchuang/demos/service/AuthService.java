package com.howhich.fuchuang.demos.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.howhich.fuchuang.demos.constant.Result;
import com.howhich.fuchuang.demos.entity.req.*;
import com.howhich.fuchuang.demos.entity.resp.*;
import com.howhich.fuchuang.demos.entity.Base.User;

import java.util.List;

public interface AuthService extends IService<User> {

    Result<GetUsersRespVO> page(UsersInfoParam usersInfoParam);

    Result delete(List<UsersInfoParam> usersInfoParamList);

    Result add(User user);

    Result resetUsers(List<Long> ids);

    Result registry(RegistryUserReqVO reqVO);

    Result<GetAllClassRespVO> getAllClasses(GetAllClassReqVO reqVO);

    Result<GetAllStudentsByClassIdRespVO> getAllStudentsByClassId(GetAllStudentsByClassIdReqVO reqVO);

    Result bindStudentById(BindStudentReqVO reqVO);

    Result registeStudent(TeacherRegisteReqVO reqVO);

    Result studentEdit(StudentEditReqVO reqVO);

    Result teacherEdit(TeacherEditReqVO reqVO);

    Result<GetStudentInfoRespVO> getStudentInfo();

    Result<GetTeacherInfoRespVO> getTeacherInfo();
}
