package com.nala.faceCatch.util;


import org.apache.commons.codec.binary.Base64;


/**
 * create by lizenn
 * create date 2018/7/30
 * description 图像工具类
 */
public class ImageUtil {

    /**
     * @param bytes
     * @return
     */
    public static byte[] decode(final byte[] bytes) {
        return Base64.decodeBase64(bytes);
    }

    /**
     * 二进制数据编码为BASE64字符串
     *
     * @param bytes
     * @return
     * @throws Exception
     */
    public static String encode(final byte[] bytes) {
        return new String(Base64.encodeBase64(bytes));
    }


    public static void main(String[] args) throws Exception{
        byte[] bytes1 = FileUtil.readFileByBytes("/Users/lizengqi/Pictures/2.jpg");
        System.out.println(encode(bytes1));
    }

}
