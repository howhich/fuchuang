package com.howhich.fuchuang.demos.aspect;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface OperationAspect {
//    String detail() default "";
//    String module() default "";
//    String type() default "";
}
