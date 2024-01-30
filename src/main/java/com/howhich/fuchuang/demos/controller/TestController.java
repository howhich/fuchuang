package com.howhich.fuchuang.demos.controller;

import cn.dev33.satoken.annotation.SaCheckRole;
import cn.dev33.satoken.stp.StpUtil;
import com.howhich.fuchuang.demos.constant.Result;
import com.howhich.fuchuang.demos.constant.RoleType;
import com.howhich.fuchuang.demos.entity.Base.Record;
import com.howhich.fuchuang.demos.entity.req.UsersInfoParam;
import com.howhich.fuchuang.demos.entity.resp.GetUsersRespVO;
import com.howhich.fuchuang.demos.service.AuthService;
import com.howhich.fuchuang.demos.service.RecordsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/test")
@Api(tags = "测试接口")
@Slf4j
//@SaCheckLogin
public class TestController {
    @Resource
    private AuthService authService;
    @Resource
    private RecordsService recordsService;

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
    public Result<GetUsersRespVO> getUsers(UsersInfoParam usersInfoParam){
        return authService.page(usersInfoParam);
    }

    @PostMapping("/photo")
    @ApiOperation("上传图片测试")
    public Result uploadPhoto(@RequestBody MultipartFile file) throws IOException {
        

        FileInputStream fileInputStream = (FileInputStream) file.getInputStream();

        UUID uuid = UUID.randomUUID();
        String detaiURL = uuid.toString();
        detaiURL = detaiURL.replace("-","") + ".jpg";

        Record record = new Record();
        record.setUrl(detaiURL);
        System.out.println(file.getOriginalFilename());
        System.out.println(file.getName());
        record.setRecordName(file.getOriginalFilename());
        recordsService.save(record);
// TODO 不需要存到数据库里面 数据库只存图片URI即可
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
            record.setUrl(detaiURL);
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
}