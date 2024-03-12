package com.howhich.fuchuang.demos.aspect;

import cn.hutool.extra.spring.SpringUtil;
import com.howhich.fuchuang.demos.Utils.exception.TimeUtil;
import com.howhich.fuchuang.demos.config.thread.AsyncExecutor;
import com.howhich.fuchuang.demos.constant.Result;
import com.howhich.fuchuang.demos.entity.Base.PaperResult;
import com.howhich.fuchuang.demos.entity.resp.ImportRecordsRespVO;
import com.howhich.fuchuang.demos.service.PaperResultService;
import lombok.Data;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Component
@Aspect
public class OperationCallAspect {
    @Autowired
    private PaperResultService paperResultService;
    @Pointcut(value = "@annotation(com.howhich.fuchuang.demos.aspect.OperationAspect)")
    public void operationLogPointCut(){
    }

    @AfterReturning(value = "operationLogPointCut()", returning = "data")
    public void saveOperationLog(JoinPoint joinPoint, Object data){

        try {
            AsyncExecutor.getExecutor().schedule(new Thread(() ->{
                System.out.println("——————————————————AOP异步调用启动————————————————————————————————");
                //todo 调用外部接口

//                System.out.printf(data.toString());
//                Result result = (Result) data;
//                ImportRecordsRespVO respVO = (ImportRecordsRespVO) result.getData();
////            ImportRecordsRespVO respVO = (ImportRecordsRespVO) data;
//                List<Long> ids = respVO.getIds();
//                int size = ids.size();
//                List<PaperResult> paperResults = new ArrayList<>();
//
//                for(int i = 0;i<size;i++){
//                    PaperResult paperResult = new PaperResult();
//                    paperResult.setId(ids.get(i));
//                    paperResult.setCreateUser(1L);
//                    paperResult.setUpdateUser(1L);
//                    paperResults.add(paperResult);
//                    paperResultService.updateStatus(ids.get(i));
//
//                }
                System.out.println("——————————————————AOP异步调用成功————————————————————————————————");

            }));
        }catch (Exception e){

        }

    }
    @AfterThrowing(value = "operationLogPointCut()")
    public void saveOperationThrowLog(JoinPoint joinPoint){

    }

}
