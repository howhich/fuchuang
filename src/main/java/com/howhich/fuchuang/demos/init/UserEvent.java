package com.howhich.fuchuang.demos.init;

import com.howhich.fuchuang.demos.entity.Base.User;
import org.springframework.context.ApplicationEvent;

public class UserEvent extends ApplicationEvent {
    public UserEvent(Object source) {
        super(source);
    }


}
