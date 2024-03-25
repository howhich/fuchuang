package com.howhich.fuchuang.demos.init;


import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
/**
 * 自定义的初始化器
 */
public class MyApplicationContextInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
    @Override
    public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
        System.out.println("Initialization of MyApplicationContextInitializer...");
    }
}