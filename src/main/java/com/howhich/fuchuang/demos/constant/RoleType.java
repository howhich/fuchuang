package com.howhich.fuchuang.demos.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

public interface RoleType {
//    @Getter
//    @AllArgsConstructor
//    class ADMIN implements RoleType{
//        public static final String code = "ADMIN";
//        public static final String name = "管理员";
//    }
    @Getter
    @AllArgsConstructor
    class USER implements RoleType{
        public static final String code = "USER";
        public static final String name = "用户";
    }
    @Getter
    @AllArgsConstructor
    class TEACHER implements RoleType{
        public static final String code = "TEACHER";
        public static final String name = "老师";
    }
}
