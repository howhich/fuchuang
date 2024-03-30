package com.howhich.fuchuang.demos.controller;

import cn.dev33.satoken.annotation.SaCheckRole;
import cn.dev33.satoken.stp.StpUtil;
import com.alibaba.fastjson.JSON;
import com.howhich.fuchuang.demos.constant.Result;
import com.howhich.fuchuang.demos.constant.RoleType;
import com.howhich.fuchuang.demos.entity.Base.Record;
import com.howhich.fuchuang.demos.entity.Base.User;
import com.howhich.fuchuang.demos.entity.req.UsersInfoReqVO;
import com.howhich.fuchuang.demos.entity.resp.GetUsersRespVO;
import com.howhich.fuchuang.demos.service.AuthService;
import com.howhich.fuchuang.demos.service.RecordsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.core.ApplicationContext;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.bouncycastle.asn1.x500.style.RFC4519Style.o;

@RestController
@RequestMapping("/api/test")
@Api(tags = "测试test")
@Slf4j
//@SaCheckLogin
public class TestController {
    @Resource
    private AuthService authService;
    @Resource
    private RecordsService recordsService;
    @Value("${file.pictureurl}")
    private String url;
    @Autowired
    private RedisTemplate redisTemplate;

    @GetMapping("/stream")
    @ApiOperation("流式测试")
    public void streamTest(String prompt, HttpServletResponse res) throws InterruptedException {
        log.info("【prompt内容】：{}", prompt);
        String str = "       什么是爱而不得? \n" +
                "东边日出西边雨，道是无晴却有晴。\n" +
                "他朝若是同淋雪，此生也算共白头。\n" +
                "我本将心向明月，奈何明月照沟渠。\n" +
                "此时相望不相闻，愿逐月华流照君。\n" +
                "衣带渐宽终不悔，为伊消得人憔悴。\n" +
                "此情可待成追忆，只是当时已惘然。\n" +
                "人生若只如初见，何事西风悲画扇。\n" +
                "曾经沧海难为水，除却巫山不是云。\n" +
                "何当共剪西窗烛，却话巴山夜雨时。\n" +
                "天长地久有时尽，此恨绵绵无绝期。\n" +
                "\n";
        // 响应流
        res.setHeader("Content-Type", "text/event-stream");
        res.setContentType("text/event-stream");
        res.setCharacterEncoding("UTF-8");
        res.setHeader("Pragma", "no-cache");
        ServletOutputStream out = null;
        try {
            out = res.getOutputStream();
            for (int i = 0; i < str.length(); i++) {
                out.write(String.valueOf(str.charAt(i)).getBytes());
                // 更新数据流
                out.flush();
                Thread.sleep(100);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    @GetMapping("/redisTest")
    @ApiOperation("REDIS测试")
    public String redisTest() throws IOException, ClassNotFoundException {
        User kobe = User.builder().id(1L).username("kobe").build();
        redisTemplate.opsForValue().set("man",kobe);
        Object o1 = redisTemplate.opsForValue().get("kobe");
        return o1.toString();
    }
    @GetMapping("/normal")
    @ApiOperation("测试接口")
    public String normal(){
        return "hello,fufuchuangchuang";
    }

    @GetMapping("/url")
    @ApiOperation("urltest")
    public String url(){return url;}
    @GetMapping("/hello")
    @ApiOperation("token测试")
    public String test1(){
        log.info("auth:"+StpUtil.getTokenValue());
        return (StpUtil.getLoginIdAsString());
    }
    @SaCheckRole(RoleType.TEACHER.code)
    @GetMapping("/auth")
    @ApiOperation("权限测试")
    public String test2(){
        return StpUtil.getRoleList().get(0);
    }

    @GetMapping("/getRole")
    @ApiOperation("获取角色")
    public String getRole(){
        return StpUtil.getRoleList().toString();
    }

    @GetMapping("/getUsers")
    @ApiOperation("获取所有用户信息")
    public Result<GetUsersRespVO> getUsers(UsersInfoReqVO usersInfoReqVO){
        return authService.page(usersInfoReqVO);
    }


    @PostMapping("/jar")
    @ApiOperation("上传jar测试")
    public Result uploadjar (@RequestBody MultipartFile file) throws IOException {
        if(!file.getOriginalFilename().equals("fuchuang-0.0.1-SNAPSHOT.jar")){
            return Result.fail("文件名错误");
        }
        String basicUrl = url;
        FileInputStream fileInputStream = (FileInputStream) file.getInputStream();
        FileOutputStream fileOutputStream = new FileOutputStream(basicUrl + file.getOriginalFilename());
        byte[] buffer = new byte[1024];
        int len = 0;
        while((len = fileInputStream.read(buffer)) != -1){
            fileOutputStream.write(buffer, 0, len);
        }
        fileOutputStream.close();
        return Result.success("完成上传");

    }
    @GetMapping("/roll")
    @ApiOperation("轮询")
    public DeferredResult<String> getDeferredResult(Long time){
        DeferredResult<String> deferredResult = new DeferredResult<>(5000L,"time out man");
        new Thread(()->{
            try {
                Thread.sleep(time);
                deferredResult.setResult("success");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
        return deferredResult;

    }

    @PostMapping("/photo")
    @ApiOperation("上传图片测试")
    public Result uploadPhoto(@RequestBody MultipartFile file) throws IOException {
        

        FileInputStream fileInputStream = (FileInputStream) file.getInputStream();

        UUID uuid = UUID.randomUUID();
        String detaiURL = uuid.toString();
        detaiURL = detaiURL.replace("-","") + ".jpg";

        Record record = new Record();
//        record.setUrl(detaiURL);
        System.out.println(file.getOriginalFilename());
        System.out.println(file.getName());
        record.setRecordName(file.getOriginalFilename());
        recordsService.save(record);

        String basicURL = "src/main/resources/static/";
        FileOutputStream fileOutputStream = new FileOutputStream(basicURL + detaiURL);

        byte[] buffer = new byte[1024];
        int len = 0;
        while((len = fileInputStream.read(buffer)) != -1){
            fileOutputStream.write(buffer, 0, len);
        }
        fileOutputStream.close();
        return Result.success("完成上传");
    }
    @PostMapping("/photoBatch")
    @ApiOperation(value = "批量上传图片测试")
    public Result uploadPhotoByBacth(@RequestBody List<MultipartFile> fileList) {
        String basicURL = "src/main/resources/static/";
        List<Record> records = new ArrayList<>();
        fileList.forEach(file -> {
            FileInputStream fileInputStream;
            try {
                 fileInputStream = (FileInputStream)file.getInputStream();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            UUID uuid = UUID.randomUUID();
            String detaiURL = uuid.toString();
            detaiURL = detaiURL.replace("-","") + ".jpg";

            Record record = new Record();
//            record.setUrl(detaiURL);
            record.setRecordName(file.getOriginalFilename());
            records.add(record);

            try {
                FileOutputStream fileOutputStream = new FileOutputStream(basicURL + detaiURL);
                byte[] buffer = new byte[1024];
                int len = 0;
                while ((len = fileInputStream.read(buffer))!=-1){
                    fileOutputStream.write(buffer,0,len);
                }
                fileInputStream.close();
                fileOutputStream.close();
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        recordsService.saveBatch(records);
        int count = records.size();
        return Result.success("完成上传,共计图片数量："+count);
    }
    @GetMapping("/sql")
    @ApiOperation(value = "sqltest")
    public Result sql(){
//        SqlSessionTemplate sqlSessionTemplate;
//        sqlSessionTemplate.
        return null;
    }
}
