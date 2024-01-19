package com.howhich.fuchuang.demos.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

public interface UserStatus {
    @Getter
    @AllArgsConstructor
    class YES implements UserStatus {
        public static final String code = "YES";
        public static final String name = "正常";
    }
    @Getter
    @AllArgsConstructor
    class NO implements UserStatus {
        public static final String code = "NO";
        public static final String name = "停用";
    }
}
