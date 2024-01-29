package com.howhich.fuchuang.demos.service.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.howhich.fuchuang.demos.constant.Result;
import com.howhich.fuchuang.demos.constant.RoleType;
import com.howhich.fuchuang.demos.constant.UserStatus;
import com.howhich.fuchuang.demos.entity.Base.Record;
import com.howhich.fuchuang.demos.entity.Base.Student;
import com.howhich.fuchuang.demos.entity.Base.User;
import com.howhich.fuchuang.demos.entity.req.ImportRecordsReqVO;
import com.howhich.fuchuang.demos.entity.req.RegisteStudentReqVO;
import com.howhich.fuchuang.demos.entity.resp.ImportRecordsRespVO;
import com.howhich.fuchuang.demos.mapper.RecordMapper;
import com.howhich.fuchuang.demos.mapper.StudentMapper;
import com.howhich.fuchuang.demos.service.AuthService;
import com.howhich.fuchuang.demos.service.RecordsService;
import com.howhich.fuchuang.demos.service.StudentService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements StudentService {
//    @Autowired
//    private AuthService authService;
//    @Autowired
//    private RedisTemplate redisTemplate;
//    @Override
//    public Result registy(RegisteStudentReqVO reqVO) {
//        User user = User.builder()
//                .role(RoleType.STUDENT.code)
//                .password(DigestUtils.md5DigestAsHex(reqVO.getPassword().getBytes()))
//                .status(UserStatus.YES.code)
//                .username((String) redisTemplate.opsForValue().get("username"))
//                .build();
//        authService.save(user);
//        authService.getOne(new LambdaQueryWrapper<User>().orderByDesc(User::getCreateTime).last("limit 1"));
//        Student student = Student.builder()
//                .studentNum(reqVO.getStudentNum())
//                .name(reqVO.getName())
//                .changable(0)
//                .build();
//        this.save(student);
//        return Result.success("注册成功");
//    }
}
