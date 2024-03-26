package com.howhich.fuchuang.demos.init;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class UserListener1 implements ApplicationListener<UserEvent> {
    @Override
    public void onApplicationEvent(UserEvent event) {
        Object  msg = event.getSource();
//        System.out.println("监听器1收到消息" + msg);
    }
}
