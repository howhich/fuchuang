package com.howhich.fuchuang;

import cn.hutool.extra.spring.SpringUtil;
import com.howhich.fuchuang.demos.entity.Base.User;
import com.howhich.fuchuang.demos.init.FakeListener;
import com.howhich.fuchuang.demos.init.UserEvent;
import com.howhich.fuchuang.demos.init.UserListener;
import com.howhich.fuchuang.demos.service.serviceImpl.AuthServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class FuchuangApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(FuchuangApplication.class, args);
//        context.addApplicationListener(new UserListener());
        context.addApplicationListener(new FakeListener());
//        context.publishEvent(new UserEvent("哈哈"));
//        SpringUtil.getApplicationContext().getBean(AuthServiceImpl.class);
    }

}
