package com.howhich.fuchuang.demos.service.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.howhich.fuchuang.demos.constant.Result;
import com.howhich.fuchuang.demos.constant.RoleType;
import com.howhich.fuchuang.demos.constant.UserStatus;
import com.howhich.fuchuang.demos.entity.Base.Student;
import com.howhich.fuchuang.demos.entity.Base.User;
import com.howhich.fuchuang.demos.entity.req.StudentEditReqVO;
import com.howhich.fuchuang.demos.mapper.AuthMapper;
import com.howhich.fuchuang.demos.mapper.StudentMapper;
import com.howhich.fuchuang.demos.mapper.UsersInfoMapper;
import com.howhich.fuchuang.demos.service.AuthService;
import com.howhich.fuchuang.demos.service.StudentService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements StudentService {
    @Autowired
    private AuthMapper authMapper;
    @Autowired
    private UsersInfoMapper usersInfoMapper;
    @Autowired
    private StudentMapper studentMapper;
    @Override
    public Result registry(StudentEditReqVO reqVO) {
        User user = new User();
        BeanUtils.copyProperties(reqVO,user);
        user.setStatus(UserStatus.YES.code);
        user.setRole(RoleType.STUDENT.code);
        user.setUsername(reqVO.getStudentNum());
        user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));

        usersInfoMapper.insert(user);
        user = usersInfoMapper.selectOne(new LambdaQueryWrapper<User>()
                .orderByDesc(User::getCreateTime)
                .last("limit 1"));
        if(user.getRole().equals(RoleType.STUDENT.code)){
            Student student = Student.builder()
                    .id(user.getId())
                    .studentNum(reqVO.getStudentNum())
                    .build();
//            this.save(student);
        studentMapper.insert(student);
        }

        return Result.success("注册成功");
    }
}
