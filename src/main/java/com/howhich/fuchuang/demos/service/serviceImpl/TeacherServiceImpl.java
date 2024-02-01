package com.howhich.fuchuang.demos.service.serviceImpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.howhich.fuchuang.demos.entity.Base.Student;
import com.howhich.fuchuang.demos.entity.Base.Teacher;
import com.howhich.fuchuang.demos.mapper.StudentMapper;
import com.howhich.fuchuang.demos.mapper.TeacherMapper;
import com.howhich.fuchuang.demos.service.StudentService;
import com.howhich.fuchuang.demos.service.TeacherService;
import org.springframework.stereotype.Service;

@Service
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher> implements TeacherService {
//    @Autowired
//    private AuthService authService;
//    @Autowired
//    private RedisTemplate redisTemplate;
//    @Override
//    public Result stundetEdit(StudentEditReqVO reqVO) {
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
