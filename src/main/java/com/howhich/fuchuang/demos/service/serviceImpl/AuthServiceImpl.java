package com.howhich.fuchuang.demos.service.serviceImpl;

import cn.dev33.satoken.stp.StpInterface;
import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.howhich.fuchuang.demos.Utils.exception.AssertUtils;
import com.howhich.fuchuang.demos.Utils.exception.ExceptionsEnums;
import com.howhich.fuchuang.demos.constant.Result;
import com.howhich.fuchuang.demos.constant.RoleType;
import com.howhich.fuchuang.demos.constant.UserStatus;
import com.howhich.fuchuang.demos.entity.Base.Clazz;
import com.howhich.fuchuang.demos.entity.Base.Student;
import com.howhich.fuchuang.demos.entity.req.*;
import com.howhich.fuchuang.demos.entity.resp.GetAllClassRespVO;
import com.howhich.fuchuang.demos.entity.resp.GetAllStudentsByClassIdRespVO;
import com.howhich.fuchuang.demos.entity.resp.GetUsersRespVO;
import com.howhich.fuchuang.demos.entity.Base.User;
import com.howhich.fuchuang.demos.mapper.AuthMapper;
import com.howhich.fuchuang.demos.mapper.UsersInfoMapper;
import com.howhich.fuchuang.demos.service.AuthService;
import com.howhich.fuchuang.demos.service.ClassService;
import com.howhich.fuchuang.demos.service.StudentService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

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

    @Override
    public Result<GetUsersRespVO> page(UsersInfoParam usersInfoParam) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        Page<User> page = new Page(usersInfoParam.getPage(),usersInfoParam.getPageSize());

        PageHelper.startPage(usersInfoParam.getPage(),usersInfoParam.getPageSize());
        page = this.page(page,queryWrapper);

        long count = this.count();

        //TODO 条件查询

        GetUsersRespVO respVO = new GetUsersRespVO();
        respVO.setList(page.getRecords());
        respVO.setTotal(count);
        return Result.success(respVO);
    }

    @Override
    public Result delete(List<UsersInfoParam> usersInfoParamList) {
        List<Long> ids = new ArrayList<>();
        usersInfoParamList.forEach(usersInfoParam -> {
            ids.add(usersInfoParam.getId());
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
    public Result edit(EditStudentReqVO reqVO) {
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        long id = StpUtil.getLoginIdAsLong();
        lambdaQueryWrapper.eq(User::getId,id);
        User user = new User();
        user.setPassword(DigestUtils.md5DigestAsHex(reqVO.getPassword().getBytes()));
        this.update(user, lambdaQueryWrapper);


        Student student = new Student();
        student.setName(reqVO.getName());
        student.setId(id);
        studentService.updateById(student);
        return Result.success("修改成功");
    }

    @Override
    public Result resetUsers(List<UsersInfoParam> usersInfoParamList) {
        List<Long> ids = new ArrayList<>();
        usersInfoParamList.forEach(usersInfoParam -> {
            ids.add(usersInfoParam.getId());
        });
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
    public Result editSelf(User user) {
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(User::getId,user.getId());
        user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
        this.update(user, lambdaQueryWrapper);
        return Result.success("修改成功");
    }

    @Override
    public Result registry(User user) {
        User user1 = User.builder()
                .role(user.getRole())
                .status(UserStatus.YES.code)
                .username(user.getUsername())
                .password(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()))
                .build();
        this.save(user1);
        user1 = this.getOne(new LambdaQueryWrapper<User>().orderByDesc(User::getCreateTime)
                .last("limit 1"));
        if(user1.getRole().equals(RoleType.STUDENT.code)){
            Student student = Student.builder()
                    .Id(user1.getId())
                    .build();
            studentService.save(student);
        }
        return Result.success("注册成功");
    }

    @Override
    public Result<GetAllClassRespVO> getAllClasses(GetAllClassReqVO reqVO) {
        List<Clazz> clazzes = new ArrayList<>();
        clazzes = authMapper.getAllClasse(reqVO.getTeacherId());

        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        Page<User> page = new Page(reqVO.getPage(),reqVO.getPageSize());

        PageHelper.startPage(reqVO.getPage(),reqVO.getPageSize());
        page = this.page(page,queryWrapper);

        int count;
        LambdaQueryWrapper<Clazz> clazzLambdaQueryWrapper = new LambdaQueryWrapper<>();
        clazzLambdaQueryWrapper.eq(Clazz::getTeacherId,reqVO.getTeacherId());
        //TODO 条件查询
//        if(reqVO.getId()!=null){
//            clazzLambdaQueryWrapper.eq(Clazz::getId,reqVO.getId());
//        }
//        if(reqVO.get)
        count = (int) classService.count();


        GetAllClassRespVO respVO = new GetAllClassRespVO();
        respVO.setClassList(clazzes);
        respVO.setTotal((int) count);

        return Result.success(respVO);
    }

    @Override
    public Result<GetAllStudentsByClassIdRespVO> getAllStudentsByClassId(Long classId) {

        List<Student> students = new ArrayList<>();
        LambdaQueryWrapper<Student> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Student::getClassId,classId);
        students = studentService.list(queryWrapper);

//        students = authMapper.getAllStudentsByClassId(classId);
        GetAllStudentsByClassIdRespVO respVO = new GetAllStudentsByClassIdRespVO();
        int count = (int) studentService.count(new LambdaQueryWrapper<Student>().eq(Student::getClassId, classId));
        respVO.setStudentList(students);
        respVO.setTotal(count);
        return Result.success(respVO);
    }

    @Override
    public Result bindStudentById(BindStudentReqVO reqVO) {

        String studentNum = reqVO.getStudentNum();
        Student student = studentService.getOne(new LambdaQueryWrapper<Student>().eq(Student::getStudentNum,reqVO.getStudentNum()));
//        Student student = authMapper.getStudentByStudentNum(studentNum);
        AssertUtils.isFalse(ObjectUtils.isNotEmpty(student), ExceptionsEnums.UserEX.ACCOUNT_NOT_FIND);
        authMapper.bindStudentById(reqVO);
        return Result.success("绑定成功");
    }

    @Override
    public Result registeStudent(RegisteStudentReqVO reqVO) {
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
        System.out.println(user.getId()+"!!!!!");
        Student student = Student.builder()
                .studentNum((reqVO.getStudentNum()))
                .name(reqVO.getName())
                .Id(user.getId())
                .build();
        studentService.save(student);
        return Result.success("注册成功");
    }
    @Override
    public Result registy(RegisteStudentReqVO reqVO) {
        long id = StpUtil.getLoginIdAsLong();
        User user = User.builder()
                .role(RoleType.STUDENT.code)
                .password(DigestUtils.md5DigestAsHex(reqVO.getPassword().getBytes()))
                .status(UserStatus.YES.code)
                .username((String) redisTemplate.opsForValue().get("username"))
                .id(id)
                .build();
        this.updateById(user);
        this.getOne(new LambdaQueryWrapper<User>().orderByDesc(User::getCreateTime).last("limit 1"));
        Student student = Student.builder()
                .studentNum(reqVO.getStudentNum())
                .name(reqVO.getName())
                .changable(0)
                .Id(id)
                .build();
        studentService.updateById(student);
        return Result.success("学生注册成功");
    }
    public Result update(RegisteStudentReqVO reqVO) {
        User user = User.builder()
                .role(RoleType.STUDENT.code)
                .password(DigestUtils.md5DigestAsHex(reqVO.getPassword().getBytes()))
                .status(UserStatus.YES.code)
                .username((String) redisTemplate.opsForValue().get("username"))
                .build();
        this.save(user);
        this.getOne(new LambdaQueryWrapper<User>().orderByDesc(User::getCreateTime).last("limit 1"));
        Student student = Student.builder()
                .studentNum(reqVO.getStudentNum())
                .name(reqVO.getName())
                .changable(0)
                .build();
        studentService.save(student);
        return Result.success("学生注册成功");
    }
}
