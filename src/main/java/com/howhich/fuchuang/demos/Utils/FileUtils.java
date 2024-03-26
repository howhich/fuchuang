package com.howhich.fuchuang.demos.Utils;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class FileUtils extends org.apache.tomcat.util.http.fileupload.FileUtils {
    public static String getImgFileToBase64(String imgFile) {
        //将图片文件转化为字节数组字符串，并对其进行Base64编码处理
        InputStream inputStream = null;
        byte[] buffer = null;
        //读取图片字节数组
        try {
            inputStream = new FileInputStream(imgFile);
            int count = 0;
            while (count == 0) {
                count = inputStream.available();
            }
            buffer = new byte[count];
            inputStream.read(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    // 关闭inputStream流
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        // 对字节数组Base64编码
        return new BASE64Encoder().encode(buffer);
    }

    /**
     * 将图片内容转换成Base64编码的字符串，并获得图片宽高，进行缩放
     * @param imageFile 图片文件的全路径名称
     * @param flag 判断图片是否是用户头像
     * @return base64字符串和图片宽高
     */

    public static Map<String,String> getImageBase64String(String imageFile,boolean flag) {
        Map<String,String> map = new HashMap<>();
        if (StringUtils.isEmpty(imageFile)) {
            return null;
        }
        File file = new File(imageFile);
        if (!file.exists()) {
            return null;
        }
        InputStream is = null;
        InputStream is1 = null;
        byte[] data = null;
        try {
            is = new FileInputStream(file);
            is1 = new FileInputStream(file);
            data = new byte[is.available()];
            is.read(data);
            //获取图片宽高
            BufferedImage image = ImageIO.read(is1);
            //图片的原始宽高
            int height = image.getHeight();
            int width  = image.getWidth();
            //如果图片是用户头像的话，按照50*50的标准来判断是否要缩小，否则的话按照500*500
            if (flag){
                //宽或高有一项大于50时，等比缩小
                if (width > 50 || height > 50){
                    int cWidth  = 50;
                    int cHeight  = 50;
                    int showWidth = cWidth;
                    int showHeight = cHeight;
                    //原图宽高太大进行等比缩放
                    if(1.0 * width/height >= 1.0 * cWidth/cHeight){
                        //图片比较宽
                        showHeight = showWidth * height / width;
                    }else {
                        //图片比较长
                        showWidth = showHeight * width / height;
                    }
                    map.put("height",Integer.toString(showHeight));
                    map.put("width",Integer.toString(showWidth));
                }else {
                    //否则使用图片的原始大小
                    map.put("height",Integer.toString(height));
                    map.put("width",Integer.toString(width));
                }
            }else {
                //宽或高大于500时，进行缩放
                if (width > 500 || height > 500){
                    int cWidth  = 500;
                    int cHeight  = 500;
                    int showWidth = cWidth;
                    int showHeight = cHeight;
                    //原图宽高太大进行等比缩放
                    if(1.0 * width/height >= 1.0 * cWidth/cHeight){
                        //图片比较宽
                        showHeight = showWidth * height / width;
                    }else {
                        //图片比较长
                        showWidth = showHeight * width / height;
                    }
                    map.put("height",Integer.toString(showHeight));
                    map.put("width",Integer.toString(showWidth));
                }else {
                    map.put("height",Integer.toString(height));
                    map.put("width",Integer.toString(width));
                }
            }
            is.close();
            is1.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        BASE64Encoder encoder = new BASE64Encoder();
        map.put("encode",encoder.encode(data));
        return map;
    }
}
