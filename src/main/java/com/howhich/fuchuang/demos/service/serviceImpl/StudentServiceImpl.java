package com.howhich.fuchuang.demos.service.serviceImpl;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.howhich.fuchuang.demos.Utils.SM4EncryptUtil;
import com.howhich.fuchuang.demos.constant.Result;
import com.howhich.fuchuang.demos.constant.RoleType;
import com.howhich.fuchuang.demos.constant.UserStatus;
import com.howhich.fuchuang.demos.entity.Base.*;
import com.howhich.fuchuang.demos.entity.req.GetImportRecordsReqVO;
import com.howhich.fuchuang.demos.entity.req.StudentEditReqVO;
import com.howhich.fuchuang.demos.entity.resp.GetImportRecordsRespVO;
import com.howhich.fuchuang.demos.entity.resp.GetStudentRecordsRespVO;
import com.howhich.fuchuang.demos.mapper.AuthMapper;
import com.howhich.fuchuang.demos.mapper.PaperResultMapper;
import com.howhich.fuchuang.demos.mapper.StudentMapper;
import com.howhich.fuchuang.demos.mapper.UsersInfoMapper;
import com.howhich.fuchuang.demos.service.AuthService;
import com.howhich.fuchuang.demos.service.RecordsService;
import com.howhich.fuchuang.demos.service.StudentService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements StudentService {
    @Autowired
    private AuthMapper authMapper;
    @Autowired
    private UsersInfoMapper usersInfoMapper;
    @Autowired
    private StudentMapper studentMapper;
    @Autowired
    private RecordsService recordsService;
    @Autowired
    private PaperResultMapper paperResultMapper;
    @Override
    public Result registry(StudentEditReqVO reqVO) {
        User user = new User();
        BeanUtils.copyProperties(reqVO,user);
        user.setStatus(UserStatus.YES.code);
        user.setRole(RoleType.STUDENT.code);
        user.setUsername(reqVO.getStudentNum());
//        user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
        user.setPassword(SM4EncryptUtil.encrypt(user.getPassword()));
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

    @Override
    public Result<GetStudentRecordsRespVO> getStudentRecord(GetImportRecordsReqVO reqVO) {
        long loginId= StpUtil.getLoginIdAsLong();
        LambdaQueryWrapper<Student> studentLambdaQueryWrapper = new LambdaQueryWrapper();
        studentLambdaQueryWrapper.eq(Student::getId,loginId).last("limit 1");
        Student student = studentMapper.selectOne(studentLambdaQueryWrapper);
        String studentNum = student.getStudentNum();


        LambdaQueryWrapper queryWrapper = new LambdaQueryWrapper<Record>();
        Page<Record> page = new Page<>(reqVO.getPage(),reqVO.getPageSize());
        page = recordsService.page(page,queryWrapper);

        PageHelper.startPage(reqVO.getPage(),reqVO.getPageSize());
        page = recordsService.page(page,queryWrapper);
        long count = this.count(queryWrapper);



        GetStudentRecordsRespVO respVO = new GetStudentRecordsRespVO();
        List<StudentRecord> studentRecords = new ArrayList<>();
        List<Record> records = page.getRecords();
        records.forEach(record -> {
            Long recordId = record.getId();
            LambdaQueryWrapper<PaperResult> resultLambdaQueryWrapper = new LambdaQueryWrapper<>();
            resultLambdaQueryWrapper.eq(PaperResult::getRecordId,recordId)
                    .eq(PaperResult::getStudentNum,studentNum).last("limit 1");
            PaperResult paperResult = paperResultMapper.selectOne(resultLambdaQueryWrapper);
            if(!ObjectUtils.isEmpty(paperResult)){
                Long resultGroupId = paperResult.getResultGroupId();

                StudentRecord studentRecord = new StudentRecord();
                BeanUtils.copyProperties(record,studentRecord);
//            studentRecord.setRecordName(record.getRecordName());
                studentRecord.setGroupId(resultGroupId);
                studentRecords.add(studentRecord);
            }

        });

        respVO.setList(studentRecords);
        respVO.setTotal((long) studentRecords.size());
        return Result.success(respVO);
    }
}
