package com.howhich.fuchuang.demos.service.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.howhich.fuchuang.demos.constant.Result;
import com.howhich.fuchuang.demos.entity.Base.PaperResult;
import com.howhich.fuchuang.demos.entity.Base.User;
import com.howhich.fuchuang.demos.entity.req.GetPaperResultByIdReqVO;
import com.howhich.fuchuang.demos.entity.resp.GetAllStudentsByClassIdRespVO;
import com.howhich.fuchuang.demos.entity.resp.GetPaperResultByIdRespVO;
import com.howhich.fuchuang.demos.entity.resp.GetUsersRespVO;
import com.howhich.fuchuang.demos.mapper.PaperResultMapper;
import com.howhich.fuchuang.demos.service.PaperResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaperResultServiceImpl extends ServiceImpl<PaperResultMapper, PaperResult> implements PaperResultService {
    @Autowired
    private PaperResultMapper paperResultMapper;
    @Override
    public Result<GetPaperResultByIdRespVO> page(GetPaperResultByIdReqVO reqVO) {
        LambdaQueryWrapper<PaperResult> queryWrapper = new LambdaQueryWrapper<>();
        Page<PaperResult> page = new Page(reqVO.getPage(),reqVO.getPageSize());

        PageHelper.startPage(reqVO.getPage(),reqVO.getPageSize());

        page = this.page(page,queryWrapper);

        long count = this.count();

        //TODO 条件查询

        GetPaperResultByIdRespVO respVO = new GetPaperResultByIdRespVO();
        respVO.setList(page.getRecords());
        respVO.setTotal((int) count);
        return Result.success(respVO);
    }
}
