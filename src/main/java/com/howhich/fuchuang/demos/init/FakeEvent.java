package com.howhich.fuchuang.demos.init;

import org.springframework.context.ApplicationEvent;

public class FakeEvent extends ApplicationEvent {
    public FakeEvent(Object source) {
        super(source);
    }


}
