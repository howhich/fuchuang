package com.howhich.fuchuang.demos.service.serviceImpl;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.howhich.fuchuang.demos.constant.Result;
import com.howhich.fuchuang.demos.entity.Base.PaperResult;
import com.howhich.fuchuang.demos.entity.Base.Record;
import com.howhich.fuchuang.demos.entity.Base.Student;
import com.howhich.fuchuang.demos.entity.req.GetPaperResultByIdReqVO;
import com.howhich.fuchuang.demos.entity.req.GetStudentPaperResultReqVO;
import com.howhich.fuchuang.demos.entity.resp.GetPaperResultByIdRespVO;
import com.howhich.fuchuang.demos.entity.resp.GetStudentPaperResultRespVO;
import com.howhich.fuchuang.demos.mapper.PaperResultMapper;
import com.howhich.fuchuang.demos.service.PaperResultService;
import com.howhich.fuchuang.demos.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaperResultServiceImpl extends ServiceImpl<PaperResultMapper, PaperResult> implements PaperResultService {
    @Autowired
    private PaperResultMapper paperResultMapper;
    @Autowired
    private StudentService studentService;
    @Override
    public Result<GetPaperResultByIdRespVO> page(GetPaperResultByIdReqVO reqVO) {
        LambdaQueryWrapper<PaperResult> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PaperResult::getRecordId,reqVO.getId());
        Page<PaperResult> page = new Page(reqVO.getPage(),reqVO.getPageSize());

        PageHelper.startPage(reqVO.getPage(),reqVO.getPageSize());

        page = this.page(page,queryWrapper);

        long count = this.count(queryWrapper);

        //TODO 条件查询

        GetPaperResultByIdRespVO respVO = new GetPaperResultByIdRespVO();
        respVO.setList(page.getRecords());
        respVO.setTotal((int) count);
        return Result.success(respVO);
    }

    @Override
    public Result<GetStudentPaperResultRespVO> getStudentPaperResult(GetStudentPaperResultReqVO reqVO) {
        LambdaQueryWrapper<Student> studentLambdaQueryWrapper = new LambdaQueryWrapper<>();
        studentLambdaQueryWrapper.eq(Student::getId, StpUtil.getLoginIdAsLong())
                .last("limit 1");
        Student student = studentService.getOne(studentLambdaQueryWrapper);
        String studentNum = student.getStudentNum();

        LambdaQueryWrapper<PaperResult> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PaperResult::getStudentNum,studentNum);

        Page<PaperResult> page = new Page<>(reqVO.getPage(),reqVO.getPageSize());

        PageHelper.startPage(reqVO.getPage(),reqVO.getPageSize());
        page = this.page(page,queryWrapper);

        int count = (int) this.count(queryWrapper);

        GetStudentPaperResultRespVO respVO = new GetStudentPaperResultRespVO();
        respVO.setList(page.getRecords());
        respVO.setTotal(count);
        return Result.success(respVO);

//        return null;
    }

    @Override
    public void updateStatus(Long id) {
        paperResultMapper.updateStatus(id);
    }


}
