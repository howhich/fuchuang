package com.howhich.fuchuang;

import com.howhich.fuchuang.demos.mapper.UsersInfoMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import java.util.List;

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
}
