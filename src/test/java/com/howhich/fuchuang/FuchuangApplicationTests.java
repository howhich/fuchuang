package com.howhich.fuchuang;

import cn.hutool.core.date.StopWatch;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.SmUtil;
import cn.hutool.crypto.symmetric.SM4;
import com.howhich.fuchuang.demos.Utils.SM4EncryptUtil;
import com.howhich.fuchuang.demos.Utils.exception.TimeUtil;
import com.howhich.fuchuang.demos.entity.Base.User;
import com.howhich.fuchuang.demos.mapper.UsersInfoMapper;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.List;

import static cn.hutool.core.util.RandomUtil.*;

@SpringBootTest
class FuchuangApplicationTests {
    @Autowired
    private RedisTemplate redisTemplate;
    @Test
    void contextLoads() {
        redisTemplate.opsForValue().set("haha","nihao");
        System.out.println("操作成功");
        System.out.println(redisTemplate.opsForValue().get("haha"));
        System.out.println(redisTemplate.opsForValue().get("h"));
    }
    @Resource
    private UsersInfoMapper usersInfoMapper;

    @Test
    void test(){
        List<User> users = usersInfoMapper.selectList(null);
        users.forEach(user -> System.out.println(user.getUsername() +":" + user.getRole()));
    }
    @Test
    void test1(){
        System.out.printf(TimeUtil.getLastWorkDayAsDay());
    }
    @Test
    void test2(){
        String key = randomString(16);
        System.err.println("生成1个128bit的加密key:"+key);

        //原文
        String str = "hello";
        System.err.println("原文:"+str);

        StopWatch sw = StopWatch.create("q11");
        sw.start();

        SM4 sm41 = SmUtil.sm4(key.getBytes());
        //加密为Hex
        String hexPass = sm41.encryptHex(str);
        System.err.println("Hex形式的密文:"+hexPass);
        sw.stop();
        System.err.println(sw.getLastTaskInfo().getTimeSeconds());

        sw.start();
        //加密为base64
        String base64Pass = sm41.encryptBase64(str);
        System.err.println("base64形式的密文:"+base64Pass);
        sw.stop();
        System.err.println(sw.getLastTaskInfo().getTimeSeconds());

        System.err.println("--------------");
        //hex解密
        String s = sm41.decryptStr(hexPass);
        System.out.println(s);

        System.out.println("--------------");
        //base64解密
        String s2 = sm41.decryptStr(base64Pass);
        System.out.println(s2);

    }
    @Test
    void Test3(){
        String encrypt = SM4EncryptUtil.encrypt("123456");
        System.out.println(encrypt);
        System.out.println(SM4EncryptUtil.decrypt(encrypt));
    }
    @Test
    void test4(){
        System.out.println(StringUtils.center("man", 40));
    }
}
