package com.image.combiner.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/** 文件工具
 * author xiaochi
 * date 2024/11/19
 */
public class FileCoverUtil {

    /**
     * InputStream转Base64格式
     * @throws Exception
     */
    public static String toBase64(InputStream in) throws Exception {
        return toBase64(in,"jpg");
    }

    /**
     * Base64格式图片转input输入流
     */
    public static InputStream base64ToInStream(String base64Str){
        byte[] bytes = base64ToByteArray(base64Str);
        return new ByteArrayInputStream(bytes);
    }

    /**
     * Base64格式图片转byte[]数组
     */
    public static byte[] base64ToByteArray(String base64Str){
        base64Str = base64Str.replaceAll("data:image/(jpg|png|jpeg);base64,","");
        return Base64.getDecoder().decode(base64Str);
    }

    /**
     * InputStream转Base64格式
     * @throws Exception
     */
    public static String toBase64(InputStream in, String imageType) throws Exception {
        byte[] bytes = new byte[4096];
        int len;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        while ((len = in.read(bytes)) != -1){
            out.write(bytes,0,len);
        }
        out.flush();
        out.close();
        return "data:image/"+imageType+";base64,"+ new String(Base64.getEncoder().encode(out.toByteArray()), StandardCharsets.UTF_8);
    }
}
