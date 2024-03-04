package com.howhich.fuchuang.demos.service.serviceImpl;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.howhich.fuchuang.demos.Utils.exception.AssertUtils;
import com.howhich.fuchuang.demos.Utils.exception.ExceptionsEnums;
import com.howhich.fuchuang.demos.constant.Result;
import com.howhich.fuchuang.demos.constant.RoleType;
import com.howhich.fuchuang.demos.constant.UserStatus;
import com.howhich.fuchuang.demos.entity.Base.Clazz;
import com.howhich.fuchuang.demos.entity.Base.Student;
import com.howhich.fuchuang.demos.entity.Base.Teacher;
import com.howhich.fuchuang.demos.entity.req.*;
import com.howhich.fuchuang.demos.entity.resp.*;
import com.howhich.fuchuang.demos.entity.Base.User;
import com.howhich.fuchuang.demos.mapper.AuthMapper;
import com.howhich.fuchuang.demos.mapper.UsersInfoMapper;
import com.howhich.fuchuang.demos.service.AuthService;
import com.howhich.fuchuang.demos.service.ClassService;
import com.howhich.fuchuang.demos.service.StudentService;
import com.howhich.fuchuang.demos.service.TeacherService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class AuthServiceImpl extends ServiceImpl<UsersInfoMapper, User> implements AuthService {

    @Autowired
    private AuthMapper authMapper;
    @Autowired
    private StudentService studentService;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private ClassService classService;
    @Resource
    private TeacherService teacherService;


    @Override
    public Result<GetUsersRespVO> page(UsersInfoReqVO usersInfoReqVO) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        Page<User> page = new Page(usersInfoReqVO.getPage(), usersInfoReqVO.getPageSize());

        PageHelper.startPage(usersInfoReqVO.getPage(), usersInfoReqVO.getPageSize());
        page = this.page(page,queryWrapper);

        long count = this.count();

        //TODO 条件查询

        GetUsersRespVO respVO = new GetUsersRespVO();
        respVO.setList(page.getRecords());
        respVO.setTotal(count);
        return Result.success(respVO);
    }

    @Override
    public Result delete(List<UsersInfoReqVO> usersInfoReqVOList) {
        List<Long> ids = new ArrayList<>();
        usersInfoReqVOList.forEach(usersInfoReqVO -> {
            ids.add(usersInfoReqVO.getId());
        });
        this.removeByIds(ids);
        return Result.success("删除成功");
    }

    @Override
    public Result add(User user) {
        user.setStatus(UserStatus.YES.code);
        String password = DigestUtils.md5DigestAsHex("123456".getBytes());
        user.setPassword(password);
        this.save(user);
        return Result.success("添加成功");
    }


    @Override
    public Result resetUsers(List<Long> ids) {
        List<User> users = new ArrayList<>();
        ids.forEach(id -> {
            User user = this.getById(id);
            user.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));
            users.add(user);
        });
        this.updateBatchById(users);
        return Result.success("重置成功，一共重置"+ users.size()+"条");
    }


    @Override
    public Result registry(RegistryUserReqVO reqVO) {
        User user = new User();
        BeanUtils.copyProperties(reqVO,user);
        user.setStatus(UserStatus.YES.code);
        user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));

        this.save(user);
        user = this.getOne(new LambdaQueryWrapper<User>().orderByDesc(User::getCreateTime)
                .last("limit 1"));
        if(user.getRole().equals(RoleType.STUDENT.code)){
            Student student = Student.builder()
                    .id(user.getId())
                    .build();
            studentService.save(student);
        }
        else if(user.getRole().equals(RoleType.TEACHER.code)){
            Teacher teacher = Teacher.builder()
                    .id(user.getId())
                    .build();
            teacherService.save(teacher);
        }

        return Result.success("注册成功");
    }

    @Override
    public Result<GetAllClassRespVO> getAllClasses(GetAllClassReqVO reqVO) {
        List<Clazz> clazzes = new ArrayList<>();

        Page<Clazz> page = new Page(reqVO.getPage(),reqVO.getPageSize());
        PageHelper.startPage(reqVO.getPage(),reqVO.getPageSize());

        int count;
        LambdaQueryWrapper<Clazz> clazzLambdaQueryWrapper = new LambdaQueryWrapper<>();
        clazzLambdaQueryWrapper.eq(Clazz::getTeacherId,reqVO.getTeacherId());

        if(reqVO.getId()!=null && !reqVO.getId().equals("")){
            clazzLambdaQueryWrapper.eq(Clazz::getId,reqVO.getId());
        }
        if(reqVO.getClassName()!=null && !reqVO.getClassName().equals("")){
            clazzLambdaQueryWrapper.eq(Clazz::getClassName,reqVO.getClassName());
        }

        page = classService.page(page, clazzLambdaQueryWrapper);
        clazzes = page.getRecords();
        count = (int) classService.count(clazzLambdaQueryWrapper);

        GetAllClassRespVO respVO = new GetAllClassRespVO();
        respVO.setClassList(clazzes);
        respVO.setTotal(count);

        return Result.success(respVO);
    }

    @Override
    public Result<GetAllStudentsRespVO> getAllStudentsByClassId(GetAllStudentsReqVO reqVO) {
        long loginIdAsLong = StpUtil.getLoginIdAsLong();
//        System.out.printf("id:"+String.valueOf(loginIdAsLong));

        Page<Student> page = new Page(reqVO.getPage(),reqVO.getPageSize());
        PageHelper.startPage(reqVO.getPage(),reqVO.getPageSize());

        List<Student> students = new ArrayList<>();
        LambdaQueryWrapper<Student> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Student::getTeacherId, loginIdAsLong);

        page = studentService.page(page, queryWrapper);
        students = page.getRecords();
        GetAllStudentsRespVO respVO = new GetAllStudentsRespVO();
        int count = (int) studentService.count(new LambdaQueryWrapper<Student>().eq(Student::getTeacherId, reqVO.getId()));
        respVO.setStudentList(students);
        respVO.setTotal(count);
        return Result.success(respVO);
    }

    @Override
    public Result bindStudentById(BindStudentReqVO reqVO) {
        long loginId = StpUtil.getLoginIdAsLong();
//        Long classId = loginIdAsLong;
        List<String> studentNums = reqVO.getStudentNums();
        for (String studentNum : studentNums) {
            Student student = studentService.getOne(new LambdaQueryWrapper<Student>().eq(Student::getStudentNum,studentNum));
            AssertUtils.isFalse(ObjectUtils.isNotEmpty(student), ExceptionsEnums.UserEX.ACCOUNT_NOT_FIND);
            student.setTeacherId(loginId);
            studentService.update(student,new LambdaQueryWrapper<Student>().eq(Student::getId,student.getId()));

        }
        return Result.success("绑定成功");
    }

    @Override
    public Result registeStudent(TeacherRegisteReqVO reqVO) {
        //通过学号判断是否存在该学生
        Student student1 = studentService.getOne(new LambdaQueryWrapper<Student>().eq(Student::getStudentNum, reqVO.getStudentNum()));
        AssertUtils.isFalse(ObjectUtils.isEmpty(student1),ExceptionsEnums.UserEX.USER_HAVE);

        User user = User.builder()
                .status(UserStatus.YES.code)
                .username((reqVO.getStudentNum()))
                .password(DigestUtils.md5DigestAsHex(reqVO.getPassword().getBytes()))
                .role(RoleType.STUDENT.code)
                .build();

        this.save(user);
        user = this.getOne(new LambdaQueryWrapper<User>().eq(User::getUsername,reqVO.getStudentNum()));

        Student student = Student.builder()
                .studentNum((reqVO.getStudentNum()))
                .name(reqVO.getName())
                .id(user.getId())
                .changeable(0)
                .build();
        studentService.save(student);
        return Result.success("注册成功");
    }
    @Override
    public Result studentEdit(StudentEditReqVO reqVO) {
        long id = StpUtil.getLoginIdAsLong();
        User user = User.builder()
                .role(RoleType.STUDENT.code)
                .password(DigestUtils.md5DigestAsHex(reqVO.getPassword().getBytes()))
                .status(UserStatus.YES.code)
                .username((String) redisTemplate.opsForValue().get("username"))
                .id(id)
                .build();
        this.updateById(user);
        Student student = Student.builder()
                .studentNum(reqVO.getStudentNum())
                .name(reqVO.getName())
                .changeable(0)
                .id(id)
                .build();
        studentService.updateById(student);
        return Result.success("学生编辑成功");
    }
    @Override
    public Result teacherEdit(TeacherEditReqVO reqVO) {
        long id = StpUtil.getLoginIdAsLong();
        User user = User.builder()
                .username(reqVO.getUsername())
                .password(DigestUtils.md5DigestAsHex(reqVO.getPassword().getBytes()))
                .id(id)
                .build();

        this.updateById(user);
        Teacher teacher = Teacher.builder()
                .name(reqVO.getName())
                .id(id)
                .build();
        teacherService.updateById(teacher);
        return Result.success("教师修改成功");
    }

    @Override
    public Result<GetStudentInfoRespVO> getStudentInfo() {
        long id = StpUtil.getLoginIdAsLong();
        Student student = studentService.getOne(new LambdaQueryWrapper<Student>().eq(Student::getId,id));
        User user = this.getOne(new LambdaQueryWrapper<User>().eq(User::getId, id));
        GetStudentInfoRespVO resp = GetStudentInfoRespVO.builder()
                .changeable(student.getChangeable())
                .StudentNum(student.getStudentNum())
                .name(student.getName())
                .password(user.getPassword())
                .build();
        return Result.success(resp);
    }

    @Override
    public Result<GetTeacherInfoRespVO> getTeacherInfo() {
        long id = StpUtil.getLoginIdAsLong();
        Teacher teacher = teacherService.getOne(new LambdaQueryWrapper<Teacher>().eq(Teacher::getId, id));
        User user = this.getOne(new LambdaQueryWrapper<User>().eq(User::getId, id));
        GetTeacherInfoRespVO respVO = GetTeacherInfoRespVO.builder()
                .name(teacher.getName())
                .password(user.getPassword())
                .build();
        return Result.success(respVO);
    }


}
