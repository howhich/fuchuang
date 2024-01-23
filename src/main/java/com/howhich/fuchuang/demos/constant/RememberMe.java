package com.howhich.fuchuang.demos.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

public interface RememberMe {
    @Getter
    @AllArgsConstructor
    class YES implements RememberMe {
        public static final String code = "YES";
        public static final String name = "记住我";
    }
    @Getter
    @AllArgsConstructor
    class NO implements RememberMe {
        public static final String code = "NO";
        public static final String name = "不记住我";
    }

}
