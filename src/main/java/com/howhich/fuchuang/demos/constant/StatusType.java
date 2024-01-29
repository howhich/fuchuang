package com.howhich.fuchuang.demos.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

public interface StatusType {
//    @Getter
//    @AllArgsConstructor
//    class ADMIN implements RoleType{
//        public static final String code = "ADMIN";
//        public static final String name = "管理员";
//    }
    @Getter
    @AllArgsConstructor
    class JUDGING implements StatusType {
        public static final String code = "JUDGING";
        public static final String name = "评审中";
    }
    @Getter
    @AllArgsConstructor
    class FINISH implements StatusType {
        public static final String code = "FINISH";
        public static final String name = "已完成";
    }
}
