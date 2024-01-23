package com.howhich.fuchuang.demos.service.serviceImpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.howhich.fuchuang.demos.constant.Result;
import com.howhich.fuchuang.demos.entity.Base.PaperResult;
import com.howhich.fuchuang.demos.mapper.PaperResultMapper;
import com.howhich.fuchuang.demos.service.PaperResultService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaperResultServiceImpl extends ServiceImpl<PaperResultMapper, PaperResult> implements PaperResultService {

    @Override
    public Result<List<PaperResult>> page(Long id) {

        return null;
    }
}
