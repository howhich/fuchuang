package com.howhich.fuchuang;

import cn.hutool.core.date.StopWatch;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.SmUtil;
import cn.hutool.crypto.symmetric.SM4;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.Sheet;
import com.howhich.fuchuang.demos.Utils.FileUtils;
import com.howhich.fuchuang.demos.Utils.SM4EncryptUtil;
import com.howhich.fuchuang.demos.Utils.WordUtils;
import com.howhich.fuchuang.demos.Utils.exception.TimeUtil;
import com.howhich.fuchuang.demos.constant.Result;
import com.howhich.fuchuang.demos.entity.Base.ExportEntity;
import com.howhich.fuchuang.demos.entity.Base.PaperDetail;
import com.howhich.fuchuang.demos.entity.Base.Record;
import com.howhich.fuchuang.demos.entity.Base.User;
import com.howhich.fuchuang.demos.mapper.UsersInfoMapper;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static cn.hutool.core.util.RandomUtil.*;

@SpringBootTest
class FuchuangApplicationTests {
    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    void ListImageTest() throws TemplateException, IOException {
        Map<String,Object> dataMap = new HashMap<>();

        List<Map<String,Object>> ExportEntity = new ArrayList<>();

        Map<String,Object> et1 = new HashMap<>();
        et1.put("questionNum","12");
        et1.put("score","2600");
        et1.put("comment","学生的翻译在整体意思上把握得不错，但在具体表达上还需加强。建议学生在翻译时更注重词汇的准确性和表达的流畅性。同时，也要注意避免笔误和理解错误，确保答案的完整性和正确性。相信通过不断努力和练习，学生的翻译能力会有所提高。");
        et1.put("image", FileUtils.getImgFileToBase64("C:\\Users\\howhich\\Desktop\\paper\\1\\3_2021091202001_7.png"));
        ExportEntity.add(et1);

        Map<String,Object> et2 = new HashMap<>();
        et2.put("questionNum","12");
        et2.put("score","26002");
        et2.put("comment","good");
        et2.put("image", FileUtils.getImgFileToBase64("C:\\Users\\howhich\\Desktop\\paper\\1\\3_2021091202001_9.png"));
        ExportEntity.add(et2);

        dataMap.put("ExportEntity",ExportEntity);

        WordUtils wordUtils = new WordUtils();
        String wordName = "D:\\resource\\test.doc";
        wordUtils.exportWord(dataMap,"temp.xml",wordName);
    }
    @Test
    void ImageTest() throws IOException, TemplateException {
        Map<String,String> dataMap = new HashMap<String,String>();
        dataMap.put("image",FileUtils.getImgFileToBase64("C:\\Users\\howhich\\Desktop\\paper\\1\\3_2021091202001_9.png"));
        Configuration configuration = new Configuration();
        configuration.setDefaultEncoding("utf-8");
        //.xml 模板文件所在目录
        configuration.setDirectoryForTemplateLoading(new File("D:\\resource"));
        // 输出文档路径及名称
        File outFile = new File("D:\\resource\\test1.doc");
        //以utf-8的编码读取模板文件
        Template t =  configuration.getTemplate("imageTest.xml","utf-8");
        Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile), "utf-8"),10240);
        t.process(dataMap, out);
        out.close();
        System.out.println("生成成功");
    }
    @Test
    void ListTest() throws IOException, TemplateException {
        List<PaperDetail> paperDetails = new ArrayList<>();
        PaperDetail paperDetail = new PaperDetail();
        paperDetail.setQuestionNum(1);
        paperDetail.setScore(33);
        paperDetail.setComment("good");

        PaperDetail paperDetail1 = new PaperDetail();
        paperDetail1.setQuestionNum(2);
        paperDetail1.setScore(66);
        paperDetail1.setComment("gooood");

        paperDetails.add(paperDetail);
        paperDetails.add(paperDetail1);

        Map<String,Object> map = new HashMap<>();
        map.put("paperDetails",paperDetails);
        Configuration configuration = new Configuration();

        configuration.setDefaultEncoding("utf-8");
        configuration.setDirectoryForTemplateLoading(new File("D:\\resource"));
        File outFile = new File("D:\\resource\\test.doc");

        Template t =  configuration.getTemplate("t1.xml","utf-8");
        Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile), "utf-8"),10240);
        t.process(map, out);
        out.close();
        System.out.println("生成成功");
    }

    @Test
    void ExcelImageTest() throws IOException, TemplateException {
        Map<String,String> dataMap = new HashMap<>();
        dataMap.put("questionNum","12");
        dataMap.put("score","33");
        dataMap.put("comment","good");
        Configuration configuration = new Configuration();
        configuration.setDefaultEncoding("utf-8");
        configuration.setDirectoryForTemplateLoading(new File("D:\\resource"));
        File outFile = new File("D:\\resource\\test.doc");

        Template t =  configuration.getTemplate("t1.xml","utf-8");
        Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile), "utf-8"),10240);
        t.process(dataMap, out);
        out.close();
        System.out.println("生成成功");
    }
    @Test
    void ExcelTest(){
            String tempFileName = "simple.xlsx";
            Record exportRecord = new Record();
            exportRecord.setId(1L);
            exportRecord.setTotalNum(55);
            List<Record> records = new ArrayList<>();
            records.add(exportRecord);
            EasyExcel.write(tempFileName,Record.class)
                    .sheet("考试导出")
                    .doWrite(records);
    }
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
