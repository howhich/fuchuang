package com.howhich.fuchuang.demos.init;

import com.howhich.fuchuang.demos.entity.Base.User;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

public class UserListener implements ApplicationListener<UserEvent> {
    @Override
    public void onApplicationEvent(UserEvent event) {
        Object  msg = event.getSource();
//        System.out.printf("监听器收到消息" + msg);
    }
}
