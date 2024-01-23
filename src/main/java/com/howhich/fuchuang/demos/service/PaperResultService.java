package com.howhich.fuchuang.demos.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.howhich.fuchuang.demos.constant.Result;
import com.howhich.fuchuang.demos.entity.Base.PaperResult;

import java.util.List;

public interface PaperResultService extends IService<PaperResult> {
    Result<List<PaperResult>> page(Long id);
}
