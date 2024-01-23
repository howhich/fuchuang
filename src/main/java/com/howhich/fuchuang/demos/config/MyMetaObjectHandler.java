package com.howhich.fuchuang.demos.config;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.howhich.fuchuang.demos.Utils.exception.DateUtil;
import com.howhich.fuchuang.demos.Utils.exception.TimeUtil;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;

@Component
public class MyMetaObjectHandler  implements MetaObjectHandler {
    @Resource
    private RedisTemplate redisTemplate;
    @Override
    public void insertFill(MetaObject metaObject) {
        this.setFieldValByName("createTime", new Date(), metaObject);
        this.setFieldValByName("updateTime", new Date(), metaObject);
        if(!StpUtil.isLogin())return;
        this.setFieldValByName("createUser", StpUtil.getLoginIdAsLong(),metaObject);
        this.setFieldValByName("updateUser", StpUtil.getLoginIdAsLong(),metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName("updateTime", new Date(), metaObject);
        if(!StpUtil.isLogin())return;
        this.setFieldValByName("updateUser", StpUtil.getLoginIdAsLong(),metaObject);

    }
}
