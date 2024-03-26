package com.howhich.fuchuang.demos.init;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class UserListener2 implements ApplicationListener<UserEvent> {
    @EventListener
    public void onApplicationEvent(UserEvent event) {
        Object  msg = event.getSource();
//        System.out.println("监听器2收到消息" + msg);
    }
}
