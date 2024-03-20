package com.howhich.fuchuang.demos.Utils;

import cn.hutool.crypto.SmUtil;
import cn.hutool.crypto.symmetric.SM4;
import org.springframework.beans.factory.annotation.Value;

public class SM4EncryptUtil {
//    @Value("${encryption-key}")
    private static String key = "abcdabcdabcdabcd";
    public static String encrypt(String str) {
        SM4 sm4 = SmUtil.sm4(key.getBytes());
        String s = sm4.encryptHex(str.getBytes());
        return s;
    }
    public static String decrypt(String str) {
        SM4 sm4 = SmUtil.sm4(key.getBytes());
        String s = sm4.decryptStr(str);
        return s;
    }
}
