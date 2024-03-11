package com.howhich.fuchuang.demos.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.howhich.fuchuang.demos.constant.Result;
import com.howhich.fuchuang.demos.entity.Base.Student;
import com.howhich.fuchuang.demos.entity.req.GetImportRecordsReqVO;
import com.howhich.fuchuang.demos.entity.req.StudentEditReqVO;
import com.howhich.fuchuang.demos.entity.resp.GetImportRecordsRespVO;
import com.howhich.fuchuang.demos.entity.resp.GetStudentRecordsRespVO;

public interface StudentService extends IService<Student> {
    Result registry(StudentEditReqVO reqVO);

    Result<GetStudentRecordsRespVO> getStudentRecord(GetImportRecordsReqVO reqVO);

//    Result stundetEdit(StudentEditReqVO reqVO);
}
