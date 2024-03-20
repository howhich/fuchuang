package com.howhich.fuchuang.demos.entity.listener;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.howhich.fuchuang.demos.Utils.FileUtil;
import com.howhich.fuchuang.demos.Utils.SM4EncryptUtil;
import com.howhich.fuchuang.demos.Utils.exception.AssertUtils;
import com.howhich.fuchuang.demos.Utils.exception.ExceptionsEnums;
import com.howhich.fuchuang.demos.Utils.exception.TimeUtil;
import com.howhich.fuchuang.demos.entity.Base.Student;
import com.howhich.fuchuang.demos.entity.Base.StudentInfo;
import com.howhich.fuchuang.demos.entity.Base.User;
import com.howhich.fuchuang.demos.mapper.AuthMapper;
import com.howhich.fuchuang.demos.mapper.StudentMapper;
import com.howhich.fuchuang.demos.mapper.UsersInfoMapper;
import lombok.Data;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.StringJoiner;

import static cn.dev33.satoken.SaManager.log;

@Data

public class ImportBatchStudentsListener extends AnalysisEventListener<StudentInfo> {

    private File importFile;

    private Long dataNum = new Long(0l);

    private Long time = new Long(0l);

    private Long start;

    private FileWriter fileWriter;

    private BufferedWriter bufferedWriter;

    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        // 开始计时

        start = System.currentTimeMillis();

        // 获取数据实体的字段列表
        Field[] fields = StudentInfo.class.getDeclaredFields();
        // 遍历字段进行判断
        for (Field field : fields) {
            // 获取当前字段上的ExcelProperty注解信息
            ExcelProperty fieldAnnotation = field.getAnnotation(ExcelProperty.class);
            // 判断当前字段上是否存在ExcelProperty注解
            if (ObjectUtils.isNotEmpty(fieldAnnotation)) {
                // 存在ExcelProperty注解则根据注解的index索引到表头中获取对应的表头名
                String headName = headMap.get(fieldAnnotation.index());
                // 判断表头是否为空或是否和当前字段设置的表头名不相同
                if (headName.isEmpty() ) {
                    // 如果为空，则抛出异常不再往下执行
                    AssertUtils.throwException("列模板名称不能为空");
                }
                if (!headName.equals(fieldAnnotation.value()[0])){
                    // 如果不匹配，也抛出异常不再往下执行
                    String[] value = fieldAnnotation.value();
                    Integer index = fieldAnnotation.index()+1;
                    AssertUtils.throwException("第" + index + "列模板名称不匹配，应为"+value[0]);
                }
            }
        }

        // 表头匹配, 创建文件和流
//        try {
//            importFile = FileUtil.createFile(System.currentTimeMillis() + "ImportTalent.txt");
//            fileWriter = new FileWriter(importFile,true);
//            bufferedWriter = new BufferedWriter(fileWriter);
//        } catch (IOException e){
//            AssertUtils.throwException("导入失败(创建文件失败)");
//        }

    }

    @Override
    public void invoke(StudentInfo info, AnalysisContext context) {
//        writeFile(info);
        long loginIdAsLong = StpUtil.getLoginIdAsLong();

        StudentMapper studentMapper = SpringUtil.getBean(StudentMapper.class);
        UsersInfoMapper usersInfoMapper = SpringUtil.getBean(UsersInfoMapper.class);


        Student student = studentMapper.selectOne(new LambdaQueryWrapper<Student>()
                .eq(Student::getStudentNum, info.getStudentNum())
                .last("limit 1"));
//        AssertUtils.isFalse(ObjectUtils.isEmpty(student), ExceptionsEnums.UserEX.USER_HAVE);
        if(ObjectUtils.isNotEmpty(student)){
            return;
        }
        String studentNum = info.getStudentNum();
        String password = studentNum.substring(studentNum.length() - 6);
        usersInfoMapper.insert(User.builder()
                        .username(info.getStudentNum())
//                        .password(DigestUtils.md5DigestAsHex(password.getBytes()))
                        .password(SM4EncryptUtil.encrypt(password))
                .build());
        Long id = usersInfoMapper.selectOne(new LambdaQueryWrapper<User>()
                        .orderByDesc(User::getCreateTime)
                        .last("limit 1")).getId();
        studentMapper.insert(Student.builder()
                        .name(info.getName())
                        .studentNum(info.getStudentNum())
                        .id(id)
                        .build());
        dataNum++;
    }

    @Override
    @Transactional
    public void doAfterAllAnalysed(AnalysisContext context){
        try {
            bufferedWriter.close();
            fileWriter.close();

//            UsersInfoMapper usersInfoMapper = SpringUtil.getBean(UsersInfoMapper.class);
//            usersInfoMapper.insert()

//            StudentMapper studentMapper = SpringUtil.getBean(StudentMapper.class);
//            studentMapper.importStudnet(importFile.getPath());
            time = System.currentTimeMillis()-start;
            log.info("{}条数据上传成功",dataNum);
        }catch (Exception e){
            e.printStackTrace();
        } finally {
            importFile.delete();
        }

    }

    private void writeFile(StudentInfo info){
        StringJoiner joiner = new StringJoiner(",");
        AssertUtils.isFalse(StringUtils.isNotEmpty(info.getStudentNum()),"学号不能为空");
        AssertUtils.isFalse(StringUtils.isNotEmpty(info.getName()),"姓名不能为空");

        joiner.add(StringUtils.isNotEmpty(info.getName()) ? info.getName() : "");
        joiner.add(StringUtils.isNotEmpty(info.getStudentNum()) ? info.getStudentNum() : "");

        try {
            bufferedWriter.write(joiner.toString() + "\n");
            bufferedWriter.flush();
        } catch (Exception e){
            e.printStackTrace();
            AssertUtils.throwException("导入失败(写入文件失败)");
        }

    }
}
